package co.alectronic.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    int activePlayer = 0;

    int[] gamestate = {2,2,2,2,2,2,2,2,2};

    int[][] winningStates = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    boolean notWon = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset(null);
    }



    public void dropIn(View v){
        boolean b = false;
        ImageView imgView = (ImageView) v;
        int slot = Integer.parseInt(imgView.getTag().toString());

        if(notWon){
            if(gamestate[slot] == 2){
                gamestate[slot] = activePlayer;
                imgView.setTranslationY(-1000f);

                if(activePlayer == 0){imgView.setImageResource(R.drawable.noughtblue); activePlayer=1;}
                else if(activePlayer == 1){imgView.setImageResource(R.drawable.noughtred);activePlayer=0;}
                else {/** fuckedup */}
                imgView.animate().translationYBy(1000).setDuration(300);

                for(int[] winningPos: winningStates){
                    if(gamestate[winningPos[0]] == gamestate[winningPos[1]] &&
                            gamestate[winningPos[1]] == gamestate[winningPos[2]] &&
                            gamestate[winningPos[0]] != 2)
                    {gameOver();}
                }

                for(int gameSlot: gamestate){if(gameSlot == 2){b = true;}}

                if(!b){gameOver();}

            }
        }
    }

    public void gameOver(){
        notWon = false;
        findViewById(R.id.btnReset).setEnabled(true);
        findViewById(R.id.btnReset).setVisibility(View.VISIBLE);
    }

    public void reset(View v){

        int[] myResources = {R.id.imageView, R.id.imageView2, R.id.imageView3,R.id.imageView4,R.id.imageView5,R.id.imageView6,R.id.imageView7
                ,R.id.imageView8,R.id.imageView9};

        for(int i = 0; i<myResources.length; i++) {
            ((ImageView)findViewById(myResources[i])).setImageResource(0);
        }


        activePlayer = 0;
        gamestate = new int[] {2,2,2,2,2,2,2,2,2};
        notWon = true;
        findViewById(R.id.btnReset).setEnabled(false);
        findViewById(R.id.btnReset).setVisibility(View.INVISIBLE);

    }
}
