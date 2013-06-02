package com.example.testlircfish_thread;

import com.example.testlircfish_thread.R;
import com.microcontrollerbg.irdroid.Lirc;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Handler mHandler = new Handler();
	byte buffer[];
	Lirc lirc = new Lirc();
	SharedPreferences mPrefs;
	private static final String nameInLirc = "AirSwimmer2013";
	
	
	@SuppressWarnings("deprecation")
	int bufSize = AudioTrack.getMinBufferSize(45000,
			AudioFormat.CHANNEL_CONFIGURATION_STEREO,
			AudioFormat.ENCODING_PCM_8BIT);
	AudioTrack ir;
	AudioManager audio;
	// private static final String path = "/samsungLirc.txt";
	//private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";
	 private static final String path = "/mnt/sdcard/AirSwimmerLirc.txt";
	// private static final String path = "/storage/sdcard0/Lircleft.txt";
	
	
	//thread declaration
	private Runnable dive = new Runnable() {
		public void run() {
			System.out.println("dive pressed");
			ir.release();

			String cmd = Commands.DIVE.name();
			// Log.i("repeatBtn", "repeat click");

			try {

				sendCommand(cmd);
				// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
				// mHandler.removeCallbacks(mUpdateTask);

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

		}
	};
	
	private Runnable climb = new Runnable() {
		public void run() {
			System.out.println("climb pressed");
			ir.release();

			String cmd = Commands.CLIMB.name();
			// Log.i("repeatBtn", "repeat click");

			try {

				sendCommand(cmd);
				// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
				// mHandler.removeCallbacks(mUpdateTask);

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

		}
	};
	
	private Runnable left = new Runnable() {
		public void run() {
			System.out.println("left pressed");
			ir.release();

			String coolcmd = Commands.TAILLEFT.name();
			// Log.i("repeatBtn", "repeat click");

			try {

				sendCommand(coolcmd);
				// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
				// mHandler.removeCallbacks(mUpdateTask);

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

		}
	};
	
	private Runnable right = new Runnable() {
		public void run() {
			System.out.println("right pressed");
			ir.release();

			String cmd = Commands.TAILRIGHT.name();
			// Log.i("repeatBtn", "repeat click");

			try {

				sendCommand(cmd);
				// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
				// mHandler.removeCallbacks(mUpdateTask);

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

		}
	};
	
	
	
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

		// expects the name of the Lirc file (not filename, name is stated in
		// the file) & the command which should be executed
		buffer = lirc.getIrBuffer(nameInLirc, command);

		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 45000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(2, 2);
		//audio.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
		ir.play();
		System.out.println(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
		System.out.println(command + " sent successfully!");

	}

	public void buttons() {
		// set buttons
		Button btclimb = (Button) findViewById(R.id.bclimb);
		Button btdive = (Button) findViewById(R.id.bdive);
		Button btright = (Button) findViewById(R.id.bright);
		Button btleft = (Button) findViewById(R.id.bleft);

		btdive.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = Commands.DIVE.name();

					try {

						sendCommand(mycmd);
						System.out.println("dive pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(dive,
							SystemClock.uptimeMillis());
				}

				else if (action == MotionEvent.ACTION_UP) {
					// Log.i("repeatBtn", "MotionEvent.ACTION_UP");\
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.removeCallbacks(dive);

				}
				return false;
			}

		});
		
		btclimb.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = Commands.CLIMB.name();

					try {

						sendCommand(mycmd);
						System.out.println("climb pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(climb,
							SystemClock.uptimeMillis());
				}

				else if (action == MotionEvent.ACTION_UP) {
					// Log.i("repeatBtn", "MotionEvent.ACTION_UP");\
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.removeCallbacks(climb);

				}
				return false;
			}

		});
		
		btleft.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = Commands.TAILLEFT.name();

					try {

						sendCommand(mycmd);
						System.out.println("left pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(left,
							SystemClock.uptimeMillis());
				}

				else if (action == MotionEvent.ACTION_UP) {
					// Log.i("repeatBtn", "MotionEvent.ACTION_UP");\
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.removeCallbacks(left);

				}
				return false;
			}

		});
		
		btright.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = Commands.TAILRIGHT.name();

					try {

						sendCommand(mycmd);
						System.out.println("right pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(right,
							SystemClock.uptimeMillis());
				}

				else if (action == MotionEvent.ACTION_UP) {
					// Log.i("repeatBtn", "MotionEvent.ACTION_UP");\
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.removeCallbacks(right);

				}
				return false;
			}

		});
	}
	public int getVolume() {
		return mPrefs.getInt("volume", 50);
	}

}
