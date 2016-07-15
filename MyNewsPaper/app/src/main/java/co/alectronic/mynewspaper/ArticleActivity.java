package co.alectronic.mynewspaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
    }

    public boolean onCreateOptions(Menu menu){
        getMenuInflater().inflate(R.menu.article_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnArticleRefresh: break;
            default: Log.wtf("Unknown Item", "Item ID = "+item.getItemId() + " Name " + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }
}
