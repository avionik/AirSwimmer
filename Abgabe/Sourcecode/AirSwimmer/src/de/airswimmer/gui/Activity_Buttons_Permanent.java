package de.airswimmer.gui;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity_Buttons_Permanent extends BaseButtons {

	private boolean forwardMovement = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_layout_buttons_permanent);
		super.onCreate(savedInstanceState);
	}
	
	// Change word of button
	public void changeMoveState(View view){
		Button button_start = (Button) view;
		if(forwardMovement){
			forwardMovement=false;
			button_start.setText("Start");
			// TODO stop forward movement
		}
		else {
			forwardMovement=true;
			button_start.setText("Stop");
			// TODO start forward movement
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

	// Runnable Interface with run-method
	// Used to realize permanent movement independent of surface
	/*
	 * Runnable permanent_thread = new Runnable() {
	 * 
	 * public void run() {
	 * 
	 * // loop of right and left moves do { moveRight(); try { // Thread should
	 * wait until AirSwimmer has moved to the // right, once
	 * Thread.sleep(waiting_time); } catch (InterruptedException e) { System.err
	 * .println("Exception while swimming forward after right move: " + e); }
	 * 
	 * moveLeft();
	 * 
	 * try { // Thread should wait until AirSwimmer has moved to the // left, //
	 * once Thread.sleep(waiting_time); } catch (InterruptedException e) {
	 * System.err .println("Exception while swimming forward after left move: "
	 * + e); } } while (forwardMovement == true);
	 * 
	 * }
	 * 
	 * };
	 */
}
