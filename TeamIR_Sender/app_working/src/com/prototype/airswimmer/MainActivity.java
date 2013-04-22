package com.prototype.airswimmer;

import com.example.airswimmer.R;
import com.microcontrollerbg.irdroid.Lirc;

import android.media.AudioTrack;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	AudioTrack track;
	Thread t1 = null;
	short[] buffer = new short[1024];
	float samples[] = new float[1024];
	boolean play = false;
	final Lirc l = new Lirc();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button createBtn = (Button) findViewById(R.id.stopBtn);
		createBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				EditText txt = (EditText) findViewById(R.id.status);
				txt.setText("create pressed");
				stopAudio();
			}
		});

		Button playBtn = (Button) findViewById(R.id.playBtn);
		playBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				EditText txt = (EditText) findViewById(R.id.status);
				txt.setText("play pressed");
				playAudio();
			}
		});
		
		
		@SuppressWarnings("unused")
		Button parseLirc = (Button) findViewById(R.id.parseLirc);
		playBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				EditText txt = (EditText) findViewById(R.id.status);
				txt.setText("parse lirc");
				selectFile();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		l.parse("");
		return true;
	}

	// method for creating an audio file
	public boolean stopAudio() {

		t1.interrupt();

		EditText txt = (EditText) findViewById(R.id.status);
		txt.setText("audioTrack stopped");
		return true;
	}

	// method to play the audio file , communiucation with aux port of mobile
	// device
	public void playAudio() {
		System.out.println("start thread from main activity");
		EditText et = (EditText) findViewById(R.id.playTime);

		int playTime = Integer.parseInt(et.getText().toString());
		t1 = new AudioThread(playTime);
		t1.start();
		EditText txt = (EditText) findViewById(R.id.status);
		txt.setText("audioTrack plays");
	}
	
	 public String selectFile(){
			
		 
	    	final EditText ed = new EditText(this);
	    		
	        	Builder builder = new Builder(this);
	        	builder.setTitle("Select a file to parse");
	        	builder.setView(ed);
	        	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {  	
	    			public void onClick(DialogInterface dialog, int which) {
	    				if (which == Dialog.BUTTON_NEGATIVE) {
	    					dialog.dismiss();
	    					return;
	    				}
	    				l.parse(ed.getText().toString());
	    			}});    			
	    		builder.setNegativeButton("OK", null);
	    	   	final AlertDialog asDialog = builder.create();
	    	  	asDialog.show();  
	        	return null;
	 }
}
