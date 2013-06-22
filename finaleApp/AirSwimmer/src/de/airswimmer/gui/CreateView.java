package de.airswimmer.gui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class CreateView extends SurfaceView {

	private SurfaceHolder surfaceHolder;
	private Bitmap bmp;
	private int y = 670;
	private info_page_thread theGameLoopThread;
	private int x = 100;

	public CreateView(Context context) {
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
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), 0x7f020006), 0, 0, null);
		if (y >= getHeight() - bmp.getHeight()) {
			y = y - 2;
		}else if(y <= getHeight() - bmp.getHeight()) {
			y = y - 2;
		}
		canvas.drawBitmap(bmp, x, y, null);
	}

}
