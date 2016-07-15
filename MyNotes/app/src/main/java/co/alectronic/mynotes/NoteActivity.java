package co.alectronic.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    EditText txtNote;
    int noteLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //setup UI
        try{getActionBar().setDisplayHomeAsUpEnabled(true);} catch(Exception e){e.printStackTrace();}
        txtNote = (EditText) findViewById(R.id.txtNote);

        //Populate UI
        try{
            Intent i = getIntent();
            noteLocation = i.getIntExtra(MainActivity.NOTE_ID,-1);
            if(noteLocation != -1) {
                String s = MainActivity.notes.get(noteLocation);
                txtNote.setText(s);
            }
        }
        catch(Exception e){Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();}

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: save(); return true;
            case R.id.btnCancel: finish(); return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

    public void save(){
        if(noteLocation != -1){MainActivity.notes.set(noteLocation,txtNote.getText().toString());}
        else{MainActivity.notes.add(txtNote.getText().toString());}

        MainActivity.set.clear();
        MainActivity.set.addAll(MainActivity.notes);
        MainActivity.sharedPreferences.edit().remove(MainActivity.NOTES).apply();
        MainActivity.sharedPreferences.edit().putStringSet(MainActivity.NOTES,MainActivity.set).apply();
        MainActivity.arrayAdapter.notifyDataSetChanged();
        finish();
    }
}
