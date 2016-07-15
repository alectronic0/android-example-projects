package co.alectronic.mynewspaper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final static String JSON_QUERY = ".json?print=pretty";
    final static String URL_HACKERNEWS_TOPSTORIES = "https://hacker-news.firebaseio.com/v0/topstories"+JSON_QUERY;
    final static String URL_HACKERNEWS_ITEM = "https://hacker-news.firebaseio.com/v0/item/";
    final static String STRING_INTENT_URL = "urlLink";

    final static String DB_ARTICLE = "ArticlesDB";
    final static String TABLE_ARTICLE = "articles";
    final static String FIELD_ID = "id";
    final static String FIELD_ID_ARTICLE = "article_id";
    final static String FIELD_URL = "url";
    final static String FIELD_TITLE = "title";

    ListView listView;
    ArrayAdapter arrayAdapter;
    Map<Integer,String> newsList = new HashMap<Integer, String>();
    Map<Integer,String> newsListUrl = new HashMap<Integer, String>();
    ArrayList<Integer> newsId = new ArrayList<Integer>();
    SQLiteDatabase articleDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        articleDB = this.openOrCreateDatabase(DB_ARTICLE, MODE_PRIVATE, null);
        articleDB.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
        articleDB.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_ARTICLE+" ("+FIELD_ID+" INTERGER PRIMARY KEY, "+FIELD_ID_ARTICLE+" INTERGER "+FIELD_TITLE+" VARCHAR, "+FIELD_URL+" VARCHAR");

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                intent.putExtra(STRING_INTENT_URL,newsListUrl.get(i));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return true;
            }
        });

        //arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, R.id.listView,newsList);
        listView.setAdapter(arrayAdapter);

        DownloadTask task = new DownloadTask();
        try {
            String s = task.execute(URL_HACKERNEWS_TOPSTORIES).get();
            JSONArray jsonArray = new JSONArray(s);

            //if(jsonArray.length() < 400){Log.i("ERROR", "Only "+jsonArray.length()+" Found"); }
            for(int i = 0; i < 20; i++){//jsonArray.length();

                int articleId = Integer.valueOf(jsonArray.getString(i));
                String articleIdURL = URL_HACKERNEWS_ITEM + articleId  + JSON_QUERY;
                Log.i("Article Details","ArticleID: " + articleId + " ArticleURL: " + articleIdURL);


                String t = (new DownloadTask()).execute(articleIdURL).get();
                JSONObject jsonObject = new JSONObject(t);

                String articleTitle = jsonObject.getString("title");
                String articleURL = jsonObject.getString("url");
                String tmpQuery = "INSERT INTO "+TABLE_ARTICLE+"("+FIELD_ID_ARTICLE+","+FIELD_TITLE+","+FIELD_URL+") VALUES("+articleId+",'"+articleTitle+"','"+articleURL+"')";
                newsId.add(articleId);
                newsList.put(articleId,articleTitle);
                newsListUrl.put(articleId,articleURL);

                articleDB.execSQL(tmpQuery);

                Log.i("Article Details","ArticleID: " + articleId +  " Title " + articleTitle + " URL: " + articleURL);
            }
        }catch(Exception e){e.printStackTrace(); }


        //Check Query
        Cursor c = articleDB.rawQuery("SELECT * FROM "+TABLE_ARTICLE,null);
        c.moveToFirst();
        while (c != null){
            Log.i("SQL OUTPUT",FIELD_ID +": "+ c.getColumnIndex(FIELD_ID)  + "\n " +
                               FIELD_ID_ARTICLE +": "+ c.getColumnIndex(FIELD_ID_ARTICLE) + "\n " +
                               FIELD_TITLE +": "+ c.getColumnIndex(FIELD_TITLE) + "\n " +
                               FIELD_URL +": "+ c.getColumnIndex(FIELD_URL));
            c.moveToNext();
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            Log.i("RESULT",urls[0]);
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                int data = reader.read();
                while(data != -1){
                    result = result + (char) data;
                    data = reader.read();
                }
            }catch(Exception e){e.printStackTrace();}

            return result;
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnMainRefresh: break;
            default: Log.wtf("Unknown Item", "Item ID = "+item.getItemId() + " Name " + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }






}
