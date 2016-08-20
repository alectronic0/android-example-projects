package co.alectronic.parsedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {
//parse-dashboard --appId  alstgram472U294GYGI847 --masterKey  7ntyc487wct0874c0ntw --serverURL  "https://alstgram.herokuapp.com/parse" --appName alstgram
    private static final String APP_ID = "alstgram472U294GYGI847";
    private static final String MASTER_KEY = "7ntyc487wct0874c0ntw";
    private static final String MONGODB_URI = "mongodb://heroku_37rhm300:qbsc10rqns7j6mcpo30vt7a61u@ds023315.mlab.com:23315/heroku_37rhm300";
    private static final String PARSE_MOUNT = "/parse";
    private static final String SERVER_URL = "http://alectronic.herokuapp.com/parse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Parse.enableLocalDatastore(this); //Its already before initialize
        Parse.initialize(this,APP_ID,SERVER_URL);
        //ParseInstallation.getCurrentInstallation().saveInBackground();
        //ParseAnalytics.trackAppOpenedInBackground(getIntent());


        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", "1337");
        gameScore.put("playerName","Alec");
        gameScore.put("cheatMode",false);
        gameScore.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // if null, it means the save has succeeded
                    //String id = gameScore.getObjectId(); // Here you go
                    Log.i("PARSE","Sucess ");
                } else {
                    // the save call was not successful.
                    Log.i("PARSE","Fail");
                }
            }
        });

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL,true);

    }
}
