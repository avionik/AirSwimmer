package de.airswimmer.gui;

import android.graphics.Canvas;

public class info_page_thread extends Thread {

	private CreateView theView;
	private boolean isRunning = false;

	public info_page_thread(CreateView theView) {
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
