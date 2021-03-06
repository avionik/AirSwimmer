package de.airswimmer.gui;


import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public abstract class BaseButtons extends BaseActivity implements OnClickListener {

	private ImageButton button_up;
	private ImageButton button_down;
	private ImageButton button_right;
	private ImageButton button_left;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		button_up = (ImageButton) findViewById(R.id.imageButtonUp);
		action.climbListener(button_up);
		
		button_right = (ImageButton) findViewById(R.id.imageButtonRight);
		action.climbListener(button_right);
		
		button_down = (ImageButton) findViewById(R.id.imageButtonDown);
		action.climbListener(button_down);
		
		button_left = (ImageButton) findViewById(R.id.imageButtonLeft);
		action.climbListener(button_left);
		
		
		//Perform the first click
		//button_down.performClick();
		//button_up.performClick();
		//button_right.performClick();
		//button_left.performClick();
		
		// Set Colors
		button_down.setBackgroundColor(Color.TRANSPARENT);
		button_up.setBackgroundColor(Color.TRANSPARENT);
		button_left.setBackgroundColor(Color.TRANSPARENT);
		button_right.setBackgroundColor(Color.TRANSPARENT);
		// initialise Audiotrack, read config File and check some important options 
	}
	
	@Override
	public void onClick(View v) { // what happens when you click a button
		    
	    
		if (v == button_up) { // if button_up was clicked
			// TODO command up (shark lifts)
			button_up.getAnimation();
			button_up.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						
						// action while pressing the button down
						button_up.setBackgroundColor(Color.BLUE);	
						Toast.makeText(v.getContext(), "up", Toast.LENGTH_SHORT).show(); // a text pops up for a short amount of time showing "up"
							
						return true;
					}
					if(event.getAction() == MotionEvent.ACTION_UP){
						
						// action while pressing the button up
						button_up.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					
					return false;
				}
			});

		} else if (v == button_down) {
			// TODO command down (shark is diving)

			button_down.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						
						button_down.setBackgroundColor(Color.BLUE);							
						Toast.makeText(v.getContext(), "down", Toast.LENGTH_SHORT).show();
						
						return true;
					}
					if(event.getAction() == MotionEvent.ACTION_UP){
						
						button_down.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					
					return false;
				}
				
			});

		} else if (v == button_right) {

			button_right.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						moveRight();
						button_right.setBackgroundColor(Color.BLUE);	
						Toast.makeText(v.getContext(), "right",Toast.LENGTH_SHORT).show();
						
						return true;
					}
					if(event.getAction() == MotionEvent.ACTION_UP){
						
						button_right.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					return false;
				}
			});

		} else if (v == button_left) {
			button_left.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						moveLeft();
						button_left.setBackgroundColor(Color.BLUE);	
						Toast.makeText(v.getContext(), "left",Toast.LENGTH_SHORT).show();
						
						return true;
					}
					if(event.getAction() == MotionEvent.ACTION_UP){
						
						button_left.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					return false;
				}
			});

		}
	}
	public abstract void moveLeft();
	public abstract void moveRight();
}
