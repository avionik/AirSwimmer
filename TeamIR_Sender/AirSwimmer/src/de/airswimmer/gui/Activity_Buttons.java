package de.airswimmer.gui;

import android.os.Bundle;
import android.widget.TextView;

public class Activity_Buttons extends BaseButtons{

	/*TextView mCustomTitle;
	
	public void setTitle(CharSequence title) {
	    //you can override the other setTitle as well if you need it
	    mCustomTitle.setText("Einfache Tastensteuerung");        
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_layout_buttons);
		super.onCreate(savedInstanceState);   
	}
	
	@Override
	public void moveLeft() {
		// TODO implement one left move
		
	}

	@Override
	public void moveRight() {
		// TODO implement one right move
		
	}



}
