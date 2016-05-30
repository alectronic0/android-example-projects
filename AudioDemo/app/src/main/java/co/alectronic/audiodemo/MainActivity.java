package co.alectronic.audiodemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager aManager;
    SeekBar volumeControl;
    SeekBar seekControl;
    //TextView txtSeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayer = MediaPlayer.create(this, R.raw.hammering);
        aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        volumeControl = (SeekBar)findViewById(R.id.volumeBar);
        volumeControl.setMax(aManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeControl.setProgress(aManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                aManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
                //mPlayer.seekTo(progress);
                //txtSeek.setText("" + progress);
            }
        });

        seekControl = (SeekBar)findViewById(R.id.seekBar);
        seekControl.setMax(mPlayer.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekControl.setProgress(mPlayer.getCurrentPosition());
            }
        },0,100);
        seekControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
                mPlayer.seekTo(progress);
                //txtSeek.setText("" + progress);
            }
        });


    }

    public void audioClick(View v){


        if(((ToggleButton) v).isChecked()){  mPlayer.start();}
        else{mPlayer.pause();}
    }



}
