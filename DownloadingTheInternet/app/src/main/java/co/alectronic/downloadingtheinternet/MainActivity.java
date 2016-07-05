package co.alectronic.downloadingtheinternet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){

            String result = "";
            URL url;
            HttpURLConnection urlConn = null;

            try{
                url = new URL(urls[0]);
                urlConn = (HttpURLConnection) url.openConnection();
                InputStream in = urlConn.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "Fail";
            }


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String result = null;
        DownloadTask dl = new DownloadTask();
        try{
            result = dl.execute("https://www.fb.com/", "http://fb.com/alectronicX").get();

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("result", result);

    }
}
