package co.alectronic.mynewspaper;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ArticleActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        webView = (WebView) findViewById(R.id.webView);
        Intent i = getIntent();

        try{
            String t = i.getStringExtra(MainActivity.STRING_INTENT_TITLE);
            Log.i("TITLE",t);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(t);
        }catch (Exception e){e.printStackTrace();}


        try{
            String s = i.getStringExtra(MainActivity.STRING_INTENT_URL);
            Log.i("URL",s);

            WebView webView = (WebView)findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            //webView.loadData(s,"text/html","UTF-8");
            webView.loadUrl(s);
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        }catch(Exception e){e.printStackTrace();}
    }

    public boolean onCreateOptions(Menu menu){
        getMenuInflater().inflate(R.menu.article_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnArticleRefresh: break;
            case android.R.id.home:finish();
            default: Log.wtf("Unknown Item", "Item ID = "+item.getItemId() + " Name " + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }
}
