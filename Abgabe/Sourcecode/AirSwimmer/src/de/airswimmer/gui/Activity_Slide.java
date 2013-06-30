package de.airswimmer.gui;


/**
 * Class for wiping where one slide causes one move of airswimmer
 * 
 */

public class Activity_Slide extends BaseSlide {
	
	boolean moved_left = false;
	boolean moved_right = false;
	int prevX, prevY;
	
	// method for movement of the fish
	public void move(int xAxis, int yAxis) {
		
		if((Math.abs(prevX - xAxis) < 20) && (Math.abs(prevY - yAxis) < 20)){
			return;
		}
		@SuppressWarnings("deprecation")
		int max_width = getWindowManager().getDefaultDisplay().getWidth()-325; 
		@SuppressWarnings("deprecation")
		int max_height = getWindowManager().getDefaultDisplay().getHeight()-295;
		int min_width = 0;
		int min_height = 0;
		int m_width = max_width/2;
		int m_height = max_height/2;
		
		
		
		//fish is situated in the upper region
		if((yAxis > min_height && yAxis < m_height -70) && (xAxis > max_width*0.33 && xAxis < max_width*0.66)){
			moved_right = false;
			moved_left = false;
			
			action.climbing();
		
			System.out.println("climb");
			
		}else if((yAxis > m_height  && yAxis < max_height) && (xAxis > max_width*0.33 && xAxis < max_width*0.66)){
			moved_right = false;
			moved_left = false;
			action.diving();
			
			System.out.println("dive");
			
		}else if((xAxis > m_width +70  && xAxis < max_width) && (yAxis > max_height*0.33 && yAxis <  max_height*0.66)){
			
			if(!moved_right){
				moved_right = true;
				moved_left = false;
				action.moveRight();
				System.out.println("right");
				
			}
			
		}else if((xAxis > min_width && xAxis < m_width -70)&&(yAxis > max_height*0.33 && yAxis <  max_height*0.66)){
			if(!moved_left){
				moved_left = true;
				moved_right = false;
				action.moveLeft();
				System.out.println("left");
				
			}
		}
		prevX = xAxis;
		prevY = yAxis;
	}
}
