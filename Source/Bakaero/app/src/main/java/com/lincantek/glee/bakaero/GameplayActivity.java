package com.lincantek.glee.bakaero;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lincantek.glee.bakaero.model.Expression;
import com.lincantek.glee.bakaero.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameplayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int[] BUTTON_IDS = {
            R.id.num0,
            R.id.num1,  R.id.num2,  R.id.num3,
            R.id.num4,  R.id.num5,  R.id.num6,
            R.id.num7,  R.id.num8,  R.id.num9,
            R.id.clear, R.id.back,
    };

    Expression expression;
    Player player;

    TextView txOperandOne, txOperandTwo, txOperator;
    private int tempMem;

    ProgressBar progessBar;
    CountDownTimer mCountDownTimer;
    int currentProgress;

    TextView txAnswer, txScore;
    int result;
    ExpressController gmc;

    DBContext dbContext;

    long thinkingTime;

    int numQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logEvent("Start play game");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        //load setting
        thinkingTime = getApplicationContext().getResources().getInteger(R.integer.thinking_time);
        thinkingTime *= 1000; // convert to milisec

        //create game controller
        gmc = ExpressController.getInstance();
        numQuest = 0;

        //init widgets
        initialViews();

        //create Player for new sesson
        Bundle bundle = getIntent().getExtras();
        String playerName="StupidOne";
        if (bundle != null) {
            playerName = bundle.getString(MainActivity.PLAYER_NAME);
        }
        player = new Player(playerName);

        // get database context
        dbContext = DBContext.getInst();

        //animation
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
    }

    private void initialViews() {
        generateKeypad();

        //init expression
        try {
            txOperandOne = (TextView) findViewById(R.id.txviewOperand1);
            txOperandTwo = (TextView) findViewById(R.id.txviewOperand2);
            txOperator = (TextView) findViewById(R.id.txviewOperator);

            txAnswer = (TextView) findViewById(R.id.txAnswer);
            txScore = (TextView) findViewById(R.id.txScore);
            progessBar = (ProgressBar) findViewById(R.id.progressBar);
            loadExpression();
        }catch (Exception e){
            showToast(e.getMessage());
        }

    }

    private void generateKeypad() {
        try {
            List<Button> keypad = new ArrayList<>();
            int i=0;
            for (int ids :
                    BUTTON_IDS) {
                Button btnKey = (Button) findViewById(ids);
                keypad.add(btnKey);
                keypad.get(i).setOnClickListener(this);
                i++;
            }
        }catch (Exception e){
            showToast(e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //animation
        overridePendingTransition(R.anim.trans_back_in, R.anim.trans_back_out);
    }

    private Dialog dialog;
    public void endGame(){
        if (mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }

        dialog = new Dialog(GameplayActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lose_layout);

        TextView txDeadReason = (TextView) dialog.findViewById(R.id.txDeadReason);
        String answer = (tempMem==0)? "?" : String.format("%d",tempMem);
        player.setKiller(expression.toString()+answer);
        txDeadReason.setText(player.getKiller());


        Button btnCancel = (Button) dialog.findViewById(R.id.btnFine);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logEvent("Dismiss dialog");
                dialog.dismiss();
                GameplayActivity.this.onBackPressed();
            }
        });

        logEvent("Show register dialog");
        dialog.show();

        dbContext.savePlayer(player);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        for (int i=0; i<BUTTON_IDS.length; i++) {
            if (v.getId()==BUTTON_IDS[i]) {
                switch (i) {
                    case 10: // clear
                        tempMem = 0;
                        loadExpression();
                        updateAnswer();
                        break;
                    case 11: // backspace
                        tempMem /= 10;
                        updateAnswer();
                        break;
                    case 0:
                        if (result==0){
                            nextQuestion();
                            break;
                        }
                        if (tempMem != 0 && tempMem<1000) {
                            tempMem *= 10;
                        }
                        updateAnswer();
                        break;
                    default: // other case 1-9
                        if (tempMem<1000) {
                            tempMem = tempMem * 10 + i;
                            updateAnswer();
                        }
                }
            }
        }
    }

    private void nextQuestion(){
        updateScore();
        mCountDownTimer.cancel();
        loadExpression();
    }

    private void loadExpression(){
        tempMem=0;
        numQuest++;
        expression = gmc.createExpression(numQuest);
        result = expression.getResult();

        txOperandOne.setText(String.format("%d", expression.getOperandOne()));
        txOperandTwo.setText(String.format("%d", expression.getOperandTwo()));
        txOperator.setText(expression.getOperator());
        txAnswer.setText("?");

        currentProgress = 100;
        mCountDownTimer =  new CountDownTimer(thinkingTime,10) {

            @Override
            public void onTick(long millisUntilFinished) {
                currentProgress = (int) (millisUntilFinished*100 / thinkingTime);
                progessBar.setProgress(currentProgress);
            }

            @Override
            public void onFinish() {
                progessBar.setProgress(0);
                endGame();
            }
        };
        mCountDownTimer.start();
    }


    private void updateScore() {
        player.incScore();
        txScore.setText(String.format("%d", player.getScore()));
    }

    // not solved when result = 0
    private void updateAnswer() {

        if (tempMem == 0) {
            txAnswer.setText("?");
        } else {
            txAnswer.setText(String.format("%d", tempMem));
        }

        int resVal = Utilities.specCompare(tempMem, result);

        if (resVal==1) {
            nextQuestion();
        } else if (resVal==-1) {
            endGame();
        }
    }


    public void showToast(String msg){
        Toast toast =Toast.makeText(getApplicationContext(),msg + "\n" +
                "YOU'RE STUPID!",Toast.LENGTH_SHORT);

        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void logEvent(String msg){
        String tagLog = getString(R.string.app_name).replaceAll(" ","_").toUpperCase();
        Log.v(tagLog,msg.toUpperCase());
    }
}
