package com.example.beispiel03sensorfarbwechsel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	private SensorManager sensorManager;
	private View view;
	TextView beschleunigungen;
	TextView posWerte;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		view = findViewById(R.id.view1);
		view.setBackgroundColor(Color.LTGRAY);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		beschleunigungen = (TextView) findViewById(R.id.textBeschleunigungen);
		posWerte = (TextView) findViewById(R.id.textPosWerte);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			
			getAccelerometer(event);
			
		}
		
	}
	
	private void getAccelerometer(SensorEvent event){
		float[] values = event.values;
		
		float x = values[0];
		float y = values[1];
		float z = values[2];
		
		float xWert;
		float yWert;
		float zWert;
		
		int xWertInt; 
		int yWertInt;
		int zWertInt;
		
		beschleunigungen.setText("X: " +event.values[0]+ 
				"\nY: " + event.values[1] + "\nZ: " + event.values[2]);
		
		//negative werte "vernichten" abs -> Betrag
		xWert = Math.abs(event.values[0]);
		yWert = Math.abs(event.values[1]);
		zWert = Math.abs(event.values[2]);
		
		posWerte.setText("X: " +xWert+ 
				"\nY: " + yWert + "\nZ: " + zWert);
		
		//Zahlen vor kommer:
		xWertInt = (int) xWert;
		yWertInt = (int) yWert;
		zWertInt = (int) zWert;
		
	
		String xString ="";
		String yString ="";
		String zString ="";
		String myHexColor = "#";
		
		
		
		//(Integer.toHexString(10)
		
		//view.setBackgroundColor(0xfff0ff00);
		//switch-case um die WertInt zu den Farben 00-FF zu "wandeln
		switch(xWertInt){
		case 0:
			xString = "00";
			break;
		case 1:
			xString = "11";
			break;
		case 2:
			xString = "22";
			break;
		case 3:
			xString = "33";
			break;
		case 4:
			xString = "44";
			break;
		case 5:
			xString = "55";
			break;
		case 6:
			xString = "66";
			break;
		case 7:
			xString = "77";
			break;
		case 8:
			xString = "88";
			break;
		case 9:
			xString = "99";
			break;
		case 10:
			xString = "aa";
			break;
		case 11:
			xString = "bb";
			break;
		case 12:
			xString = "cc";
			break;
		case 13:
			xString = "dd";
			break;
		case 14:
			xString = "ee";
			break;
		case 15:
			xString = "ff";
			break;	
		}
		
		switch(yWertInt){
		case 0:
			yString = "00";
			break;
		case 1:
			yString = "11";
			break;
		case 2:
			yString = "22";
			break;
		case 3:
			yString = "33";
			break;
		case 4:
			yString = "44";
			break;
		case 5:
			yString = "55";
			break;
		case 6:
			yString = "66";
			break;
		case 7:
			yString = "77";
			break;
		case 8:
			yString = "88";
			break;
		case 9:
			yString = "99";
			break;
		case 10:
			yString = "aa";
			break;
		case 11:
			yString = "bb";
			break;
		case 12:
			yString = "cc";
			break;
		case 13:
			yString = "dd";
			break;
		case 14:
			yString = "ee";
			break;
		case 15:
			yString = "ff";
			break;	
		}
		
		
		switch(zWertInt){
		case 0:
			zString = "00";
			break;
		case 1:
			zString = "11";
			break;
		case 2:
			zString = "22";
			break;
		case 3:
			zString = "33";
			break;
		case 4:
			zString = "44";
			break;
		case 5:
			zString = "55";
			break;
		case 6:
			zString = "66";
			break;
		case 7:
			zString = "77";
			break;
		case 8:
			zString = "88";
			break;
		case 9:
			zString = "99";
			break;
		case 10:
			zString = "aa";
			break;
		case 11:
			zString = "bb";
			break;
		case 12:
			zString = "cc";
			break;
		case 13:
			zString = "dd";
			break;
		case 14:
			zString = "ee";
			break;
		case 15:
			zString = "ff";
			break;	
		}
		
		
		
		//jetzt "bauen" wir die myHexColor auf
		myHexColor = myHexColor + xString + yString + zString;

		view.setBackgroundColor(Color.parseColor(myHexColor));
		
		/*
		Toast toast = Toast.makeText(this, myHexColor, Toast.LENGTH_SHORT);
		toast.show();
		*/
		
	}
	
	
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    // register this class as a listener for the orientation and
	    // accelerometer sensors
	    sensorManager.registerListener(this,
	        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    // unregister listener
	    super.onPause();
	    sensorManager.unregisterListener(this);
	  }



}
