package de.airswimmer.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.microcontrollerbg.irdroid.Lirc;

public class Movement extends Activity {

	private byte buffer[];
	private Lirc lirc = new Lirc();
	private SharedPreferences mPrefs;
	private static final String nameInLirc = "AirSwimmer2013";
	private static final String nameOfLirc = "lirc.txt";
	final Handler mHandler;
	private BaseActivity caller;
	private int bufSize;
	private AudioTrack ir;
	private AudioManager audio;


	public Movement(BaseActivity caller, AudioManager audio,
			SharedPreferences pref, Handler mHandler) {
		this.audio = audio;
		this.caller = caller;
		this.mHandler = mHandler;
		mPrefs = pref;
		bufSize = AudioTrack.getMinBufferSize(45000,
				AudioFormat. CHANNEL_IN_STEREO,
				AudioFormat.ENCODING_PCM_8BIT);
		init();
	}

	public void init() {

		int currentVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume / 2, 0);

		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 45000,
				AudioFormat.CHANNEL_IN_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		// get lirc config file for the air swimmer remote
		if (readConfigFile(importLircfile())) {
			System.out.println("Read and wrote Lirc-File successfully");
			// Configure Volume Level
			// configureVolume();
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

				// final InputStream is =
				// getResources().getAssets().open(nameOfLirc);
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

	public void sendCommand(String command) {
		int volLevel = mPrefs.getInt("voice", 6);
		// expects the name of the Lirc file (not filename, name is stated in
		// the file) & the command which should be executed
		buffer = lirc.getIrBuffer(nameInLirc, command);

		if (buffer == null) {
			System.err.println("error, buffer empty");
			return;
		}
		ir = new AudioTrack(AudioManager.STREAM_MUSIC, 45000,
				AudioFormat.CHANNEL_IN_STEREO,
				AudioFormat.ENCODING_PCM_8BIT, bufSize, AudioTrack.MODE_STATIC);

		if (bufSize < buffer.length)
			bufSize = buffer.length;

		ir.write(buffer, 0, buffer.length);

		ir.setStereoVolume(1, 1);
		
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, volLevel, 0);
		ir.play();
		System.out.println(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
		System.out.println(command + " sent successfully!");

	}

	protected int getVolume() {
		return mPrefs.getInt("volume", 50);
	}

	public void diving() {

		Thread thread = new Thread() {
			@Override
			public void run() {
				System.out.println("dive pressed runnable");
				if (ir != null) {
					ir.flush();
					ir.release();

					String cmd = Commands.DIVE.name();
					try {
						sendCommand(cmd);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();
	}


	public void climbing() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				System.out.println("climb pressed runnable");
				if (ir != null) {
					ir.flush();
					ir.release();

					String cmd = Commands.CLIMB.name();
					try {
						sendCommand(cmd);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();
	}

	public void moveLeft() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				System.out.println("left pressed runnable");
				if (ir != null) {
					ir.flush();
					ir.release();

					String cmd = Commands.TAILLEFT.name();
					try {
						sendCommand(cmd);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();
	}


	public void moveRight() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				System.out.println("right pressed runnable");
				if (ir != null) {
					ir.flush();
					ir.release();

					String cmd = Commands.TAILRIGHT.name();
					try {
						sendCommand(cmd);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();
	}

}
