package com.example.testlirc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.microcontrollerbg.irdroid.Lirc;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	byte buffer[];
	Lirc lirc = new Lirc();
	@SuppressWarnings("deprecation")
	int bufSize = AudioTrack.getMinBufferSize(48000,
			AudioFormat.CHANNEL_CONFIGURATION_STEREO,
			AudioFormat.ENCODING_PCM_8BIT);
	AudioTrack ir;
	AudioManager audio;
	private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";
	//private static final String path = "/storage/sdcard0/Lircleft.txt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_main);
		
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		super.onCreate(savedInstanceState);
		
		//set buttons
		Button dive = (Button) findViewById(R.id.dive);
		
		dive.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				System.out.println("Dive pressed");
				dive();
			
			}
		});
		//Button voldn = (Button) findViewById(R.id.apple_voldn);
		//Button voldn = (Button) findViewById(R.id.apple_voldn);
		//Button voldn = (Button) findViewById(R.id.apple_voldn);
		
		//get lirc config file for the air swimmer remote
		System.out.println(readConfigFile(path));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean readConfigFile(String filename) {

		java.io.File file = new java.io.File(filename);
		final String LIRCD_CONF_FILE = "/sdcard/lirc.conf";

		if (lirc.parse(filename) == 0) {
			System.err.println("error parsing lirc file");
			return false;
		}
		try {
			FileInputStream in = new FileInputStream(file);
			FileOutputStream out = new FileOutputStream(LIRCD_CONF_FILE);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = in.read(buf)) != -1) {
				out.write(buf, 0, i);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			System.err.println("error reading file: " + e);
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public void dive() {

		buffer = lirc.getIrBuffer("AirSwimmer2013", "DIVE");

		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(1, 1);

		ir.play();
		System.out.println("dive successfull");

	}

	@SuppressWarnings("deprecation")
	public void climb() {
		
		buffer = lirc.getIrBuffer("test", "CLIMB");

		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(1, 1);

		ir.play();

	}

	@SuppressWarnings("deprecation")
	public void right() {
		
		buffer = lirc.getIrBuffer("test", "TAILRIGHT");

		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(1, 1);
		ir.play();

	}

	@SuppressWarnings("deprecation")
	public void left() {
		
		buffer = lirc.getIrBuffer("test", "TAILLEFT");

		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(1, 1);

		ir.play();

	}

}
