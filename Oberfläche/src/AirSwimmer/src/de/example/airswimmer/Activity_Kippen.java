package de.example.airswimmer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;


public class Activity_Kippen extends Activity {
	
	DrawShark ourShark;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_layout_kippen); 
		ourShark = new DrawShark(this);
		setContentView(ourShark);
		Intent ourIntent = new Intent(this, MoveShark.class);
		startActivity(ourIntent);
	}

}
