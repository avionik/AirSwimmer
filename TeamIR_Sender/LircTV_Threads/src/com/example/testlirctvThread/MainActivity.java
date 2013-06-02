package com.example.testlirctvThread;

import com.example.testlirctvThread.R;
import com.microcontrollerbg.irdroid.Lirc;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Handler mHandler = new Handler();
	byte buffer[];
	Lirc lirc = new Lirc();
	private static final String nameInLirc = "SamsungTV";
	@SuppressWarnings("deprecation")
	int bufSize = AudioTrack.getMinBufferSize(48000,
			AudioFormat.CHANNEL_CONFIGURATION_STEREO,
			AudioFormat.ENCODING_PCM_8BIT);
	AudioTrack ir;
	AudioManager audio;
	// private static final String path = "/samsungLirc.txt";
	private static final String path = "/storage/sdcard0/SamsungLirc.txt";

	// private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";
	// private static final String path = "/storage/sdcard0/Lircleft.txt";
	
	
	//thread declaration
	private Runnable chandown = new Runnable() {
		public void run() {
			System.out.println("chandown pressed");
			ir.release();

			String coolcmd = "Chan-";
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
	
	private Runnable chanup = new Runnable() {
		public void run() {
			System.out.println("chan+ pressed");
			ir.release();

			String coolcmd = "Chan+";
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
	
	private Runnable volumeup = new Runnable() {
		public void run() {
			System.out.println("volup pressed");
			ir.release();

			String coolcmd = "Vol+";
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
	
	private Runnable volumedown = new Runnable() {
		public void run() {
			System.out.println("voldown pressed");
			ir.release();

			String coolcmd = "Vol-";
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
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);

		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(1, 1);

		ir.play();
		System.out.println(command + " sent successfully!");

	}

	public void buttons() {
		// set buttons
		Button pdown = (Button) findViewById(R.id.Pdown);
		Button pup = (Button) findViewById(R.id.Pup);
		Button voldown = (Button) findViewById(R.id.Voldown);
		Button volup = (Button) findViewById(R.id.Volup);
		Button beamer = (Button) findViewById(R.id.beamer);

		pdown.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = "Chan-";

					try {

						sendCommand(mycmd);
						System.out.println("chan- pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(chandown,
							SystemClock.uptimeMillis() + 250);
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
					mHandler.removeCallbacks(chandown);

				}
				return false;
			}

		});
		
		pup.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = "Chan+";

					try {

						sendCommand(mycmd);
						System.out.println("chan+ pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(chanup,
							SystemClock.uptimeMillis() + 250);
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
					mHandler.removeCallbacks(chanup);

				}
				return false;
			}

		});
		
		volup.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = "Vol+";

					try {

						sendCommand(mycmd);
						System.out.println("vol+ pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(volumeup,
							SystemClock.uptimeMillis() + 250);
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
					mHandler.removeCallbacks(volumeup);

				}
				return false;
			}

		});
		
		voldown.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					String mycmd = "Vol-";

					try {

						sendCommand(mycmd);
						System.out.println("vol- pressed");
						// Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
						// mHandler.removeCallbacks(mUpdateTask);

					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					mHandler.postAtTime(volumedown,
							SystemClock.uptimeMillis() + 250);
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
					mHandler.removeCallbacks(volumedown);

				}
				return false;
			}

		});
		
		beamer.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("beamer pressed");
				sendCommand("BEAMER");

			}
		});
	}

}
