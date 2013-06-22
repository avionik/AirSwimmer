package de.airswimmer.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// FrontPage.java implements Activity-Functions of FrontPage

public class FrontPage extends Activity {
	private SharedPreferences preferences;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		boolean firstStart;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.front_page);
		//check if frontpage is started the first time so reminder to irdroid adapter has to be shown
		Intent i = getIntent();
		firstStart = i.getBooleanExtra("firstStart", true);
		if (firstStart) {
			// reference for infrared transmitter
			final CharSequence[] answer = { getResources().getString(
					R.string.OKButton) };
			AlertDialog.Builder irReminderADBuilder = new AlertDialog.Builder(
					this)
					.setTitle(getResources().getString(R.string.alert_ir_title))
					.setCancelable(false)
					.setItems(answer, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int item) {
							String choice = (String) answer[item];
							if (choice == answer[0]) {

							} else if (choice == answer[1]) {

							}
						}
					});

			AlertDialog irReminderAlert = irReminderADBuilder.create();
			irReminderAlert.show();
		}
		preferences = getSharedPreferences("AirSwimmerPrefs",
				Context.MODE_WORLD_READABLE); // get preferences in file
												// AirSwimmerPrefs
		int layout = preferences.getInt("layout", -1); // get value for key
														// "layout" or -1 if
														// "layout" does not
														// exist
		if (layout == -1) { // if layout does not exist ask where aux is
			// strings for dialog
			String title = getResources().getString(R.string.alert_aux_title);
			String short_side = getResources()
					.getString(R.string.aux_shortSide);
			String long_side = getResources().getString(R.string.aux_longSide);
			// Alert Dialog
			final CharSequence[] possibleSides = { short_side, long_side }; //elements of dialog
			AlertDialog.Builder getAuxADBuilder = new AlertDialog.Builder(this)
					.setTitle(title)
					.setCancelable(false)
					.setItems(possibleSides,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int item) {
									SharedPreferences.Editor editor = preferences
											.edit();
									int layout = 0;
									// get value for requested screen
									// orientation
									if (item == 0) { // possibleSides[0] is
														// short side
										layout = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
									} else if (item == 1) {// possibleSides[1]
															// is long side
										layout = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
									}

									editor.putInt("layout", layout); // save
																		// requested
																		// screen
																		// orientation
																		// in
																		// preferences
									editor.commit();
									setRequestedOrientation(layout); // change
																		// screen
																		// orientation
																		// of
																		// frontPage
								}
							});

			AlertDialog getAuxAlert = getAuxADBuilder.create();
			getAuxAlert.show();
		} else {
			setRequestedOrientation(layout); // set screen orientation to stored
												// value
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);// Inflate the menu; this
														// adds items to the
														// action bar if it is
														// present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getGroupId() == R.id.submenu_change_layout_orientation) {// change
																			// screen
																			// orientation
			SharedPreferences.Editor editor = preferences.edit();
			int layout = 0;
			switch (item.getItemId()) { // get value for requested orientation
			case R.id.landscape:
				layout = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
				break;
			case R.id.portrait:
				layout = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
				break;
			default:
				layout = getRequestedOrientation(); // per default use current
													// orientation
				break;
			}
			editor.putInt("layout", layout); // save orientation in preferences
												// (AirSwimmerPrefs)
			editor.commit();
			setRequestedOrientation(layout); // set orientation of FrontPage to
												// requested orientation
			return true;
		} else if (item.getItemId() == R.id.sound_calc) {
			startActivity(new Intent(this, SetSoundActivity.class));
		} else if (item.getItemId() == R.id.info_page) {
			startActivity(new Intent(this, info_page.class));
		}
		return false;
	}

	// What will happen when you click on a button
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.Button_Button:
			startActivity(new Intent(this, Activity_Buttons.class));
			break;
		case R.id.Button_Tilt:
			startActivity(new Intent(this, Start_button_tilt.class));
			break;
		case R.id.Button_Slide:
			startActivity(new Intent(this, Activity_Slide.class));
			break;
		default:
			;
		}
		;

	}
}
