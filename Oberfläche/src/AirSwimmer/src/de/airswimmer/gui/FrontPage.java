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
	
	AlertDialog alert;
	AlertDialog alert2;
	
	private SharedPreferences preferences;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.front_page);
		preferences = getSharedPreferences("AirSwimmerPrefs",
				Context.MODE_WORLD_READABLE); //get preferences in file AirSwimmerPrefs
		int layout = preferences.getInt("layout", -1); //get value for key "layout" or -1 if "layout" does not exist

		//reference for infrared transmitter
		final CharSequence[] answer = { "OK" };					
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
				.setTitle("Bitte schlieﬂen Sie, falls noch nicht geschen, einen Infrarotsender an !").setCancelable(false)
				.setItems(answer, new DialogInterface.OnClickListener() {
					
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						String choice = (String) answer[item];
						if (choice == answer[0]) { 
							
						} else if (choice == answer[1]) {
							
						}
						alert2.dismiss();
						
					}
				});

		alert2 = builder2.create();
		
		alert2.show();
	
		
		
		if (layout == -1) { //if layout does not exist ask where aux is
			// Alert Dialog
			
			final CharSequence[] items = { "kurze Seite", "lange Seite" };

		
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setTitle("W‰hle AUX-Eingang").setCancelable(false)
					.setItems(items, new DialogInterface.OnClickListener() 

					{
						
						@Override
						public void onClick(DialogInterface dialog, int item) {
							
							SharedPreferences.Editor editor = preferences
									.edit();
									
							String choice = (String) items[item];
							int layout = 0;
							if (choice == items[0]) { //get value for requested screen orientation
								layout = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
							} else if (choice == items[1]) {
								layout = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
							}

							editor.putInt("layout", layout); //save requested screen orientation in preferences
							editor.commit();
							setRequestedOrientation(layout); //change screen orientation of frontPage
							alert.dismiss();
						}
						
					});
			

			alert = builder.create();
			alert.show();
			
			setRequestedOrientation(layout); //set screen orientation to stored value	 
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
		if (item.getGroupId() == R.id.submenu_change_layout_orientation) {//change screen orientation
			SharedPreferences.Editor editor = preferences.edit();
			int layout = 0;
			switch (item.getItemId()) { //get value for requested orientation
			case R.id.landscape:
				layout = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
				break;
			case R.id.portrait:
				layout = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
				break;
			default:
				layout = getRequestedOrientation(); //per default use current orientation
				break;
			}
			editor.putInt("layout", layout);	//save orientation in preferences (AirSwimmerPrefs)
			editor.commit();
			setRequestedOrientation(layout);	//set orientation of FrontPage to requested orientation
			return true;
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
		default:
			;

		}
		;

	}
}
