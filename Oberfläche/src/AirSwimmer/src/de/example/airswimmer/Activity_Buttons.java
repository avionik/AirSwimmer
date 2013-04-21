package de.example.airswimmer;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Activity_Buttons extends BaseActivity implements OnClickListener{

	private ImageButton button_up;
	private ImageButton button_down;
	private ImageButton button_right;
	private ImageButton button_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_buttons);

		button_up = (ImageButton) findViewById(R.id.imageButtonUp);
		button_up.setOnClickListener(this);
		button_right = (ImageButton) findViewById(R.id.imageButtonRight);
		button_right.setOnClickListener(this);
		button_down = (ImageButton) findViewById(R.id.imageButtonDown);
		button_down.setOnClickListener(this);
		button_left = (ImageButton) findViewById(R.id.imageButtonLeft);
		button_left.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) { // what happens when you click a button
		if (v == button_up) { // if button_up was clicked
			//TODO
			Toast.makeText(v.getContext(), "up", Toast.LENGTH_SHORT).show(); // a text pops up for a short amount of time showing "up"
		} else if (v == button_down) {
			//TODO
			Toast.makeText(v.getContext(), "down", Toast.LENGTH_SHORT).show();
		} else if (v == button_right) {
			//TODO
			Toast.makeText(v.getContext(), "right", Toast.LENGTH_SHORT).show();
		} else if (v == button_left) {
			//TODO
			Toast.makeText(v.getContext(), "left", Toast.LENGTH_SHORT).show();
		}
	}
}
