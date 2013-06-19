package de.airswimmer.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class Start_button_tilt_Permanent extends BaseActivity implements OnClickListener{

	private ImageButton Button_start_tilt_permanent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_button_tilt_permanent);
		
		Button_start_tilt_permanent = (ImageButton) findViewById(R.id.start_button_tilt1);
		Button_start_tilt_permanent.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { // what happens when you click a button
		if (v == Button_start_tilt_permanent) { 
		
			startActivity(new Intent(this, Activity_Tilt_Permanent.class));
		}
	}
}