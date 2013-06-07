package de.airswimmer.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.microcontrollerbg.irdroid.Lirc;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Movement extends Activity {

	protected Handler mHandler = new Handler();
	private byte buffer[];
	private Lirc lirc = new Lirc();
	private SharedPreferences mPrefs;
	private static final String nameInLirc = "AirSwimmer2013";
	private static final String nameOfLirc = "lirc.txt";
	
	private BaseActivity caller;
	private int bufSize;
	AudioTrack ir;
	private AudioManager audio;

	@SuppressWarnings("deprecation")
	public Movement(BaseActivity caller, AudioManager audio, SharedPreferences pref) {
		this.audio = audio;
		this.caller = caller;
		mPrefs = pref;
		bufSize = AudioTrack.getMinBufferSize(45000,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_8BIT);
		init();
	}
	
	@SuppressWarnings("deprecation")
	public void init(){

		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);
		audio = (AudioManager) caller.getSystemService(Context.AUDIO_SERVICE);

		int now = getVolume();
		if (audio.isBluetoothA2dpOn()) {

			audio.setBluetoothA2dpOn(true);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);

			audio = (AudioManager) caller
					.getSystemService(Context.AUDIO_SERVICE);
		} else {
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, now, 0);
			audio = (AudioManager) caller.getSystemService(Context.AUDIO_SERVICE);
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
	private String importLircfile() {
		String newFolder = "/AirSwimmer";
		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		// create directory
		File dir = new File(extStorageDirectory + newFolder + "/Lirc.txt");
		if (!dir.isDirectory()) {

			File myNewFolder = new File(extStorageDirectory + newFolder);
			myNewFolder.mkdir();
			// create lirc file
			File f = new File(extStorageDirectory + newFolder + "/Lirc.txt");
			try {

				//final InputStream is = getResources().getAssets().open(nameOfLirc);
				final InputStream is = caller.getAssets().open(nameOfLirc);
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
	private boolean readConfigFile(String filepath) {

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
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
		ir.play();
		//System.out.println(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
		System.out.println(command + " sent successfully!");

	}
	
	
	protected int getVolume() {
		return mPrefs.getInt("volume", 50);
	}
	
	
	public void diving(){
		String mycmd = Commands.DIVE.name();
		try {
			sendCommand(mycmd);
		} catch (IllegalStateException e) {
			System.err.println("Exception while diving: " + e);
		}

		mHandler.postAtTime(dive, SystemClock.uptimeMillis());
	}
	
	public void finishDiving(){
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

	public void climbing(){
		String mycmd = Commands.CLIMB.name();
		try {
			sendCommand(mycmd);
		} catch (IllegalStateException e) {
			System.err.println("Exception while climbing: " + e);
		}

		mHandler.postAtTime(climb, SystemClock.uptimeMillis());
	}
	
	public void finishClimbing(){
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
	
	public void moveLeft(){
		String mycmd = Commands.TAILLEFT.name();
		try {
			sendCommand(mycmd);
		} catch (IllegalStateException e) {
			System.err.println("Exception while swimming left: " + e);
		}

		mHandler.postAtTime(left, SystemClock.uptimeMillis());
	}
	
	public void finishMovingLeft(){
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
	
	public void moveRight(){
		String mycmd = Commands.TAILRIGHT.name();
		try {
			sendCommand(mycmd);
		} catch (IllegalStateException e) {
			System.err.println("Exception while swimming right: " + e);
		}

		mHandler.postAtTime(right, SystemClock.uptimeMillis());
	}
	
	public void finishMovingRight(){
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

	
	// thread declaration
		Runnable dive = new Runnable() {
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
}
