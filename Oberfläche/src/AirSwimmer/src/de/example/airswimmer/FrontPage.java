package de.example.airswimmer;

import de.example.airswimmer.R;
import de.example.airswimmer.Activity_Buttons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

// FrontPage.java implements Activity-Functions of FrontPage

public class FrontPage extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.front_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	// What will happen when you click on a button
	public void onButtonClick(View view){
		switch(view.getId()){
			case R.id.Button_Buttons:
				startActivity(new Intent(this, Activity_Buttons.class));
				break;
			case R.id.Button_Tilt:
				startActivity(new Intent(this, Activity_Tilt.class));
				break;
			case R.id.Button_Wipe:
				startActivity(new Intent(this, Activity_Wipe.class));
			default:;
					
		
		}
	}

	
	

}
