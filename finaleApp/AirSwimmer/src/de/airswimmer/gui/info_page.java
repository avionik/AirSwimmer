package de.airswimmer.gui;

import android.app.Activity;
import android.os.Bundle;

public class info_page extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ViewErzeugen(this));
	}

}
