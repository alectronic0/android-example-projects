package co.alectronic.pgp_bionode_hack;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {




    public final static String URLGENO = "https://my.pgp-hms.org/public_genetic_data";

    //usplash
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //searchGenoRepo(URLGENO);
        genTableList();
    }



    public void searchGenoRepo(String url){
        try{new TaskDLWebSite().execute(URLEncoder.encode(url.trim(),"UTF-8" ));}
        catch(Exception e){Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();}
    }

    public class TaskDLWebSite extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.i("URL",urls[0]);
                String result = "";
                HttpURLConnection urlConn = (HttpURLConnection)  new URL(urls[0]).openConnection();
                InputStreamReader reader = new InputStreamReader(urlConn.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    result += (char) data ;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {return "";}
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("DATA",s);
            /*try{
                Log.i("RESULTS",s);
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray jsonArray = new JSONArray(weatherInfo);
                Log.i("Content Results",weatherInfo + "    ===============   " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    Log.i("Main",jsonPart.getString("main"));
                    Log.i("descp",jsonPart.getString("description"));
                }

                for (int i = 0; i < jsonObject.names().length(); i++) { Log.i("Main",jsonObject.names().getString(i) + " ----- "); }
            }catch(Exception e){  Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show(); }*/
        }

    }

    private void genTableList() {
        try {
            String result = new TaskDLWebSite().execute(URLGENO).get().split("<div class\"sidevarContainder\">")[0];

            Pattern pat = Pattern.compile("<div class=\"profile-data\">(.*?)</tbody></table></div> "); //(.*?)
            Matcher m = pat.matcher(result);

            while (m.find()) { Log.i("Tag", m.group(1)); /*celebURLs.add(m.group(1));*/ }

            //pat = Pattern.compile("alt=\"(.*?)\""); //(.*?)
            //m = pat.matcher(result);

            //while (m.find()) { Log.i("Tag", m.group(1)); /*celebURLs.add(m.group(1));*/ }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
