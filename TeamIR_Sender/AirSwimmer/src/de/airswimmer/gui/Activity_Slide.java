package de.airswimmer.gui;

/**
 * Class for wiping where one slide causes one move of airswimmer
 * 
 */

public class Activity_Slide extends BaseSlide {

    // method for movement of the fish
    public void move(int xAxis, int yAxis) {
int height = getWindowManager().getDefaultDisplay().getHeight() - 295;
		
		if (yAxis > height / 2) {

			action.sendCommand(Commands.CLIMB.name());

		} else if (yAxis < height / 2) {

			action.sendCommand(Commands.DIVE.name());

		}

    }

}
