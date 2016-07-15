package co.alectronic.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static final String NOTE_ID = "noteId";
    static final String NOTES = "notes";
    static ArrayList<String> notes = new ArrayList<String>();
    static Set<String> set;
    static ArrayAdapter arrayAdapter;
    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        resetNotes();
        //Populate List View
        ListView listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, notes);


        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addOpenNote(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.w("i",i+"");
                Log.w("l",l+"");

                deletePrompt(i);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd: addOpenNote(-1);
        }
        return super.onOptionsItemSelected(item);
    }


    private void deletePrompt(final int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to delete " + notes.get(pos) + " do this?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialogInterface, int i) {deleteNote(pos);}})
                .setNegativeButton("No", new DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialogInterface, int i) {}})
                .show();
    }

    private void resetNotes(){
        set = sharedPreferences.getStringSet(NOTES,null);
        notes.clear();
        if(set != null){
            notes.addAll(set);
        }
        else{
            set = new HashSet<String>();
            set.addAll(notes);
            sharedPreferences.edit().remove(NOTES).apply();
            sharedPreferences.edit().putStringSet(NOTES,set).apply();
        }
    }

    private void addOpenNote(int id){
        Log.w("Opening/ Adding ID: ",id + "/" + notes.size());
        Intent intent = new Intent(getApplicationContext(),NoteActivity.class);
        intent.putExtra(NOTE_ID,id);
        startActivity(intent);
    }


    private void deleteNote(int id) {
        Log.w("DELETING ID: ", id + "/" + notes.size());
        notes.remove(id);
        set.clear();
        set.addAll(notes);
        sharedPreferences.edit().remove(NOTES).apply();
        sharedPreferences.edit().putStringSet(NOTES,set).apply();
        arrayAdapter.notifyDataSetChanged();
    }




}
