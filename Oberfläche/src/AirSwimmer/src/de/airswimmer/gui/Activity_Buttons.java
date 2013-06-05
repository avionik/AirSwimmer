package de.airswimmer.gui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Activity_Buttons extends BaseButtons{
	
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}



}
