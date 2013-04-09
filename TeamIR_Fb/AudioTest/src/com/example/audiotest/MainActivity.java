package com.example.audiotest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	      super.onCreate(savedInstanceState);                      
	 
	      new Thread( new Runnable( ) 
	      {
	         public void run( )
	         {        		
	            final float frequency = 440;
	            float increment = (float)(2*Math.PI) * frequency / 44100; // angular increment for each sample
	            float angle = 0;
	            AndroidAudioDevice device = new AndroidAudioDevice( );
	            float samples[] = new float[1024];
	 
	            while( true )
	            {
	               for( int i = 0; i < samples.length; i++ )
	               {
	                  samples[i] = (float)Math.sin( angle );
	                  angle += increment;
	               }
	 
	               device.writeSamples( samples );
	            }        	
	         }
	      } ).start();
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
