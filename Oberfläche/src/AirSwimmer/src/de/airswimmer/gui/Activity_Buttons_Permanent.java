package de.airswimmer.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_Buttons_Permanent extends BaseButtons {

	private boolean forwardMovement = false;
	/*TextView mCustomTitle;
	
	public void setTitle(CharSequence title) {
	    //you can override the other setTitle as well if you need it
	    mCustomTitle.setText("Permanente Tastensteuerung");        
	}*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_layout_buttons_permanent);
		super.onCreate(savedInstanceState);
	}
	
	public void changeMoveState(View view){
		Button button_start = (Button) view;
		if(forwardMovement){
			forwardMovement=false;
			button_start.setText("Start");
			//TODO stop forward movement
			
		}else{
			forwardMovement=true;
			button_start.setText("Stop");
			//TODO start forward movement
		}
	}

	@Override
	public void moveLeft() {
		// TODO implement left curve
		
	}

	@Override
	public void moveRight() {
		// TODO implement right curve
		
	}

}
