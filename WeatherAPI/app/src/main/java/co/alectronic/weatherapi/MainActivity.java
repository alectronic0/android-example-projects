package co.alectronic.weatherapi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    public final static String APIWEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    public final static String CITYNAME = "q=London,uk";
    public final static String APIKEYWEATHER = "&APPID=f56ad40ec921d35bf083ebbf3de375e9";

    EditText txtSearchBox;
    Button btnSearch;
    TextView txtViewResults;

    //usplash
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearchBox = (EditText) findViewById(R.id.txtSearchBox);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtViewResults = (TextView) findViewById(R.id.txtViewResults);




    }

    public void btnClick(View v){
        switch (v.getId()){
            case R.id.btnSearch:
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(txtSearchBox.getWindowToken(),0);
                searchWeather(txtSearchBox.getText().toString());
                break;
        }
    }

    public void searchWeather(String city){
        try{new TaskDLWebSite().execute(APIWEATHER + "q=" + URLEncoder.encode(city.trim(),"UTF-8" ) + APIKEYWEATHER);}
        catch(Exception e){Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();}
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
            } catch (Exception e) { e.printStackTrace(); Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show(); return null;}
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                Log.i("RESULTS",s);
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray jsonArray = new JSONArray(weatherInfo);
                Log.i("Content Results",weatherInfo + "    ===============   " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    Log.i("Main",jsonPart.getString("main"));
                    Log.i("descp",jsonPart.getString("description"));
                    txtViewResults.setText(jsonPart.getString("main"));
                }


                for (int i = 0; i < jsonObject.names().length(); i++) { Log.i("Main",jsonObject.names().getString(i) + " ----- "); }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }


        }
    }
}
