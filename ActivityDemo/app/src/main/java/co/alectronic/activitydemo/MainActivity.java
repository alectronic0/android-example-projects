package co.alectronic.activitydemo;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    ListView lstView;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView = (ListView) findViewById(R.id.lstFamily);
        txtView = (TextView) findViewById(R.id.txtViewFirst);

        final String family[] = {"Alec","Jenna","Peter","Barbara","Rolo"};
        final ArrayList<String> familyList = new ArrayList<String>(asList("Alec","Jenna","Peter","Barbara","Rolo"));

        lstView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,family));

        lstView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("Test", family[i]);
                        gotoNextActivity(family[i]);
                    }
                } );
        try {
            Intent i = getIntent();
            Log.i("LOG", i.getStringExtra("a"));
            txtView.setText(i.getStringExtra("a"));
        } catch( Exception e) {e.printStackTrace();}

    }

    public void btnClick(View v){
        switch(v.getId()){
        }
    }

    private void gotoNextActivity(String s){
        Intent i = new Intent(getApplicationContext(),SecondActivity.class);
        i.putExtra("a",s);
        startActivity(i);
    }
}
