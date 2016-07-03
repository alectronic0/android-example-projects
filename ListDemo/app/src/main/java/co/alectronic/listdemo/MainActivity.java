package co.alectronic.listdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String family[] = {"Alec","Jenna","Peter","Barbara","Rolo"};
        final ArrayList<String> familyList = new ArrayList<String>(asList("Alec","Jenna","Peter","Barbara","Rolo"));
        //familyList.add("Alec");
        //familyList.add("Jenna");
        //familyList.add("Peter");
        //familyList.add("Barbara");
        //familyList.add("Rolo");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,family);//familyList);//

        ListView myListView = (ListView) findViewById(R.id.mylistview);
        myListView.setAdapter(arrayAdapter);


        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      //parent.setVisibility(View.GONE);
                      //Log.i("Test",familyList.get(i));
                      //Toast.makeText(getApplicationContext(), familyList.get(i).toString() ,Toast.LENGTH_SHORT).show();
                      Log.i("Test",family[i]);
                      Toast.makeText(getApplicationContext(), family[i] ,Toast.LENGTH_SHORT).show();

                  }
              }
        );

    }
}
