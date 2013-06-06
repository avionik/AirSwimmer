package de.airswimmer.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import com.microcontrollerbg.irdroid.Lirc;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Movement extends Activity {

	private Handler mHandler = new Handler();
	byte buffer[];
//	Lirc lirc = new Lirc();
	SharedPreferences mPrefs;
	int volumeLvl = 1;
	boolean repeat = true;
	private static final String nameInLirc = "AirSwimmer2013";
	private static final String nameOfLirc = "lirc.txt";
	
	@SuppressWarnings("deprecation")
	int bufSize = AudioTrack.getMinBufferSize(45000,
			AudioFormat.CHANNEL_CONFIGURATION_STEREO,
			AudioFormat.ENCODING_PCM_8BIT);
	AudioTrack ir;
	AlertDialog vol;
	AudioManager audio;
	BaseActivity caller;
	public Movement(BaseActivity baseActivity, AudioManager audio, SharedPreferences prefs){
		this.caller = baseActivity;
		this.audio = audio;
		mPrefs = prefs;
	//	lirc = new Lirc();
		bufSize = AudioTrack.getMinBufferSize(48000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT);
	}
	
	// thread declaration
	private Runnable dive = new Runnable() {
		public void run() {
			System.out.println("dive pressed");
			if (ir != null) {
				ir.flush();
				ir.release();

				String cmd = Commands.DIVE.name();
				try {
					sendCommand(cmd);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

			}
		}
	};

	private Runnable climb = new Runnable() {
		public void run() {
			System.out.println("climb pressed");
			if (ir != null) {
				ir.flush();
				ir.release();

				String cmd = Commands.CLIMB.name();
				try {
					sendCommand(cmd);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

			}
		}
	};

	private Runnable left = new Runnable() {
		public void run() {
			System.out.println("left pressed");
			if (ir != null) {
				ir.flush();
				ir.release();

				String coolcmd = Commands.TAILLEFT.name();
				try {
					sendCommand(coolcmd);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

			}
		}
	};

	private Runnable right = new Runnable() {
		public void run() {
			System.out.println("right pressed");
			if (ir != null) {
				ir.flush();
				ir.release();

				String cmd = Commands.TAILRIGHT.name();
				try {
					sendCommand(cmd);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				mHandler.postAtTime(this, SystemClock.uptimeMillis() + 250);

			}
		}
	};

	@SuppressWarnings("deprecation")
	protected void initialise(){

		//audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);

		int now = getVolume();
		if (audio.isBluetoothA2dpOn()) {

			audio.setBluetoothA2dpOn(true);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);
		} else {
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);
		}

		// get lirc config file for the air swimmer remote
		if (readConfigFile(importLircfile())) {
			System.out.println("Read and wrote Lirc-File successfully");
			// Configure Volume Level
			//configureVolume();
		} else {
			Toast.makeText(getApplicationContext(),
					"Failed to parse Lirc-File", Toast.LENGTH_SHORT).show();
			System.err.println("Error in Lirc Context");
			finish();
		}
	}

	/**
	 * reads the lirc file in the package directory and saves it to sdcard on
	 * device
	 * 
	 * @return path to lirc file
	 */
	public String importLircfile() {
		String newFolder = "/AirSwimmer";
		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();

		File dir = new File(extStorageDirectory + newFolder + "/Lirc.txt");
		if (!dir.isDirectory()) {

			File myNewFolder = new File(extStorageDirectory + newFolder);
			myNewFolder.mkdir();
			File f = new File(extStorageDirectory + newFolder + "/Lirc.txt");
			try {
				final InputStream is = getResources().getAssets().open(nameOfLirc);
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(is));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line + '\n');
					}
					FileWriter fwriter = new FileWriter(f);
					BufferedWriter bwriter = new BufferedWriter(fwriter);
					bwriter.write(sb.toString());
					bwriter.close();
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(),
							"Failed to parse Lirc-File", Toast.LENGTH_SHORT)
							.show();
					System.err
							.println("IOException when parsing and writing file: "
									+ e);
				} finally {

					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							System.err
									.println("IOException when closing input stream: "
											+ e);
							Toast.makeText(getApplicationContext(),
									"Failed to parse Lirc-File",
									Toast.LENGTH_SHORT).show();
						}
					}
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							System.err
									.println("IOException when closing buffered reader: "
											+ e);
							Toast.makeText(getApplicationContext(),
									"Failed to parse Lirc-File",
									Toast.LENGTH_SHORT).show();
						}
					}

				}

			} catch (IOException e) {
				System.err.println("Lirc File could not be opened!");
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Failed to parse Lirc-File", Toast.LENGTH_SHORT).show();
			}
		}
		return dir.getAbsolutePath();
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
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
		ir.play();
		System.out.println(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
		System.out.println(command + " sent successfully!");

	}

