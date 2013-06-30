package de.airswimmer.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SetSoundActivity extends BaseActivity implements OnClickListener {

	private Button okbutton;
	private SeekBar bar;
	private int value;
	private boolean stopped = false;
	private SharedPreferences prefs;
	private Editor editor;
	protected FrontPage frontPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		prefs = getSharedPreferences("AirSwimmerPrefs", Context.MODE_PRIVATE);
		editor = prefs.edit();
		editor.putInt("voice", 0);
		editor.commit();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_sound);
		//set layout orientation
		final SharedPreferences prefs = getSharedPreferences("AirSwimmerPrefs",
				Context.MODE_PRIVATE); // get preferences which are stored in
										// file AirSwimmerPrefs
		int layout = prefs.getInt("layout", -1);

		if (layout != -1) {
			setRequestedOrientation(layout); // set screen orientation of current activity to stored value
		}

		okbutton = (Button) findViewById(R.id.OKButton); // set button
		okbutton.setOnClickListener(this); // set listener
		okbutton.performClick(); // calls onClickListener

		bar = (SeekBar) findViewById(R.id.SoundBar); // set SeekBar
		bar.setEnabled(false); // prevent user to change bar progress

		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // set and
																		// create
																		// OnSeekBarChangeListener
		{

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) { // Notification that value from SeekBar
										// has changed

				value = seekBar.getProgress(); // save progress
				editor.putInt("voice", value);
				editor.commit();

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { // Notification
																// that user
																// started touch
																// gesture

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) { // Notification
																// that user
																// stopped touch
																// gesture

			}
		});

	}

	protected void onStart() {

		super.onStart();
		bar.setProgress(0);

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				bar.setProgress(1);

				for (int i = 1; i < 16; i++) {

					if (stopped == true) {
						break;
					}

					bar.setProgress(i);
					// comand send signal move_fish
					for (int j = 0; j < 5; j++) {
					    
					    if (stopped == true){  //stop sending if ok-click occured while going through for-loop
					        break;
					    }
					    
						action.diving();

						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					value = i;
					try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

					}
				});
			}
		}).start();

	}

	@Override
	public void onClick(View v) {

		if (v == okbutton) {
			okbutton.setOnTouchListener(new OnTouchListener() { // create
																// OnClickListener
																// for okbutton

				@Override
				public boolean onTouch(View v, MotionEvent event) { // called
																	// when
																	// screen is
																	// touched

					if (event.getAction() == MotionEvent.ACTION_DOWN) { // if
																		// button
																		// is
																		// pressed
						stopped = true;
						okbutton.setBackgroundColor(Color.GRAY); // change
																	// button
																	// colour
																	// (so you
																	// can see
																	// that
																	// button
																	// has been
																	// pushed)
						value = bar.getProgress();
						AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
						audioManager.setStreamVolume(
								AudioManager.STREAM_SYSTEM, value, 0);

						editor.putInt("voice", value);
						editor.commit();
						return true;

					} else if (event.getAction() == MotionEvent.ACTION_UP) { // if
																				// button
																				// is
																				// released
						okbutton.setBackgroundColor(Color.LTGRAY); // change
																	// button
																	// colour
																	// again (so
																	// you can
																	// see that
																	// button
																	// has been
																	// released)
						Intent front = new Intent(SetSoundActivity.this,
								FrontPage.class);
						front.putExtra("firstStart", false);
						startActivity(front);
						return true;
					}

					return false;
				}

			});
		}

	}
	
    /**
     * Stops thread before execute step back
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stopped=true;
        }
        return super.onKeyDown(keyCode, event);
    }

}