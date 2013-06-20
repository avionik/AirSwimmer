package de.airswimmer.gui;

import android.graphics.Canvas;

public class info_page_thread extends Thread {

	private ViewErzeugen theView;
	private boolean isRunning = false;

	public info_page_thread(ViewErzeugen theView) {
		this.theView = theView;
	}

	public void setRunning(boolean run) {
		isRunning = run;
	}

	@Override
	public void run() {
		while (isRunning) {
			Canvas theCanvas = null;

			try {
				theCanvas = theView.getHolder().lockCanvas();
				synchronized (theView.getHolder()) {
					theView.onDraw(theCanvas);
				}
			} finally {
				if (theCanvas != null) {
					theView.getHolder().unlockCanvasAndPost(theCanvas);
				}
			}
		}
	}

}
