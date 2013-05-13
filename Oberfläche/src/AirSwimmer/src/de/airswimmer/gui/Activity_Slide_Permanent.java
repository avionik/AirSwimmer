package de.airswimmer.gui;

import android.view.MotionEvent;
import android.view.View;



/**
 * Class for wiping where shark is swimming permanently and sliding causes change of direction
 * 
 */
public class Activity_Slide_Permanent extends BaseSlide {
    boolean swimmForward = false; //indicates if AirSwimmer has to swim forward
    @Override
    protected boolean moveImage(View view, MotionEvent event) {
        switch(event.getActionMasked()){ //checks if screen is touched so AirSwimmer has to swim
        case MotionEvent.ACTION_UP:swimmForward=false;

        break;
        case MotionEvent.ACTION_DOWN:
            swimmForward=true;     
        }
        return super.moveImage(view, event); // moves image of shark
    }

    
    @Override
    public void move(int xAxis, int yAxis) {
        // TODO Implement movement: swimmForward=true -->AirSwimmer has to swim
    }

}
