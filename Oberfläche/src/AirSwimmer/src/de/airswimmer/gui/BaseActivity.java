package de.airswimmer.gui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

/**
 * Super class to handle a general menu.
 *
 */
public class BaseActivity extends Activity {
	
	/**
	 * Creates Menu from start.xml and make current mode invisible in menu. Only called once.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		//get name of current activity
		String currentActivityPath = this.getClass().getName();
		String currentActivityName = currentActivityPath.substring(currentActivityPath.lastIndexOf(".")+1);
		//find id to class name
		int id=0;
		if(currentActivityName.equals(getResources().getString(R.string.title_activity_activity__buttons))){
			id = R.id.mode_buttons;
		}else if(currentActivityName.equals(getResources().getString(R.string.title_activity_activity__tilt))){
			id=R.id.mode_tilt;
		}else if(currentActivityName.equals(getResources().getString(R.string.title_activity_activity__wipe))){
			id=R.id.mode_wipe;
		}
		//if id was found set current screen invisible so only different modes are visible
		if(id!=0){
			menu.findItem(id).setVisible(false);
		}
		return true;
	}

	
	/**
	 * Reaction to choice in menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//reaction to selected menupoint in submenu for changing background picture
		if (item.getGroupId() == R.id.submenu_changeBackground) {
			RelativeLayout background = (RelativeLayout) findViewById(R.id.layout);
			Resources source = getResources();
			switch (item.getItemId()) {
				case R.id.sky:// Picture sky is chosen as background
					background.setBackgroundDrawable(source.getDrawable(R.drawable.ic_sky));
					return true;
				case R.id.water:
					background.setBackgroundDrawable(source.getDrawable(R.drawable.sea));
					return true;
				default: 
					return false;
			}
			//reaction to selected menupoint in submenu for changing control mode
		} else if (item.getGroupId() == R.id.submenu_changeMode) {
			switch (item.getItemId()) {
				case R.id.mode_buttons:
					startActivity(new Intent(this, Activity_Buttons.class));
					return true;
				case R.id.mode_tilt:
					startActivity(new Intent(this, Activity_Tilt.class));
					return true;
				case R.id.mode_wipe:
					startActivity(new Intent(this, Activity_Wipe.class));
					return true;
				default:
					return false;
			}
		}
			return false;
	}

}
