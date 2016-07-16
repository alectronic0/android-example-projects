package co.alectronic.mynewspaper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final static String JSON_QUERY = ".json?print=pretty";
    final static String URL_HACKERNEWS_TOPSTORIES = "https://hacker-news.firebaseio.com/v0/topstories"+JSON_QUERY;
    final static String URL_HACKERNEWS_ITEM = "https://hacker-news.firebaseio.com/v0/item/";
    final static String STRING_INTENT_TITLE = "title";
    final static String STRING_INTENT_URL = "urlLink";

    final static String DB_ARTICLE = "ArticlesDB";
    final static String TABLE_ARTICLE = "articles";
    final static String FIELD_ID = "id";
    final static String FIELD_ID_ARTICLE = "articleid";
    final static String FIELD_URL = "url";
    final static String FIELD_TITLE = "title";
    final static String FIELD_CONTENT = "content";

    ListView listView;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> urlsLink = new ArrayList<String>();
    ArrayList<String> contents = new ArrayList<String>();
    ArrayAdapter arrayAdapter;


    //Map<Integer,String> newsList = new HashMap<Integer, String>();
    //Map<Integer,String> newsListUrl = new HashMap<Integer, String>();
    //ArrayList<Integer> newsId = new ArrayList<Integer>();
    SQLiteDatabase articleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lstView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                intent.putExtra(STRING_INTENT_TITLE,titles.get(i));
                intent.putExtra(STRING_INTENT_URL,urlsLink.get(i));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) { return true;}
        });

        refreshList();
    }

    private void refreshList(){
        articleDB = this.openOrCreateDatabase(DB_ARTICLE, MODE_PRIVATE, null);
        //articleDB.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE+";");
        articleDB.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_ARTICLE+" ("+
                FIELD_ID+" INTEGER PRIMARY KEY, " +
                FIELD_ID_ARTICLE+" INTERGER, "+
                FIELD_TITLE+" VARCHAR, "+
                FIELD_URL+" VARCHAR, " +
                FIELD_CONTENT+" VARCHAR " +
                ");");
        updateListView();

        new DownloadTask().execute(URL_HACKERNEWS_TOPSTORIES);
    }


    private void updateListView(){
        //Check Query
        titles.clear();
        try {
            Cursor c = articleDB.rawQuery("SELECT * FROM " + TABLE_ARTICLE + " ORDER BY " + FIELD_ID_ARTICLE + " DESC;", null);
            c.moveToFirst();
            while (c.moveToNext()) {
                //for(String resString : c.getColumnNames() ){ Log.i("SQL OUTPUT",c.getString(c.getColumnIndex(resString))); }
                titles.add(c.getString(c.getColumnIndex(FIELD_TITLE)));
                urlsLink.add(c.getString(c.getColumnIndex(FIELD_URL)));
                contents.add(c.getString(c.getColumnIndex(FIELD_CONTENT)));
                    /*
                    Log.i("SQL OUTPUT",FIELD_ID +": "+ c.getString(c.getColumnIndex(FIELD_ID))  + "\n " +
                            FIELD_ID_ARTICLE +": "+ c.getString(c.getColumnIndex(FIELD_ID_ARTICLE)) + "\n " +
                            FIELD_TITLE +": "+ c.getString(c.getColumnIndex(FIELD_TITLE)) + "\n " +
                            FIELD_URL +": "+ c.getString(c.getColumnIndex(FIELD_URL)) + "\n " +
                            FIELD_URL +": "+ c.getString(c.getColumnIndex(FIELD_CONTENT)));
                            */
            }
            c.close();
            //for(String tmpStr : titles){Log.i("Test",tmpStr);}
        }catch(Exception e){e.printStackTrace();}
        arrayAdapter.notifyDataSetChanged();
    }





//DOWNLOADER CLASS
    public class DownloadTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... urls) {
            Log.i("RESULT",urls[0]);
            try {
                articleDB.execSQL("DELETE FROM "+TABLE_ARTICLE+";");
                JSONArray jsonArray = new JSONArray(readURL(urls[0]));
                //if(jsonArray.length() < 400){Log.i("ERROR", "Only "+jsonArray.length()+" Found"); }
                for(int i = 0; i < 20; i++){//jsonArray.length();
                    int articleId = Integer.valueOf(jsonArray.getString(i));
                    String articleIdURL = URL_HACKERNEWS_ITEM + articleId  + JSON_QUERY;
                    //Log.i("Article Details","ArticleID: " + articleId + " ArticleURL: " + articleIdURL);

                    JSONObject jsonObject = new JSONObject(readURL(articleIdURL));

                    String articleTitle = jsonObject.getString("title");
                    String articleURL = jsonObject.getString("url");
                    String articleContent = "";//readURL(articleURL);

                    //newsId.add(articleId);
                    //newsList.put(articleId,articleTitle);
                    //newsListUrl.put(articleId,articleURL);

                    String tmpQuery = "INSERT INTO "+TABLE_ARTICLE+"("+FIELD_ID_ARTICLE+","+FIELD_TITLE+","+FIELD_URL+","+FIELD_CONTENT+") VALUES ( ? , ? , ? , ? )";

                    SQLiteStatement statement = articleDB.compileStatement(tmpQuery);
                    statement.clearBindings();
                    statement.bindString(1,String.valueOf(articleId));
                    statement.bindString(2,articleTitle);
                    statement.bindString(3,articleURL);
                    statement.bindString(4,articleContent);
                    statement.execute();
                }
            }catch(Exception e){e.printStackTrace();}
            return "";
        }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        updateListView();
    }
}

    private String readURL(String s){
        String result = "";
        try {
            URL url = new URL(s);
            URLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
            int data = reader.read();
            while(data != -1){
                result = result + (char) data;
                data = reader.read();
            }
        }catch (Exception e){e.printStackTrace();}

        return result;
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnMainRefresh: refreshList(); break;
            default: Log.wtf("Unknown Item", "Item ID = "+item.getItemId() + " Name " + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }
}
