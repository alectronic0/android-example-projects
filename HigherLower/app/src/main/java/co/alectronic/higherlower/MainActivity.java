package co.alectronic.higherlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View v){
        int id = v.getId();
        RatingBar rateBarLives = (RatingBar) findViewById(R.id.rateBarLives);

        if(id == R.id.btnHigher) {
            Toast.makeText(getApplicationContext(),"HIGHER", Toast.LENGTH_SHORT).show();
            rateBarLives.setRating(rateBarLives.getRating() + 1);

        }
        else if (id == R.id.btnLower){
            Toast.makeText(getApplicationContext(),"LOWER", Toast.LENGTH_SHORT).show();
            rateBarLives.setRating(rateBarLives.getRating() - 1);
        }
        else{
            Toast.makeText(getApplicationContext(),"UNKNOWN VIEW " + id, Toast.LENGTH_SHORT).show();

        }


        if(rateBarLives.getRating() <= 0){
            Toast.makeText(getApplicationContext()," You Died " + id, Toast.LENGTH_SHORT).show();
        }else{}

    }
}
