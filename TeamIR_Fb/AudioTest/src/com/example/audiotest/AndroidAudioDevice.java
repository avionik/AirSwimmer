package com.example.audiotest;

import android.media.*;

public class AndroidAudioDevice
{
   AudioTrack track;
   short[] buffer = new short[1024];
 
   @SuppressWarnings("deprecation")
public AndroidAudioDevice( )
   {
      int minSize =AudioTrack.getMinBufferSize( 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT );        
      track = new AudioTrack( AudioManager.STREAM_MUSIC, 44100, 
                                        AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, 
                                        minSize, AudioTrack.MODE_STREAM);
      long time = System.currentTimeMillis();
      track.play();
   }	   
 
   public void writeSamples(float[] samples) 
   {	
      fillBuffer( samples );
      track.write( buffer, 0, samples.length );
   }
 
   private void fillBuffer( float[] samples )
   {
      if( buffer.length < samples.length )
         buffer = new short[samples.length];
 
      for( int i = 0; i < samples.length; i++ )
         buffer[i] = (short)(samples[i] * Short.MAX_VALUE);;
   }		
}