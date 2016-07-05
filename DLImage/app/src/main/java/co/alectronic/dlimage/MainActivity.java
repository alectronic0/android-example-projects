package co.alectronic.dlimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    public static final String LINK = "http://vignette1.wikia.nocookie.net/zelda/images/5/57/Breath_of_the_Wild_Artwork_Link_%28Official_Artwork%29.png";
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView)findViewById(R.id.imageView);

    }


    public class TaskDl extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {

            try{
                URL url = new URL(urls[0]);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

                urlConn.connect();

                InputStream in = urlConn.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(in);

                return img;


            }catch(Exception e) {
                return null;
            }
        }
    }

    public void btnClick(View v){
        switch (v.getId()){
            case R.id.btnDlIMG: dlImg();
                break;
        }
    }


    private void dlImg(){
        TaskDl task = new TaskDl();
        Bitmap myImg;
        try{
            myImg = task.execute(LINK).get();

            MediaPlayer mPlay = new MediaPlayer();

            mPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlay.setDataSource("http://noproblo.dayjo.org/ZeldaSounds/OOT/OOT_Fanfare_Item.wav");
            mPlay.prepareAsync();


//You can show progress dialog here untill it prepared to play
            mPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //Now dismis progress dialog, Media palyer will start playing
                    mp.start();

                }
            });
            mPlay.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // dissmiss progress bar here. It will come here when MediaPlayer
                    //  is not able to play file. You can show error message to user
                    return false;
                }
            });

            imgView.setImageBitmap(myImg);


            //
            //add dun dun dun
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}


