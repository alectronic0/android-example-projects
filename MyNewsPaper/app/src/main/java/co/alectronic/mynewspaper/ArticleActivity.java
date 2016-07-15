package co.alectronic.mynewspaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ArticleActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        webView = (WebView) findViewById(R.id.webView);

        try{getActionBar().setDisplayHomeAsUpEnabled(true);}catch (Exception e){e.printStackTrace();}

        try{
            Intent i = getIntent();
            String s = i.getStringExtra(MainActivity.STRING_INTENT_URL);

            WebView webView = (WebView)findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(s);
            Toast.makeText(this,s,Toast.LENGTH_LONG);

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
