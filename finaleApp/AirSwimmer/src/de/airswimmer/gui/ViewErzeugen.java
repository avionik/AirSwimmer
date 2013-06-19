package de.airswimmer.gui;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class ViewErzeugen extends SurfaceView {

	private SurfaceHolder surfaceHolder;
	private Bitmap bmp;
	private Bitmap mbmp;
	//private String mText;
	private int y = 670;
	private info_page_thread theGameLoopThread;
	private int x = 100;
	
	//buttom, left, right, top
	
	//private Rect src = new Rect(0, 0, bmp.getWidth() , bmp.getHeight());
    //private Rect dst = new Rect(0,0,0,0);

	public ViewErzeugen(Context context) {
		super(context);
		theGameLoopThread = new info_page_thread(this);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(new SurfaceHolder.Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				theGameLoopThread.setRunning(false);
				while (retry) {
					try {
						theGameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {

					}
				}
			}

			public void surfaceCreated(SurfaceHolder holder) {
				theGameLoopThread.setRunning(true);
				theGameLoopThread.start();

			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), 0x7f02000f);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), 0x7f020006), 0, 0, null);

		// bild bewegen
		int bildHoehe = bmp.getHeight();
		int bildBreite = bmp.getWidth();
		
		/*
		for(int i = 0; i< (bildHoehe + 1) ; i ++){
			
			//buttom, left, right, top
			//dst.intersect(0,0,0,i);
			
			//Bitmap.createBitmap
			//.setPixel(..,.. , getPixel() , ...);
			//createBitmap(Bitmap source, int x, int y, int width, int height)
			mbmp.createBitmap(bmp, x, y, bildBreite, i);
			canvas.drawBitmap(mbmp, x, y, null);
		}
		*/
		/*
		for(int i = 0; i< (bildHoehe + 1) ; i ++){
			mbmp.createBitmap(bmp, x, y, bildBreite, i);
			canvas.drawBitmap(mbmp, x, y, null);
		}
		
		*/

		
		if (y >= getHeight() - bmp.getHeight()) {
			y = y - 2;
		}else if(y <= getHeight() - bmp.getHeight()) {
			y = y - 2;
		}
		//x = ((getWidth()-bildBreite)/2)
		canvas.drawBitmap(bmp, x, y, null);

		// canvas.drawText(mText, 70, y, null);

		/*
		 * while(true){ y = y + 3; canvas.drawBitmap(bmp, 25, y, null); }
		 */
	
	}

}
