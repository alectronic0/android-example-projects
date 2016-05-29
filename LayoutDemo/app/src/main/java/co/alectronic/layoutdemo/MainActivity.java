package co.alectronic.layoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fade1(View v){

        ImageView imgView =(ImageView) findViewById(R.id.imgView);
        ImageView imgView2 =(ImageView) findViewById(R.id.imgView2);

        imgView.animate().alpha(0f).setDuration(2000);
        imgView.animate().translationX(1000).setDuration(2000);
        imgView.animate().translationY(1000).setDuration(2000);
        imgView.animate().rotation(180f).setDuration(2000);
        imgView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
        imgView2.animate().alpha(1f).setDuration(2000);
    }

    public void fade2(View v){

        ImageView imgView =(ImageView) findViewById(R.id.imgView);
        ImageView imgView2 =(ImageView) findViewById(R.id.imgView2);

        imgView2.animate().alpha(0f).setDuration(2000);
        imgView.animate().rotation(360f).setDuration(2000);
        imgView.animate().scaleX(1f).scaleY(1f).setDuration(2000);
        imgView.animate().alpha(1f).setDuration(2000);
        imgView.animate().translationX(0).setDuration(2000);
        imgView.animate().translationY(0).setDuration(2000);
    }


}
