package de.airswimmer.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Activity_Tilt_Permanent extends BaseTilt {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//returns to StartPage of tilt if screen is touched
		ourView.setOnTouchListener(new View.OnTouchListener() { 
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ourView.stop();
				startActivity(new Intent(ourView.getContext(),
						Start_button_tilt_Permanent.class));
				return true;

			}
		});
	}

	// method for movement of the fish
	public void move(int xAxis, int yAxis) {
		// TODO: implement movement
	}
}
