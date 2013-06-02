package de.airswimmer.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

/**
 * Super class to handle a general menu.
 * 
 */
public class BaseActivity extends Activity {
	
	protected  MovementFunc action;
	protected AudioManager audio;
	@Override
	//handles screen orientation
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences prefs = getSharedPreferences("AirSwimmerPrefs",
				Context.MODE_PRIVATE); // get preferences which are stored in
										// file AirSwimmerPrefs
		int layout = prefs.getInt("layout", -1);
		if (layout != -1) {
			setRequestedOrientation(layout); //set screen orientation of current activity to stored value
		}
		
		//Init for Audiotrack 
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		action = new MovementFunc(this,audio,prefs);
		action.initialise();
		super.onCreate(savedInstanceState);
	}

	/**
	 * Creates Menu from control.xml and make current mode invisible in menu.
	 * Only called once per activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		// get name of current activity
		String currentActivityPath = this.getClass().getSuperclass().getName();
		if (currentActivityPath.endsWith("BaseActivity")) {
			currentActivityPath = this.getClass().getName();
		}
		String currentActivityName = currentActivityPath
				.substring(currentActivityPath.lastIndexOf(".") + 1);
		// find id to class name
		int id = 0;
		if (currentActivityName.equals("BaseButtons")) {
			id = R.id.mode_buttons;
		} else if (currentActivityName.equals("BaseTilt")) {
			id = R.id.mode_tilt;
		} else if (currentActivityName.equals("BaseSlide")) {
			id = R.id.mode_slide;
		}
		// if id was found set current screen invisible so only different modes
		// are visible
		if (id != 0) {
			menu.findItem(id).setVisible(false);
		}
		return true;
	}

	/**
	 * Reaction to choice in menu
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		String currentActivityPath = this.getClass().getName();
		String currentActivityName = currentActivityPath
				.substring(currentActivityPath.lastIndexOf(".") + 1);

		// reaction to selected menupoint in submenu for changing background
		// picture
		if (item.getGroupId() == R.id.submenu_changeBackground) {
			RelativeLayout background = (RelativeLayout) findViewById(R.id.layout);
			Resources source = getResources();
			switch (item.getItemId()) {
			case R.id.sky:// Picture sky is chosen as background
				background.setBackgroundDrawable(source
						.getDrawable(R.drawable.ic_sky));
				return true;
			case R.id.water:
				background.setBackgroundDrawable(source
						.getDrawable(R.drawable.sea));
				return true;
			case R.id.th_picture:
				background.setBackgroundDrawable(source
						.getDrawable(R.drawable.th_ingolstadt));
				return true;

			default:
				return false;
			}
			// reaction to selected menupoint in submenu for changing control
			// mode
		} else if (item.getGroupId() == R.id.submenu_changeMode) {
			switch (item.getItemId()) {
			case R.id.mode_buttons:
				startActivity(new Intent(this, Activity_Buttons.class));
				return true;
			case R.id.mode_tilt:
				startActivity(new Intent(this, Activity_Tilt.class));
				return true;
			case R.id.mode_slide:
				startActivity(new Intent(this, Activity_Slide.class));
				return true;
			default:
				return false;
			}
		}
		// reaction to selected menupoint in submenu for changing
		// permanent/single move

		// For Buttons
		if (currentActivityName.equals(getResources().getString(
				R.string.title_activity_activity__buttons))) {
			if (item.getGroupId() == R.id.submenu_changeMove) {

				switch (item.getItemId()) {
				case R.id.permanent:
					startActivity(new Intent(this,
							Activity_Buttons_Permanent.class));
					return true;
				case R.id.single:
					startActivity(new Intent(this, Activity_Buttons.class));
					return true;
				default:
					return false;
				}

			}
		} else if (item.getGroupId() == R.id.submenu_changeMove
				&& currentActivityName.equals(getResources().getString(
						R.string.title_activity_activity__buttons_permanent))) {

			if (item.getGroupId() == R.id.submenu_changeMove) {
				switch (item.getItemId()) {
				case R.id.permanent:
					startActivity(new Intent(this,
							Activity_Buttons_Permanent.class));
					return true;
				case R.id.single:
					startActivity(new Intent(this, Activity_Buttons.class));
					return true;
				default:
					return false;
				}
			}
		}

		// For Slide
		else if (currentActivityName.equals(getResources().getString(
				R.string.title_activity_activity__slide))
				|| currentActivityName.equals(getResources().getString(
						R.string.title_activity_activity__slide_permanent))) { // if
																				// one
																				// of
																				// the
																				// slide
																				// activities
																				// is
																				// active
			if (item.getGroupId() == R.id.submenu_changeMove) {

				switch (item.getItemId()) {
				case R.id.permanent:
					startActivity(new Intent(this,
							Activity_Slide_Permanent.class));
					return true;
				case R.id.single:
					startActivity(new Intent(this, Activity_Slide.class));
					return true;
				default:
					return false;
				}

			}
		}

		// For Tilt
		else if (currentActivityName.equals(getResources().getString(
				R.string.title_activity_activity__tilt))) {
			if (item.getGroupId() == R.id.submenu_changeMove) {

				switch (item.getItemId()) {
				case R.id.permanent:
					// * TODO --> name of new permanent tilt class
					startActivity(new Intent(this,
							Start_button_tilt.class));
					return true;
				case R.id.single:
					startActivity(new Intent(this, Activity_Tilt.class));
					return true;
				default:
					return false;
				}

			}
		} else if (item.getGroupId() == R.id.submenu_changeMove
				&& currentActivityName.equals(getResources().getString(
						R.string.title_activity_activity__tilt_permanent))) {

			if (item.getGroupId() == R.id.submenu_changeMove) {
				switch (item.getItemId()) {
				case R.id.permanent:
					// * TODO --> name of new permanent tilt class
					startActivity(new Intent(this,
							Activity_Tilt_Permanent.class));
					return true;
				case R.id.single:
					startActivity(new Intent(this, Activity_Tilt.class));
					return true;
				default:
					return false;
				}
			}
		}

		return false;
	}
}
