package de.example.airswimmer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity implements OnClickListener {
	
	private ImageButton oben;
	private ImageButton unten;
	private ImageButton rechts;
	private ImageButton links;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		oben = (ImageButton) findViewById(R.id.imageButtonOben);
		oben.setOnClickListener(this);
	    rechts = (ImageButton) findViewById(R.id.imageButtonRechts);
		rechts.setOnClickListener(this);
	    unten = (ImageButton) findViewById(R.id.imageButtonUnten);
		unten.setOnClickListener(this);
		links = (ImageButton) findViewById(R.id.imageButtonLinks);
		links.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}


	
	@Override
	public void onClick(View v) {
		
		if(v == oben || v == rechts || v == links || v == unten){
				
			v.getId();
			
		}
		
	}
};



