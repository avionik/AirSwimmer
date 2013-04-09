package de.example.airswimmer;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity implements OnClickListener{

    private Button button_up;      //Attribute button_up has type ImageButton
    private Button button_down;
    private Button button_right;
    private Button button_left;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) { //gets called right after app started
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);    //set the layout, R is a generated class by Java, it's not allowed to edit it!
        button_up = (Button) findViewById(R.id.button_up);         //set buttons (so they can be used)
        button_down = (Button) findViewById(R.id.button_down);
        button_right = (Button) findViewById(R.id.button_right);
        button_left = (Button) findViewById(R.id.button_left);
       
        //add buttons to the onClickListener so something can happen when you click on a button
        button_up.setOnClickListener((android.view.View.OnClickListener) this);
        button_down.setOnClickListener((android.view.View.OnClickListener) this);
        button_right.setOnClickListener((android.view.View.OnClickListener)this);
        button_left.setOnClickListener((android.view.View.OnClickListener) this);
    }


    @Override
    public void onClick(View v) { //what happens when you click a button
        
        if (v == button_up){ //if button_up was clicked
            Toast.makeText(v.getContext(), "up", Toast.LENGTH_SHORT).show();    //a text pops up for a short amount of time showing "up"
        }
        else if (v == button_down){
            Toast.makeText(v.getContext(), "down", Toast.LENGTH_SHORT).show();
        }
        else if (v == button_right){
            Toast.makeText(v.getContext(), "right", Toast.LENGTH_SHORT).show();
        }
        else if (v == button_left){
            Toast.makeText(v.getContext(), "left", Toast.LENGTH_SHORT).show();
        }
    }
}
