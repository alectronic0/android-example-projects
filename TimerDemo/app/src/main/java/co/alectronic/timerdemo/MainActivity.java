package co.alectronic.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final Handler handleit = new Handler();
        Runnable runForest = new Runnable() {
            @Override
            public void run() {
                Log.i("INFO","Run Forest Run!");
                handleit.postDelayed(this,1000);
            }
        };
        handleit.post(runForest);
        */

        new CountDownTimer(10000,1000){
            public void onTick(long millisecondUntilDone){//countdown is
                Log.i("Second Left", String.valueOf(millisecondUntilDone / 1000));
                }
            public void onFinish(){//countdown is finished
                Log.i("Done","ITS THE FINAL COUNTDOWN!");
                    }
        }.start();


    }

}
