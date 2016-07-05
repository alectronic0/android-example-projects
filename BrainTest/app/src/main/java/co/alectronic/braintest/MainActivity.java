package co.alectronic.braintest;

import android.os.CountDownTimer;
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
    Button btnReset;

    TextView txtTimer;
    TextView txtQuestion;
    TextView txtScore;
    TextView txtResult;


    int countRightQ;
    int countTotalQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo = (Button) findViewById(R.id.btnStart);
        btnReset = (Button) findViewById(R.id.btnRestart);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtTimer= (TextView) findViewById(R.id.txtTimer);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtResult = (TextView) findViewById(R.id.txtResult);

        btnReset.setVisibility(View.INVISIBLE);
        loadQuiz(false);
    }

    private void setBtnQuestion(Boolean b){
        btn1.setEnabled(b);
        btn2.setEnabled(b);
        btn3.setEnabled(b);
        btn4.setEnabled(b);
    }

    public void loadQuiz(Boolean b){
        if(b){
            btnGo.setVisibility(View.INVISIBLE);
            btnReset.setVisibility(View.INVISIBLE);

            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);
            txtQuestion.setVisibility(View.VISIBLE);
            txtTimer.setVisibility(View.VISIBLE);
            txtScore.setVisibility(View.VISIBLE);
            txtResult.setVisibility(View.VISIBLE);
            //set timer

            countRightQ = 0;
            countTotalQ = 0;

            setBtnQuestion(true);
            new CountDownTimer(30100,1000){
                public void onTick(long m) {
                    txtTimer.setText(String.valueOf(m/1000) + "S");
                }
                public void onFinish(){
                    setBtnQuestion(false);
                    txtTimer.setText("0S");
                    btnReset.setVisibility(View.VISIBLE);
                }
            }.start();

            nextQuestion();
        }else{
            btnGo.setVisibility(View.VISIBLE);

            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            txtQuestion.setVisibility(View.INVISIBLE);
            txtTimer.setVisibility(View.INVISIBLE);
            txtScore.setVisibility(View.INVISIBLE);
            txtResult.setVisibility(View.INVISIBLE);

        }
    }

    public void btnClick(View v){
        Button b;
        switch(v.getId()){
            case R.id.btnStart: loadQuiz(true);
                break;
            case R.id.btn1: amIRight(Integer.parseInt((String)((Button)v).getText()));
                break;
            case R.id.btn2: amIRight(Integer.parseInt((String)((Button)v).getText()));
                break;
            case R.id.btn3: amIRight(Integer.parseInt((String)((Button)v).getText()));
                break;
            case R.id.btn4: amIRight(Integer.parseInt((String)((Button)v).getText()));
                break;
            case R.id.btnRestart: loadQuiz(true);
        }
    }

    public void amIRight(int s){
        Log.i("Tag", "" +  (s == getAnswer()));
        if(s == getAnswer()){
            //you got it right
            txtResult.setText("Your Right");
            countRightQ++;
        }else{
            //you got it wrong
            txtResult.setText("Your Wrong");
        }
        countTotalQ++;
        nextQuestion();
    }

    public void nextQuestion(){
        ArrayList<Integer> arrAnswers = new ArrayList<Integer>();
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int c = a + b;
        int d;

        txtScore.setText(countRightQ+"/"+countTotalQ);
        txtQuestion.setText(a+"+"+b);




        int intCorrectAnswersLoc = rand.nextInt(4);

        for(int i=0;i<4; i++){
            if(i == intCorrectAnswersLoc){
                arrAnswers.add(i,c);
            }else{
                d = rand.nextInt(41);
                while(c == d || d == 0){d = rand.nextInt(41);}
                arrAnswers.add(i,d);
            }
        }

        btn1.setText(arrAnswers.get(0).toString());
        btn2.setText(arrAnswers.get(1).toString());
        btn3.setText(arrAnswers.get(2).toString());
        btn4.setText(arrAnswers.get(3).toString());
    }

    public int getAnswer(){
        int a = Integer.parseInt(((String)txtQuestion.getText()).split("\\+")[0]);
        int b = Integer.parseInt(((String)txtQuestion.getText()).split("\\+")[1]);
        return a+b;
    }



}
