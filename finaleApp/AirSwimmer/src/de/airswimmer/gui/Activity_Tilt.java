package de.airswimmer.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Activity_Tilt extends BaseTilt {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// returns to StartPage of tilt if screen is touched
		ourView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ourView.stop();
				startActivity(new Intent(ourView.getContext(),
						Start_button_tilt.class));
				return true;

			}
		});
	}

	// method for movement of the fish
	public void move(float xAxis, float yAxis) {
		double delta = 3;
		boolean isFirstTiltUp = false;
		boolean isFirstTiltDown = false;
		boolean isFirstTiltLeft = false;
		boolean isFirstTiltRight = false;

		// Up
		if (yAxis < 0 - delta) {
			if (!isFirstTiltUp) {
				action.climbing();
				isFirstTiltUp = true;
			}
		} else {
			isFirstTiltUp = false;
		}

		// Down
		if (yAxis > 0 + delta) {
			if (!isFirstTiltDown) {
				action.diving();
				isFirstTiltDown = true;
			}
		} else {
			isFirstTiltDown = false;
		}

		// MoveLeft
		if (xAxis < 0 - delta) {
			if (!isFirstTiltLeft) {
				action.moveLeft();
				isFirstTiltLeft = true;
			}
		} else {
			isFirstTiltLeft = false;
		}

		// Move Right
		if (xAxis > 0 + delta) {
			if (!isFirstTiltRight) {
				action.moveRight();
				isFirstTiltRight = true;
			}
		} else {
			isFirstTiltRight = false;
		}

	}
}
