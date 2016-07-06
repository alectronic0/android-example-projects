package co.alectronic.guessceleb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    ImageView imgView;


    public static final String CELEBURL = "http://www.posh24.com/celebrities";

    public ArrayList<String> celebURLs = new ArrayList<String>();
    public ArrayList<String> celebNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        imgView = (ImageView) findViewById(R.id.imageView);
        Log.i("START","================================APP STARTED===============================");
        genCelebs();
        nextQuestion();
    }

    public void btnClick(View v) {
        Log.i("VIEW ID = ",""+v.getId());
        switch (v.getId()) {
            case R.id.btn1: amIRight((String) ((Button) v).getText()); break;
            case R.id.btn2: amIRight((String) ((Button) v).getText()); break;
            case R.id.btn3:  amIRight((String) ((Button) v).getText()); break;
            case R.id.btn4: amIRight((String) ((Button) v).getText()); break;
        }
    }



    private void genCelebs() {
        try {
            String result = new TaskDLWebSite().execute(CELEBURL).get().split("<div class\"sidevarContainder\">")[0];

            Pattern pat = Pattern.compile("<img src=\"(.*?)\" "); //(.*?)
            Matcher m = pat.matcher(result);

            while (m.find()) { Log.i("Tag", m.group(1)); celebURLs.add(m.group(1)); }

            pat = Pattern.compile("alt=\"(.*?)\""); //(.*?)
            m = pat.matcher(result);

            while (m.find()) { Log.i("Tag", m.group(1)); celebNames.add(m.group(1)); }

        } catch (Exception e) { e.printStackTrace(); }
    }

    private int loadImg() {
        int selCeleb = new Random().nextInt(celebURLs.size());

        TaskDl task = new TaskDl();
        Bitmap myImg;
        try {
            myImg = task.execute(celebURLs.get(selCeleb)).get();
            imgView.setImageBitmap(myImg);
            imgView.setTag(celebNames.get(selCeleb));
        } catch (Exception e) { e.printStackTrace();}
        return selCeleb;
    }

    public void amIRight(String s) {
        Log.i("Tag", "" + s + getAnswer() + " " + (s.equals(getAnswer())));
        if (s.equals(getAnswer())) { Toast.makeText(getApplicationContext(), "Your Right", Toast.LENGTH_SHORT).show(); }
        else {Toast.makeText(getApplicationContext(), "Your Wrong", Toast.LENGTH_SHORT).show();}
        nextQuestion();
    }


    public void nextQuestion() {
        ArrayList<Integer> arrAnswers = new ArrayList<Integer>();
        Random rand = new Random();
        int c = loadImg(); int d;
        int intCorrectAnswersLoc = rand.nextInt(4);

        for (int i = 0; i < 4; i++) {
            if (i == intCorrectAnswersLoc) { arrAnswers.add(i, c); }
            else {
                d = rand.nextInt(celebURLs.size());
                while (c == d || d == 0) {  d = rand.nextInt(celebURLs.size()); }
                arrAnswers.add(i, d);
            }
        }


        btn1.setText(celebNames.get(arrAnswers.get(0)));
        btn2.setText(celebNames.get(arrAnswers.get(1)));
        btn3.setText(celebNames.get(arrAnswers.get(2)));
        btn4.setText(celebNames.get(arrAnswers.get(3)));
    }

    public String getAnswer() {return (String) imgView.getTag(); }


    public class TaskDLWebSite extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                HttpURLConnection urlConn = (HttpURLConnection)  new URL(urls[0]).openConnection();
                InputStreamReader reader = new InputStreamReader(urlConn.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    result += (char) data ;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) { e.printStackTrace(); return null;}
        }
    }


    public class TaskDl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                HttpURLConnection urlConn = (HttpURLConnection) new URL(urls[0]).openConnection();
                urlConn.connect();
                return BitmapFactory.decodeStream(urlConn.getInputStream());
            } catch (Exception e) { e.printStackTrace(); return null; }
        }
    }


}

/*
    private void TestStringMagic(){
        //http://www.posh24.com/celebrities
        String myString = "Alec x Steve x Jenna x Dave";
        String[] splitString = myString.split(" x ");


        Log.i("Tag", Arrays.toString(splitString));

        for(String s : splitString){
            Log.i("Tag",s);
        }

        String river = "mississippi";
        String riverSub = river.substring(2,6);
        Log.i("Tag",riverSub);


        Pattern pat = Pattern.compile("Mi(.*?)pi"); //(.*?)
        Matcher m = pat.matcher(river);

        while(m.find()){
            Log.i("Tag",m.group(1));
        }


        //<div class="image">
        String clebeMeta = "<img src=\"http://cdn.posh24.com/images/:profile/0a749b802defbf357e7ccf1361ccabef5\" alt=\"Rita Ora\"> </div>";


        pat = Pattern.compile("<img src=\"(.*?)\" "); //(.*?)
        m = pat.matcher(clebeMeta);

        while(m.find()){
            Log.i("Tag",m.group(1));
        }

        pat = Pattern.compile("alt=\"(.*?)\">"); //(.*?)
        m = pat.matcher(clebeMeta);

        while(m.find()){
            Log.i("Tag",m.group(1));
        }
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
*/