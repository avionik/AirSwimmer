package de.airswimmer.gui;

import com.microcontrollerbg.irdroid.Lirc;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MovementFunc extends Activity {

	byte buffer[];
	Lirc lirc;
	private static final String nameInLirc = "AirSwimmer2013";
	//private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";
	private static final String path = "/mnt/sdcard/AirSwimmerLirc.txt";
	// private static final String path = "/storage/sdcard0/AirSwimmerLirc.txt";
	// private static final String path = "/storage/sdcard0/Lircleft.txt";
	int bufSize;
	AudioTrack ir;
	AudioManager audio;
	SharedPreferences prefs;
	BaseActivity caller;
	private Handler mHandler = new Handler();
	// private static final String path = "/samsungLirc.txt";
	public MovementFunc(BaseActivity caller, AudioManager audio, SharedPreferences prefs){
		this.caller = caller;
		this.audio = audio;
		this.prefs = prefs;
		lirc = new Lirc();
		bufSize = AudioTrack.getMinBufferSize(48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT);
	}
	//Thread Definitions for each function 
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
	

	@SuppressWarnings("deprecation")
	protected void initialise() {

		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);
		audio = (AudioManager) caller.getSystemService(Context.AUDIO_SERVICE);
		
		int now = getVolume();
		if (audio.isBluetoothA2dpOn()) {

			audio.setBluetoothA2dpOn(true);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);

			audio = (AudioManager) caller.getSystemService(Context.AUDIO_SERVICE);
		} else {
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);
			audio = (AudioManager) caller.getSystemService(Context.AUDIO_SERVICE);
		}
		// get lirc config file for the air swimmer remote
		System.out.println(readConfigFile(path));

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
		System.out.println(command + " sent successfully!");

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
		return prefs.getInt("volume", 50);
	}
	void diveListener(ImageButton btdive){
		
		btdive.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				while (action == MotionEvent.ACTION_DOWN) {

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
							SystemClock.uptimeMillis() + 250);
				}

				if (action == MotionEvent.ACTION_UP) {
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
	}
	void climbListener(ImageButton button_up){
		button_up.setOnTouchListener(new OnTouchListener() {
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
					mHandler.removeCallbacks(climb);

				}
				return false;
			}

		});
	}
	
	void leftListener(ImageButton btleft){
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
					mHandler.removeCallbacks(left);

				}
				return false;
			}

		});
	}

	void rightListener(ImageButton btright){
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
					mHandler.removeCallbacks(right);

				}
				return false;
			}

		});
	}
}
