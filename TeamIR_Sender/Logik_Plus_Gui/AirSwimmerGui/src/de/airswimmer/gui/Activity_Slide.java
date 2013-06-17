package de.airswimmer.gui;

import android.os.SystemClock;

/**
 * Class for wiping where one slide causes one move of airswimmer
 * 
 */

public class Activity_Slide extends BaseSlide {

	// method for movement of the fish
	public void move(int xAxis, int yAxis) {

		@SuppressWarnings("deprecation")
		int max_width = getWindowManager().getDefaultDisplay().getWidth()-325; 
		@SuppressWarnings("deprecation")
		int max_height = getWindowManager().getDefaultDisplay().getHeight()-295;
		int min_width = 0;
		int min_height = 0;
		int m_width = max_width/2;
		int m_height = max_height/2;
		
		//fish is situated in the upper region
		if((yAxis > min_height && yAxis < m_height) && (xAxis > max_width*0.33 && xAxis < max_width*0.66)){
			//action.climbing();
			//action.finishClimbing();
			System.out.println("climb");
		}else if((yAxis > m_height && yAxis < max_height) && (xAxis > max_width*0.33 && xAxis < max_width*0.66)){
			action.diving();
			action.finishDiving();
			System.out.println("dive");
		}else if((xAxis > m_width && xAxis < max_width)&& (yAxis > max_height*0.33 && yAxis <  max_height*0.66)){
			action.moveRight();
			
			action.finishMovingRight();
			System.out.println("right");
		}else if((xAxis > min_width && xAxis < m_width)&&(yAxis > max_height*0.33 && yAxis <  max_height*0.66)){
			action.moveLeft();
			
			action.finishMovingLeft();
			System.out.println("left");
		}
	}
	
}
