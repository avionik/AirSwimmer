package de.example.airswimmer2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;


public class MainActivity extends Activity {
	
	DrawShark ourShark;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ourShark = new DrawShark(this);
		setContentView(ourShark);
		Intent ourIntent = new Intent(this, MoveShark.class);
		startActivity(ourIntent);
	}

}
