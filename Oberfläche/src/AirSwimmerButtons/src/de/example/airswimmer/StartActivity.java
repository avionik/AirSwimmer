package de.example.airswimmer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartActivity extends Activity implements OnClickListener {

	private ImageButton button_up;
	private ImageButton button_down;
	private ImageButton button_right;
	private ImageButton button_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		button_up = (ImageButton) findViewById(R.id.imageButtonUp);
		button_up.setOnClickListener(this);
		button_right = (ImageButton) findViewById(R.id.imageButtonRight);
		button_right.setOnClickListener(this);
		button_down = (ImageButton) findViewById(R.id.imageButtonDown);
		button_down.setOnClickListener(this);
		button_left = (ImageButton) findViewById(R.id.imageButtonLeft);
		button_left.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public void onClick(View v) { // what happens when you click a button
		if (v == button_up) { // if button_up was clicked
			Toast.makeText(v.getContext(), "up", Toast.LENGTH_SHORT).show(); // a text pops up for a short amount of time showing "up"
		} else if (v == button_down) {
			Toast.makeText(v.getContext(), "down", Toast.LENGTH_SHORT).show();
		} else if (v == button_right) {
			Toast.makeText(v.getContext(), "right", Toast.LENGTH_SHORT).show();
		} else if (v == button_left) {
			Toast.makeText(v.getContext(), "left", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Reaction to choice in menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Submenu of change_background
		// Picture sky is chosen as background
		case R.id.sky:
			return true;
			// Picture sky is chosen as background
		case R.id.water:
			return true;
		//Submenu of choose_mode
		case R.id.mode_remote:
			return true;
		case R.id.mode_shake:
			return true;
		case R.id.mode_slide:
			return true;
		default:
			return false;
		}
	}
};
