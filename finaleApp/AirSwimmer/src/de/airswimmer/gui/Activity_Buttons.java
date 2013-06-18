package de.airswimmer.gui;

import android.os.Bundle;

public class Activity_Buttons extends BaseButtons{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_layout_buttons);
		super.onCreate(savedInstanceState);   
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



}
