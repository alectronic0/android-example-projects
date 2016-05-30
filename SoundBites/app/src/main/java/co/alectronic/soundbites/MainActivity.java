package co.alectronic.soundbites;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View v){
        String ourId = v.getResources().getResourceEntryName(v.getId());
        Log.i("",Integer.toString(v.getId()));
        Log.i("",ourId);
        int resId = getResources().getIdentifier(ourId,"raw",getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();
    }
}
