package co.alectronic.preferencedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPrefrence = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String s = sharedPrefrence.getString("Name",null);

        if(s == null){alertMessage();}
        else {
            Log.i("TAG", s);
            Toast.makeText(this, s, Toast.LENGTH_SHORT);
        }
    }

    protected void onResume(){
        super.onResume();

        String s = sharedPrefrence.getString("Name","");

        if(s.equals("")){alertMessage();}
        else {
            Log.i("TAG", s);
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnGood: goodEnding(); break;
            case R.id.btnBad: badEnding(); break;
            case R.id.btnRemove: neutralEnding(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertMessage(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to do this?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialogInterface, int i) {goodEnding();}})
                .setNegativeButton("No", new DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialogInterface, int i) {badEnding();}})
                .setNeutralButton("Remove", new DialogInterface.OnClickListener() {@Override public void onClick(DialogInterface dialogInterface, int i) {neutralEnding();}})
                .show();
    }

    private void goodEnding(){Log.i("Action","GOOD Pressed");  sharedPrefrence.edit().putString("Name","GOOD").apply(); }
    private void badEnding(){Log.i("Action","BAD Pressed"); sharedPrefrence.edit().putString("Name","BAD").apply(); }
    private void neutralEnding(){Log.i("Action","Remove Pressed"); sharedPrefrence.edit().putString("Name","").apply(); }


}
