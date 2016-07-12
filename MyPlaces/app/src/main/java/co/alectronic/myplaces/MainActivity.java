package co.alectronic.myplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {


    ListView lstView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView = (ListView) findViewById(R.id.lstView);

        final ArrayList<String> places = new ArrayList<String>(asList("Add a new place...","55 The Walk"));

        lstView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places));

        lstView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {gotoNextActivity(places.get(i),i);}
                });
    }



    private void gotoNextActivity(String s,int pos){
        Log.i("Test", s);
        if(pos > 0) {
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            i.putExtra("a", s);
            startActivity(i);
        }
    }
}
