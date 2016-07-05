package co.alectronic.braintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnGo;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;



    TextView txtTimer;
    TextView txtQuestion;
    TextView txtScore;
    TextView txtResult;

    ArrayList<Integer> arrAnswers;
    int intCorrectAnswersLoc;

    public void start(View v){

        v.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo = (Button) findViewById(R.id.btnStart);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtTimer= (TextView) findViewById(R.id.txtTimer);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtResult = (TextView) findViewById(R.id.txtResult);




        btnGo.setVisibility(View.INVISIBLE);


    }

    public void btnClick(View v){

        Log.i("TAG", v.getId() + "");
        //btnGo.setVisibility(View.INVISIBLE);
    }

    public void nextQuestion(View v){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int c = a + b;
        int d = c;
        txtQuestion.setText(Integer.toString(a)+"+"+Integer.toString(b));

        intCorrectAnswersLoc = rand.nextInt(4);

        for(int i=0;i<4; i++){
            if(i == intCorrectAnswersLoc){
                arrAnswers.add(i,c);
            }else{
                while(c == d){d = rand.nextInt(41);}
                arrAnswers.add(i,d);
            }
        }

        btn1.setText(arrAnswers.get(1));
        btn2.setText(arrAnswers.get(2));
        btn3.setText(arrAnswers.get(3));
        btn4.setText(arrAnswers.get(4));
    }




}
