package co.alectronic.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int buttonState;
    public Button btnTime;
    public TextView txtTime;
    public SeekBar skbar;
    public int timerMS;
    public CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonState = 0;
        btnTime = (Button)findViewById(R.id.btnTime);
        txtTime = (TextView)findViewById(R.id.txtTime);
        skbar = (SeekBar)findViewById(R.id.skbarTime);
        btnTime.setText(getState(buttonState));
        timerMS = 1000;


        skbar.setMax(600);
        skbar.setProgress(30);
        updateTime(30);

        skbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {updateTime(i);}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void buttonClick(View v){
        kickTimer();
        buttonState++;
        //Log.i("INFO",""+buttonState);
        //if(buttonState>2){buttonState=0;}
        if(buttonState>1){buttonState=0;}
        btnTime.setText(getState(buttonState));
    }


    private void updateTime(int i){ txtTime.setText(getMin(i)+":"+getSecAsString(getRemainSecs(i)));  }

    private int getMin(int sec){
        return (int) sec / 60;
    }

    private int getRemainSecs(int sec){
        return (int) sec % 60;
    }

    private String getSecAsString(int sec){
        String tmpStr = ""+sec;
        if(sec <= 9){ tmpStr = "0"+sec; }
        return tmpStr;
    }



    private void kickTimer(){
        switch (buttonState){
            case 0: //Start Timer
                startTimer();
                break;
            case 1: //Reset Timer
                resetTimer();
                break;
            case 2: // Stop Timer
                stopTimer();
                break;
        }
    }

    private void startTimer(){
        skbar.setEnabled(false);

        countDownTimer = new CountDownTimer(skbar.getProgress()*1000 + 100,1000){
            public void onTick(long millisecondUntilDone){//countdown is
                Log.i("Second Left", String.valueOf(millisecondUntilDone / 1000));
                updateTime((int)millisecondUntilDone / 1000);
            }


            public void onFinish(){//countdown is finished
                Log.i("Done","ITS THE FINAL COUNTDOWN!");
                //MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.noise);
                //mplayer.start();
                //
                txtTime.setText("BOOM!");
            }
        }.start();

    }
    private void resetTimer(){
        skbar.setEnabled(true);
        updateTime(skbar.getProgress());
        countDownTimer.cancel();
    }

    private void stopTimer(){}



    private int a(){return 0;}

    private String getState(int i){
        String tmpStr = "";
        switch (i){
            case 0: tmpStr = "Start";
                break;
            case 1: tmpStr = "Stop";
                break;
            case 2: tmpStr = "Reset";
                break;
        }
        return tmpStr;
    }

}
