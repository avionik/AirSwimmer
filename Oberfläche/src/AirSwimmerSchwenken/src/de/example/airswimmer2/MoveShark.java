package de.example.airswimmer2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MoveShark extends Activity implements SensorEventListener {

	Bitmap shark;
	SensorManager sm;
	DrawShark ourView;
	float x, y, sensorX, sensorY;
	Bitmap mBitmap;
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) { 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) { // to able to detect the change of the sensor 
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sensorY = event.values[0];
		sensorX = event.values[1];
		
	} // class to handle the sensor events
	
	 public class DrawShark extends SurfaceView implements Runnable{

	        SurfaceHolder ourHolder;
	        Thread ourThread = null;
	        boolean isRunning = true;
	        
	        public DrawShark(Context context) {
	            super(context);
	            ourHolder = getHolder();
	        }

	        public void pause(){
	        	isRunning = false;
	        	while(true){
	        		try{
	        			ourThread.join();
	        			
	        		}catch(InterruptedException e){
	        			e.printStackTrace();
	        		}
	        		break;
	        	}
	        	ourThread = null;
	        }
	
	        public void resume(){
	            isRunning = true;
	            ourThread = new Thread(this);
	            ourThread.start();
	        }
	        
	        @Override
	        public void run() {
	            while (isRunning){
	                if (!ourHolder.getSurface().isValid()){
	                    continue;
	                }
	                
	                Display display = getWindowManager().getDefaultDisplay();    // to get the Window height and widt
	                int width = display.getWidth();
	                int height = display.getHeight();
	                
	                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_himmel); //new Bitmap to make the background image
	                int h = width;
	                int w = height;
	                
	                Bitmap scaled = Bitmap.createScaledBitmap(mBitmap, h, w, true );
	                
	                Canvas canvas = ourHolder.lockCanvas(); 
	                canvas.drawColor(Color.WHITE);
	                canvas.drawBitmap(scaled, 0,0, new Paint()); // set Background
	                float startX = 50;
	                float startY = 50;
	                canvas.drawBitmap(shark, startX +sensorX*10, startY+sensorY*10, null);
	                ourHolder.unlockCanvasAndPost(canvas);
	            }
	        }

	   }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // sensor listener to get the move of the device 
	        if (sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){
	            Sensor s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
	            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
	        }
	        shark = BitmapFactory.decodeResource(getResources(), R.drawable.ic_air_swimmers_shark);
	        x = y = sensorX = sensorY = 0;
	        ourView = new DrawShark(this);
	        ourView.resume();
	        setContentView(ourView);
	    }

	    //@Override
	 //   protected void onPause(){
	  //  	sm.unregisterListener(this);
	    //	super.onPause();
	   // }

}