void diveListener(final ImageButton btdive){
		
		btdive.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				while (action == MotionEvent.ACTION_DOWN) {
					
					btdive.setBackgroundColor(Color.BLUE);	
					String mycmd = Commands.DIVE.name();

					try {

						sendCommand(mycmd);
						System.out.println("dive pressed");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}

					mHandler.postAtTime(dive,
							SystemClock.uptimeMillis() + 250);
				}

				if (action == MotionEvent.ACTION_UP) {
					btdive.setBackgroundColor(Color.TRANSPARENT);	
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mHandler.removeCallbacks(dive);

				}
				return false;
			}

		});
	}
	void climbListener(final ImageButton btclimb){
		btclimb.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					btclimb.setBackgroundColor(Color.BLUE);	
					String mycmd = Commands.CLIMB.name();

					try {

						sendCommand(mycmd);
						System.out.println("climb pressed");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}

					mHandler.postAtTime(climb,
							SystemClock.uptimeMillis() + 250);
				}

				else if (action == MotionEvent.ACTION_UP) {
					btclimb.setBackgroundColor(Color.TRANSPARENT);	
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mHandler.removeCallbacks(climb);

				}
				return false;
			}

		});
	}
	
	void leftListener(final ImageButton btleft){
		btleft.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					btleft.setBackgroundColor(Color.BLUE);	
					String mycmd = Commands.TAILLEFT.name();

					try {

						sendCommand(mycmd);
						System.out.println("left pressed");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}

					mHandler.postAtTime(left,
							SystemClock.uptimeMillis() + 250);
				}

				else if (action == MotionEvent.ACTION_UP) {
					btleft.setBackgroundColor(Color.TRANSPARENT);	
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mHandler.removeCallbacks(left);

				}
				return false;
			}

		});
	}
	
	void rightListener(final ImageButton btright){
		btright.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionevent) {
				int action = motionevent.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					btright.setBackgroundColor(Color.BLUE);	
					String mycmd = Commands.TAILLEFT.name();

					try {

						sendCommand(mycmd);
						System.out.println("right pressed");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}

					mHandler.postAtTime(left,
							SystemClock.uptimeMillis() + 250);
				}

				else if (action == MotionEvent.ACTION_UP) {
					btright.setBackgroundColor(Color.TRANSPARENT);	
					try {
						Thread.sleep(180);
						if (ir != null) {
							ir.flush();
							ir.release();

						}
					} catch (InterruptedException e) {
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

	
	
	
	/*
	public int configureVolume() {

		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Volume Configuration");
		builder.setMessage("Volume will be configured please push movement button if there was a movement");

		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (which == -1) {
					repeat = false;
					vol.dismiss();
				} else if (which == -3) {
					volumeLvl++;
					vol.show();
				}
			}
		};

		builder.setPositiveButton("movement", listener);
		builder.setNeutralButton("no movement", listener);

		goThroughVolume();
		vol = builder.create();
		vol.show();

		return volumeLvl;
	}

	public void goThroughVolume() {

		for (int i = 0; i < 3; i++) {
			// dive.run();
			System.out.println("Testlauf " + 1);
		}
	}
	
	*/
}
