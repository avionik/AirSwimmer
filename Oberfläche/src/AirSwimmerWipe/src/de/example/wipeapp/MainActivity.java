package de.example.wipeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    
    private View selected_item = null;
    private int offset_x = 0;
    private int offset_y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                 //set Layout
        ViewGroup vg = (ViewGroup) findViewById(R.id.vg);       //set ViewGroup
        vg.setOnTouchListener(new View.OnTouchListener(){       //set OnTouchListener for ViewGroup

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                case MotionEvent.ACTION_MOVE:                   //when view moves
                    int x = (int) event.getX() - offset_x;
                    int y = (int) event.getY() - offset_y;
                    
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    //when picture moves, the view gets created again
                    lp.setMargins(x, y, 0, 0);
                    selected_item.setLayoutParams(lp);
                    break;
                    
                    default:                                    //when nothing moves
                        break;
                }
                return true;
            }
        });
        ImageView img = (ImageView) findViewById(R.id.img);     //fish image is initialized
        img.setOnTouchListener(new View.OnTouchListener() {     //onTouchListener for fish
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:                   //if someone touches the fish
                    offset_x = (int) event.getX();              //calculate new offset
                    offset_y = (int) event.getY();
                    selected_item = v;                          //view is selected
                    break;
                    
                    default:                                    //fish is not touched
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
