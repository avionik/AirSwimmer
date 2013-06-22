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

public abstract class BaseTilt extends BaseActivity implements
		SensorEventListener {

	private Bitmap shark;
	private SensorManager sm;
	protected DrawShark ourView;
	private float sensorX, sensorY;
	private Bitmap mBitmap;


	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) { // to able to detect the
														// change of the sensor
		sensorY = event.values[0];
		sensorX = event.values[1];

	} // class to handle the sensor events

	// thread to draw moving picture of shark
	public class DrawShark extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread = null;
		boolean isRunning = true;
		int background = R.drawable.ic_sky;

		public DrawShark(Context context) {
			super(context);
			ourHolder = getHolder();
		}

		@Override
		public void run() {
			while (isRunning) {
				move(sensorX, sensorY);
				if (!ourHolder.getSurface().isValid()) {
					continue;
				}
				drawSharkImage();
			}
		}

		// pauses DrawShark thread
		public void pause() {
			isRunning = false;
			while (true) {
				try {
					ourThread.join();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			ourThread = null;
		}

		// resumes DrawShark thread
		public void resume() {
			isRunning = true;
			ourThread = new Thread(this);
			ourThread.start();
		}

		// Stops DrawShark thread
		public void stop() {
			isRunning = false;
		}

		/**
		 * Function to change Background picture
		 * 
		 * @param background
		 *            id of new background drawable
		 */
		public void changeBackground(int background) {
			this.background = background;
		}

		//draws image of shark which position moves while target is tilted
		private void drawSharkImage() {
			double delay = 0.4; // sensitivity of sensor
			Display display = getWindowManager().getDefaultDisplay(); // to get
																		// the
																		// Window
																		// height
																		// and
																		// width
			@SuppressWarnings("deprecation")
			// deprecated function has to be used because of target android
			// version
			int width = display.getWidth();
			@SuppressWarnings("deprecation")
			// deprecated function has to be used because of target android
			// version
			int height = display.getHeight();

			mBitmap = BitmapFactory.decodeResource(getResources(), background); // new
																				// Bitmap
																				// to
																				// make
																				// the
																				// background
																				// image

			Bitmap scaled = Bitmap.createScaledBitmap(mBitmap, width, height,
					true);

			Canvas canvas = ourHolder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(scaled, 0, 0, new Paint()); // set Background

			// calculate new position of shark
			float startX = 350;
			float startY = 150;
			float offsetX = 0;
			float offsetY = 0;
			// ignore tilt between ]-delay;delay[
			if (sensorX > delay) { // if sensor value x is bigger than delay
									// start moving picture
				offsetX = (float) ((100.0 / 360.0 * (sensorX - delay)) * width / 4);
			} else if (sensorX < delay * -1) { // if sensor value x is smaller
												// than -delay start moving
												// picture
				offsetX = (float) ((100.0 / 360.0 * (sensorX + delay)) * width / 4);
			}
			if (sensorY > delay) { // if sensor value y is bigger than delay
									// start moving picture
				offsetY = (float) ((100.0 / 360.0 * (sensorY - delay)) * width / 4);
			} else if (sensorY < delay * -1) { // if sensor value y is smaller
												// than -delay start moving
												// picture
				offsetY = (float) ((100.0 / 360.0 * (sensorY + delay)) * width / 4);
			}

			canvas.drawBitmap(shark, startX + offsetX, startY + offsetY, null);
			ourHolder.unlockCanvasAndPost(canvas);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // sensor
																		// listener
																		// to
																		// get
																		// the
																		// move
																		// of
																		// the
																		// device
		if (sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			Sensor s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
		}
		shark = BitmapFactory.decodeResource(getResources(),
				R.drawable.bellishark_1_medium);
		sensorX = sensorY = 0;

		ourView = new DrawShark(this);
		ourView.resume();
		setContentView(ourView);
	}

	/**
	 * restarts thread for drawing image each time activity is reactivated
	 */
	@Override
	public void onResume() {
		ourView.resume();
		super.onResume();
	}

	/**
	 * Overrides changeBackground so bitmap is changed instead of background.
	 * Stops thread if menu changes activity.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getGroupId() == R.id.submenu_changeBackground) {
			int id = 0;
			switch (item.getItemId()) {
			// set Background
			case R.id.sky:// Picture sky is chosen as background
				id = R.drawable.ic_sky;
				break;
			case R.id.water:
				id = R.drawable.sea;
				break;
			case R.id.th_picture:
				id = R.drawable.th_ingolstadt;
				break;
			default:
				return false;
			}
			ourView.changeBackground(id);
			return true;
		} else if (item.getGroupId() == R.id.submenu_changeMode
				|| item.getGroupId() == R.id.submenu_changeMove||item.getItemId()==R.id.frontPage) {
			ourView.stop(); // stops drawing thread before mode is changed
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Stops drawing thread before execute step back
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ourView.stop();
		}
		return super.onKeyDown(keyCode, event);
	}

	// method to move fish

	public abstract void move(float xAxis, float yAxis);
}
