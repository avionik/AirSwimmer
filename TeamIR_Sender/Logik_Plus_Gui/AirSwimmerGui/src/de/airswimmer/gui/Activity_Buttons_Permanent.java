package de.airswimmer.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Buttons_Permanent extends BaseButtons {

	private boolean forwardMovement = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_layout_buttons_permanent);
		super.onCreate(savedInstanceState);
	}

	public void changeMoveState(View view) throws InterruptedException {
		Button buttonstart = (Button) view;

		if (forwardMovement) {

			forwardMovement = false;
			buttonstart.setText("Start");
			permanent_thread.wait(10000);
			Toast.makeText(view.getContext(), "stop", Toast.LENGTH_SHORT)
					.show();

		} else {
			buttonstart.setText("Stop");
			forwardMovement = true;
			
			
			permanent_thread.run();

			/*
			 * while(forwardMovement){ moveRight();
			 * Toast.makeText(view.getContext(), "move right",
			 * Toast.LENGTH_SHORT) .show(); SystemClock.sleep(waiting_time);
			 * moveLeft(); Toast.makeText(view.getContext(), "move left",
			 * Toast.LENGTH_SHORT) .show(); i++; if(i==8) {
			 * forwardMovement=false; }
			 * 
			 * }
			 */
		}

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
					// Thread should wait until AirSwimmer has moved to the left,
					// once
					Thread.sleep(waiting_time);
				} catch (InterruptedException e) {
					System.err
							.println("Exception while swimming forward after left move: "
									+ e);
				}

			}while(forwardMovement == false);
			
		}
		
		
	};

}
