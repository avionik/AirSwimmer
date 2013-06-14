package de.airswimmer.gui;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Buttons_Permanent extends BaseButtons {

	private boolean forwardMovement = true;
	private Button buttonStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_layout_buttons_permanent);
		super.onCreate(savedInstanceState);
		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				buttonStart.setText("stop");
				while(forwardMovement){
					swim();
				}
				buttonStart.setText("start");
				
			}
		});
	}

	public boolean swim() {
		
		if (forwardMovement) {
			forwardMovement = false;

		} else {
			
			forwardMovement = true;
			moveLeft();
			action.finishMovingLeft();
			SystemClock.sleep(waiting_time);
			moveRight();
			action.finishMovingRight();
			
			
		}
		return false;

	}

	@Override
	public void moveLeft() {
		action.moveLeft();

	}

	@Override
	public void moveRight() {
		action.moveRight();

	}

	@Override
	public void dive() {
		action.diving();

	}

	@Override
	public void climb() {
		action.climbing();

	}

	private Runnable permanent_thread = new Runnable() {

		public void run() {

			// loop of right and left moves
			do {
				moveRight();
				try {
					// Thread should wait until AirSwimmer has moved to the
					// right, once
					Thread.sleep(waiting_time);
				} catch (InterruptedException e) {
					System.err
							.println("Exception while swimming forward after right move: "
									+ e);
				}

				moveLeft();

				try {
					// Thread should wait until AirSwimmer has moved to the
					// left,
					// once
					Thread.sleep(waiting_time);
				} catch (InterruptedException e) {
					System.err
							.println("Exception while swimming forward after left move: "
									+ e);
				}
				action.finishMovingLeft();
			} while (forwardMovement == true);

		}

	};

}
