package co.alectronic.eggtimer;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonState = 0;
        btnTime = (Button)findViewById(R.id.btnTime);
        txtTime = (TextView)findViewById(R.id.txtTime);
        skbar = (SeekBar)findViewById(R.id.skbarTime)
        btnTime.setText(getState(buttonState));
        timerMS = 1000;
    }



    public void buttonClick(View v){
        kickTimer();
        buttonState++;
        //Log.i("INFO",""+buttonState);
        if(buttonState>2){buttonState=0;}
        btnTime.setText(getState(buttonState));
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

        new CountDownTimer(10000,1000){
            public void onTick(long millisecondUntilDone){//countdown is
                Log.i("Second Left", String.valueOf(millisecondUntilDone / 1000));
                timerMS = timerMS - 10;
                txtTime.setText(""+timerMS);
            }


            public void onFinish(){//countdown is finished
                Log.i("Done","ITS THE FINAL COUNTDOWN!");
            }
        }.start();

    }
    private void stopTimer(){}
    private void resetTimer(){}


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
