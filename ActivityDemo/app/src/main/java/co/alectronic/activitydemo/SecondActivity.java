package co.alectronic.activitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtView2 = (TextView) findViewById(R.id.txtViewSecond);
        try{
            Intent i = getIntent();
            Log.i("LOG", i.getStringExtra("a"));
            txtView2.setText(i.getStringExtra("a"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnClick(View v){
        switch(v.getId()){
            case R.id.btnNextAct: gotoNextActivity();break;
        }
    }

    private void gotoNextActivity(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.putExtra("a","aaa");
        startActivity(i);
    }
}
