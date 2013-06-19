package de.airswimmer.gui;


import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class SetSoundActivity extends Activity implements OnClickListener{
    
    private Button okbutton;
    private SeekBar bar;
    private int value;
    private boolean stopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sound);
        
        okbutton = (Button) findViewById(R.id.OKButton);                //set button
        okbutton.setOnClickListener(this);                              //set listener
        okbutton.performClick();                                        //calls onClickListener
        
        bar = (SeekBar) findViewById(R.id.SoundBar);                    //set SeekBar
        
        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()    //set and create OnSeekBarChangeListener
        {
        	
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){     //Notification that value from SeekBar has changed

                value = seekBar.getProgress();                          //save progress
                                                       
            } 

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {         //Notification that user started touch gesture
            	  
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {          //Notification that user stopped touch gesture
                
            }
        });
             
    }
    
    protected void onStart(){
    	
    	super.onStart();
    	bar.setProgress(0);

    new Thread(new Runnable() {
        @Override
        public void run() {
        	
        	try {
                Thread.sleep(5000);
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
        	bar.setProgress(1);
        	
        	for(int i=1;i<16;i++){ 
        		
        		if(stopped==true){
                  	 break;
                }
        		
                bar.setProgress(i);
                //comand send signal move_fish
                
                try {
                Thread.sleep(2000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
                
                value = i;
            }
        	
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	
                }
            });
        }
    }).start();
    
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_sound, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v == okbutton){
            okbutton.setOnTouchListener(new OnTouchListener(){              //create OnClickListener for okbutton

                @Override
                public boolean onTouch(View v, MotionEvent event) {         //called when screen is touched
                    
                    if (event.getAction() == MotionEvent.ACTION_DOWN){      //if button is pressed
                    	stopped = true;
                        okbutton.setBackgroundColor(Color.GRAY);            //change button colour (so you can see that button has been pushed)
                        int value = bar.getProgress();
                        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, value, 0);
                        
                        SharedPreferences preferences = getSharedPreferences("AirSwimmerPrefs",
                        		Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = preferences
                        		.edit();
                        		editor.putInt("voice", value);
                        		editor.commit();
                 
                        return true;
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP){   //if button is released
                        okbutton.setBackgroundColor(Color.LTGRAY);          //change button colour again (so you can see that button has been released)
                        return true;
                    }
                    
                    return false;
                }
                
            });
        }
        
    }

}
