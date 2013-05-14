package de.airswimmer.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class Start_button_tilt extends BaseActivity implements OnClickListener{

	private ImageButton Button_start_tilt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_button_tilt);
		
		Button_start_tilt = (ImageButton) findViewById(R.id.start_button_tilt1);
		Button_start_tilt.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { // what happens when you click a button
		if (v == Button_start_tilt) { 
		
			startActivity(new Intent(this, Activity_Tilt_Permanent.class));
		}
	}
}