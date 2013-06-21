package de.airswimmer.gui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public abstract class BaseButtons extends BaseActivity implements
		OnTouchListener {

	private ImageButton button_up;
	private ImageButton button_down;
	private ImageButton button_right;
	private ImageButton button_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		button_up = (ImageButton) findViewById(R.id.imageButtonUp);
		button_up.setOnTouchListener(this);
		button_right = (ImageButton) findViewById(R.id.imageButtonRight);
		button_right.setOnTouchListener(this);
		button_down = (ImageButton) findViewById(R.id.imageButtonDown);
		button_down.setOnTouchListener(this);
		button_left = (ImageButton) findViewById(R.id.imageButtonLeft);
		button_left.setOnTouchListener(this);

		// Set Colors
		button_down.setBackgroundColor(Color.TRANSPARENT);
		button_up.setBackgroundColor(Color.TRANSPARENT);
		button_left.setBackgroundColor(Color.TRANSPARENT);
		button_right.setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) { // what happens when you
														// click a button
		if (v == button_up) { // if button_up was clicked

			button_up.getAnimation();
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				climb();

				// action while pressing the button down
				button_up.setBackgroundColor(Color.BLUE);

				return true;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				// action while pressing the button up
				button_up.setBackgroundColor(Color.TRANSPARENT);

				return true;
			}

			return false;

		} else if (v == button_down) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				dive();

				button_down.setBackgroundColor(Color.BLUE);
				

				return true;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				button_down.setBackgroundColor(Color.TRANSPARENT);

				return true;
			}

			return false;

		} else if (v == button_right) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				moveRight();

				button_right.setBackgroundColor(Color.BLUE);
				

				return true;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				button_right.setBackgroundColor(Color.TRANSPARENT);

				return true;
			}
			return false;

		} else if (v == button_left) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				moveLeft();

				button_left.setBackgroundColor(Color.BLUE);
				

				return true;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				button_left.setBackgroundColor(Color.TRANSPARENT);

				return true;
			}
			return false;
		}
		
		return true;
	}

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void dive();

	public abstract void climb();
}
