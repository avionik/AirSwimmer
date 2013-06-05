package de.airswimmer.gui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public abstract class BaseButtons extends BaseActivity implements OnTouchListener {

	private ImageButton button_up;
	private ImageButton button_down;
	private ImageButton button_right;
	private ImageButton button_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		button_up = (ImageButton) findViewById(R.id.imageButtonUp);
		button_up.setOnTouchListener(this);
		action.climbListener(button_up);
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
		*/
		button_up = (ImageButton) findViewById(R.id.imageButtonUp);
		action.climbListener(button_up);
		
		button_down = (ImageButton) findViewById(R.id.imageButtonDown);
		action.diveListener(button_down);
		
		button_right = (ImageButton) findViewById(R.id.imageButtonRight);
		action.rightListener(button_right);
		
		button_left = (ImageButton) findViewById(R.id.imageButtonLeft);
		action.leftListener(button_left);
		
	}
/*
	@Override
	public boolean onTouch(View v, MotionEvent event) { // what happens when you click a button    
		if (v == button_up) { // if button_up was clicked
			// TODO command up (shark lifts)
			button_up.getAnimation();
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						
						// action while pressing the button down
						button_up.setBackgroundColor(Color.BLUE);	
						Toast.makeText(v.getContext(), "up", Toast.LENGTH_SHORT).show(); // a text pops up for a short amount of time showing "up"
							
						return true;
					}else if(event.getAction() == MotionEvent.ACTION_UP){
						
						// action while pressing the button up
						button_up.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					
					return false;

		} else if (v == button_down) {
			// TODO command down (shark is diving)

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						
						button_down.setBackgroundColor(Color.BLUE);							
						Toast.makeText(v.getContext(), "down", Toast.LENGTH_SHORT).show();
						
						return true;
					}else if(event.getAction() == MotionEvent.ACTION_UP){
						
						button_down.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					
					return false;

		} else if (v == button_right) {

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						moveRight();
						button_right.setBackgroundColor(Color.BLUE);	
						Toast.makeText(v.getContext(), "right",Toast.LENGTH_SHORT).show();
						
						return true;
					}else if(event.getAction() == MotionEvent.ACTION_UP){
						
						button_right.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					return false;


		} else if (v == button_left) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						moveLeft();
						button_left.setBackgroundColor(Color.BLUE);	
						Toast.makeText(v.getContext(), "left",Toast.LENGTH_SHORT).show();
						
						return true;
					}else if(event.getAction() == MotionEvent.ACTION_UP){
						
						button_left.setBackgroundColor(Color.TRANSPARENT);
						
						return true;
					}
					return false;
				}
		return true;
	}
	*/
	public abstract void moveLeft();
	public abstract void moveRight();
	
}
