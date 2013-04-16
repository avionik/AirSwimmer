package de.example.airswimmer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DrawShark extends SurfaceView { //SurfaceView is to speeding up is used for gaming 

	Bitmap shark; // variable for the image 
	SurfaceHolder ourHolder; // holds the surface to
	
	public DrawShark(Context context) { // constructor allow to share information with main(represents this)
		super(context);
		shark = BitmapFactory.decodeResource(getResources(), R.drawable.ic_air_swimmers_shark); // define image ,able too draw on the screen
		
	}


	@Override //to Draw the shark
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(shark, 50, 50, null);
	}
	
	

}
