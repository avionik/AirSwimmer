package com.example.testlirc;

import com.microcontrollerbg.irdroid.Lirc;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	byte buffer[];
	Lirc lirc = new Lirc();
	private static final String nameInLirc = "AirSwimmer2013";
	@SuppressWarnings("deprecation")
	int bufSize = AudioTrack.getMinBufferSize(48000,
			AudioFormat.CHANNEL_CONFIGURATION_STEREO,
			AudioFormat.ENCODING_PCM_8BIT);
	AudioTrack ir;
	AudioManager audio;
	SharedPreferences mPrefs;
	// private static final String path = "/samsungLirc.txt";
	private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";

	// private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";
	// private static final String path = "/storage/sdcard0/Lircleft.txt";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);

		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mPrefs = this.getApplicationContext().getSharedPreferences(
				"myAppPrefs", 0); // 0 = mode private. only this app can read
									// these preferences

		int now = getVolume();
		if (audio.isBluetoothA2dpOn()) {

			audio.setBluetoothA2dpOn(true);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);

			audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		} else {
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);

			audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		}

		super.onCreate(savedInstanceState);

		buttons();

		// get lirc config file for the air swimmer remote
		System.out.println(readConfigFile(path));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * parses the lirc file
	 * 
	 * @param filepath
	 * @return true if lirc file parsed successfully else return false
	 */
	public boolean readConfigFile(String filepath) {

		System.out.println("Filepath = " + filepath);
		if (lirc.parse(filepath) == 0) {
			System.err.println("error parsing lirc file");
			return false;
		}

		return true;

		/*
		 * java.io.File file = new java.io.File(filename);
		 * 
		 * final String LIRCD_CONF_FILE = "/sdcard/lirc.conf";
		 * 
		 * 
		 * try { FileInputStream in = new FileInputStream(file);
		 * FileOutputStream out = new FileOutputStream(LIRCD_CONF_FILE); byte[]
		 * buf = new byte[1024]; int i = 0; while ((i = in.read(buf)) != -1) {
		 * out.write(buf, 0, i); } in.close(); out.close(); } catch (Exception
		 * e) { System.err.println("error reading file: " + e); return false; }
		 */

	}

	@SuppressWarnings("deprecation")
	public void sendCommand(String command) {

		//ir.release();
		// expects the name of the Lirc file (not filename, name is stated in
		// the file) & the command which should be executed
		buffer = lirc.getIrBuffer(nameInLirc, command);
		
		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;
		/*for(int i =0; i < buffer.length; i++){
			System.out.println(buffer[i]);
		}*/
		ir.write(buffer, 0, buffer.length);
		
		ir.setStereoVolume(1, 1);

		ir.play();
		System.out.println(command + " sent successfully!");

	}

	public void buttons() {
		// set buttons
		Button left = (Button) findViewById(R.id.Left);
		Button right = (Button) findViewById(R.id.Right);
		Button dive = (Button) findViewById(R.id.Dive);
		Button climb = (Button) findViewById(R.id.Climb);

		left.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("left pressed");
				sendCommand(Commands.TAILLEFT.name());
				System.out.println(Commands.TAILLEFT.name());
				// try hard coded string
				//sendCommand("TAILLEFT");

			}
		});

		right.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("right pressed");
				sendCommand(Commands.TAILRIGHT.name());

			}
		});

		climb.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("climb pressed");
				sendCommand(Commands.CLIMB.name());

			}
		});

		dive.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("dive pressed");
				sendCommand(Commands.DIVE.name());

			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//System.out.println("KeyCode = " + keyCode);
		//System.out.println("Event = " + event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			return true;

		default:
			return super.onKeyDown(keyCode, event);
		}
	}

	public int getVolume() {
		return mPrefs.getInt("volume", 50);
	}

}
