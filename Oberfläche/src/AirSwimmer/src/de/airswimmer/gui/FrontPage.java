package de.airswimmer.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

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
		return true;
	}
	
	// What will happen when you click on a button
	public void onButtonClick(View view){
		switch(view.getId()){
			case R.id.Button_Button:
				startActivity(new Intent(this, Activity_Buttons.class));
				break;
			case R.id.Button_Tilt:
				startActivity(new Intent(this, Activity_Tilt.class));
				break;
			case R.id.Button_Wipe:
				startActivity(new Intent(this, Activity_Wipe.class));
			default:;
			
			// Alert Diloge //TODO stürzt ab !!
			/*final CharSequence[] items = {"alert_up", "alert_right"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
			.setTitle("alert_title")
			.setCancelable(false)
			.setItems(items, new DialogInterface.OnClickListener() {
				
			@Override
			public void onClick(DialogInterface dialog, int item) {
			// TODO Auto-generated method stub
		    //Here the activity 
				
			Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			}
				
		});
			
			AlertDialog alert = builder.create();
			alert.show();*/	
		};
		
		
		}
		
	}
		
	
