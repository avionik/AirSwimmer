package de.airswimmer.gui;



import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Activity_Wipe extends BaseActivity {
    
    private View selected_item = null;
    private int offset_x = 0;
    private int offset_y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_wipe);                 //set Layout
        ViewGroup vg = (ViewGroup) findViewById(R.id.layout);       //set ViewGroup
        vg.setOnTouchListener(new View.OnTouchListener(){       //set OnTouchListener for ViewGroup

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView img = (ImageView) findViewById(R.id.img);
                switch (event.getActionMasked()){
                case MotionEvent.ACTION_MOVE:                   //when view moves
                    int xAxis = (int) ((int)  event.getRawX() - (offset_x / 1.2));  //calculate position of fish in xAxis, '/' to prevent fish from 'jumping' away from finger
                    int yAxis = (int) ((int)  event.getRawY() - (offset_y * 1.3));  //calculate position of fish in yAxis, '*' to prevent fish from 'jumping' away from finger
                    
                    int[] viewCoords = new int[2];
                    img.getLocationOnScreen(viewCoords);
                    int imageX = xAxis - viewCoords[0];         //viewCoords[0] is the X coordinate
                    int imageY = yAxis - viewCoords[1];         //viewCoords[1] is the Y coordinate
                    
                    if (imageX <= 100 && imageX > -100 && imageY <= 100 && imageY > -100){          //check, if movement is in the image
                        move(xAxis, yAxis);                         //method for movement of the fish is called
                        
                        @SuppressWarnings("deprecation")
                        int width = getWindowManager().getDefaultDisplay().getWidth() - 325;        //get screen size ('-' so the fish stays the same size)
                        @SuppressWarnings("deprecation")
                        int height = getWindowManager().getDefaultDisplay().getHeight() - 295;

                        if (xAxis > width)                          //if fish is dragged too far to the left/right
                            xAxis = width;
                        
                        if (yAxis > height)                         //if fish is dragged too far up/down
                            yAxis = height;
                        
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        //when picture moves, the view gets created again
                        lp.setMargins(xAxis, yAxis, 0, 0);
                        selected_item.setLayoutParams(lp);
                        break;
                    }
                    
                    default:                                    //when nothing moves
                        break;
                }
                return true;
            }
        });
        ImageView img = (ImageView) findViewById(R.id.img);     //fish image is initialized
        img.setOnTouchListener(new View.OnTouchListener() {     //onTouchListener for fish
            
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:                   //if someone touches the fish
                    offset_x = (int) event.getX();              //calculate new offset
                    offset_y = (int) event.getY();
                    selected_item = view;                       //view is selected
                    break;
                    
                    default:                                    //fish is not touched
                        break;
                }
                return false;
            }
        });
    }

    
    //method for movement of the fish
    public void move(int xAxis, int yAxis){
        //TODO: implement movement
    }
    
}
