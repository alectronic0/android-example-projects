package co.alectronic.higherlower;

import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int randomInt;
    private int lastRandomInt;
    private int counter;

    private RatingBar rateBarLives;
    private TextView txtResult;
    private TextView txtInfo;
    private Button btnHigher;
    private Button btnLower;
    private Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rateBarLives = (RatingBar) findViewById(R.id.rateBarLives);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnHigher = ((Button) findViewById(R.id.btnHigher));
        btnLower = ((Button) findViewById(R.id.btnLower));
        btnReset = ((Button) findViewById(R.id.btnReset));

        resetGame();
    }

    public void btnClick(View v){
        int id = v.getId();

        if(id == R.id.btnHigher) {
            randomNumberGo();

            if ( randomInt > lastRandomInt ){//Higher
                txtInfo.setText("Your Right");
                //rateBarLives.setRating(rateBarLives.getRating() + 1);
            }
            else{
                txtInfo.setText("Your Wrong");
                rateBarLives.setRating(rateBarLives.getRating() - 1);
            }
            if(didIDie()){gameOver();}else{lvlUp(randomInt > lastRandomInt);}
        }
        else if (id == R.id.btnLower){
            randomNumberGo();
            if ( randomInt < lastRandomInt ){//Higher
                txtInfo.setText("Your Right");
                //rateBarLives.setRating(rateBarLives.getRating() + 1);
            }
            else{
                txtInfo.setText("Your Wrong");
                rateBarLives.setRating(rateBarLives.getRating() - 1);
            }
            if(didIDie()){gameOver();}else{lvlUp(randomInt < lastRandomInt);}
        }
        else if (id == R.id.btnReset){resetGame();}
        else{
            Log.d("DEBUG","UNKNOWN VIEW " + id);
            Toast.makeText(getApplicationContext(),"UNKNOWN VIEW " + id, Toast.LENGTH_SHORT).show();
        }
    }

    private void randomNumberGo(){
        randomInt =(new Random()).nextInt(101);
        txtResult.setText( String.valueOf(randomInt));
    }


    private void lvlUp(Boolean b){if(b){counter++;} lastRandomInt = randomInt; }

    private boolean didIDie(){return (rateBarLives.getRating() <= 0);}


    private void gameOver(){
        txtInfo.setText("You Died, Your Score was: " + counter);

        btnHigher.setEnabled(false);
        btnLower.setEnabled(false);
        btnReset.setEnabled(true);

        btnHigher.setVisibility(View.INVISIBLE);
        btnLower.setVisibility(View.INVISIBLE);
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetGame(){

        txtInfo.setText("");
        txtResult.setText("0");
        rateBarLives.setRating(5);

        btnHigher.setEnabled(true);
        btnLower.setEnabled(true);
        btnReset.setEnabled(false);

        btnHigher.setVisibility(View.VISIBLE);
        btnLower.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);

        counter = 0;
        randomInt = 0;
        lastRandomInt = 0;
    }



}


