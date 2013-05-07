package de.airswimmer.gui;



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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class BaseTilt extends BaseActivity implements SensorEventListener {

	Bitmap shark;
	SensorManager sm;
	DrawShark ourView;
	float sensorX, sensorY;
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
	        int background = R.drawable.ic_sky;
	        
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
	        
	        
	        /**
	         * Function to change Background picture
	         * @param background id of new background drawable
	         */
	        public void changeBackground(int background){
	        	this.background=background;
	        }
	        
	        /**
	         * Stops DrawShark thread
	         */
	        public void stop(){
	        	isRunning=false;
	        }
	        private void drawShark(){
	         
                
                Display display = getWindowManager().getDefaultDisplay();    // to get the Window height and widt
                int width = display.getWidth();
                int height = display.getHeight();
                
                mBitmap = BitmapFactory.decodeResource(getResources(), background); //new Bitmap to make the background image
                int h = width;
                int w = height;
                
                Bitmap scaled = Bitmap.createScaledBitmap(mBitmap, h, w, true );
                
                Canvas canvas = ourHolder.lockCanvas(); 
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(scaled, 0,0, new Paint()); // set Background
                float startX = 350;
                float startY = 150;
                float offsetX = (float) ((100.0/360.0*sensorX)*width/4);
                float offsetY = (float) ((100.0/360.0*sensorY)*height/4);
        
                canvas.drawBitmap(shark, startX +offsetX, startY+offsetY, null);
                ourHolder.unlockCanvasAndPost(canvas);
	        }
	        
	        @Override
	        public void run() {
	            while (isRunning){
	            	if (!ourHolder.getSurface().isValid()){
	            		continue;
	            	}
	               drawShark();
	               if(sensorX>0){
	            	   //TODO implement ?forward? movement
	               }
	               if(sensorY<0){
	            	   //TODO implement ?right? curve
	               }else if (sensorY>0){
	            	   //TODO implement ?left? curve
	               }
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
	        shark = BitmapFactory.decodeResource(getResources(), R.drawable.bellishark_1_medium);
	        sensorX = sensorY = 0;
	        ourView = new DrawShark(this);
	        ourView.resume();
	        setContentView(ourView);
	    }
	    
	    @Override
	    public void onResume(){
	    	ourView.resume();
	    	super.onResume();
	    }

	    /**
	     * Overrides changeBackground so bitmap is changed instead of background.
	     */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	if (item.getGroupId() == R.id.submenu_changeBackground) {
	    		int id=0;
	    		switch (item.getItemId()) {
	    		 // set Background
				case R.id.sky:// Picture sky is chosen as background
					id=R.drawable.ic_sky;
			       break;
				case R.id.water:
					id=R.drawable.sea;
					break;
				case R.id.th_picture:
					id=R.drawable.th_ingolstadt;
					break;
				default: 
					return false;
			}		
					ourView.changeBackground(id);
			        return true;
	    	}else if(item.getGroupId()==R.id.submenu_changeMode){
	    		ourView.stop(); //stops drawing thread before mode is changed	
	    	}
	    	return super.onOptionsItemSelected(item);
	    }
	    
	    
	    /**
	     * Stops drawing thread before execute step back
	     */
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if(keyCode==KeyEvent.KEYCODE_BACK){
	    		ourView.stop();
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }

	 // method for movement of the fish
	    public abstract void move(int xAxis, int yAxis);
	    // TODO: implement movement
	    
}
