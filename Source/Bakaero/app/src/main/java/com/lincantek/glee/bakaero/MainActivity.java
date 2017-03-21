package com.lincantek.glee.bakaero;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lincantek.glee.bakaero.model.Player;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String PLAYER_NAME = "BAKANAME";
    Button btnPlay, btnLadder, btnHelp;

    DBContext dbContext;
    List<Player> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logEvent("Main Create Start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbContext = DBContext.getInst();
        list = dbContext.getAllRecordSortByTime();
        init_view();
        logEvent("Main Create End");
    }

    private void init_view() {
        logEvent("View Initial Start");
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnLadder = (Button) findViewById(R.id.btnLadder);
        btnHelp = (Button) findViewById(R.id.btnHelp);

        btnPlay.setOnClickListener(this);
        btnLadder.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        logEvent("View Initial Start");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHelp:
                showToast("Don't you know how to play that game?");
                break;
            case R.id.btnPlay:
                showDialog();
                break;
            case R.id.btnLadder:
                Intent intent = new Intent(MainActivity.this, LadderBoardActivity.class);
                startActivity(intent);
                break;
        }
    }


    private Dialog dialog;
    public void showDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.register_layout);

        Button btnLeft = (Button) dialog.findViewById(R.id.btnRegist);
        Button btnRight = (Button) dialog.findViewById(R.id.btnCancelRegist);

        showToast(String.format("%d", list.size()));

        if (list.isEmpty()) {
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EditText txUserName = (EditText) dialog.findViewById(R.id.edUsername);
                        String userName;
                        if ((userName = txUserName.getText().toString().trim()).isEmpty()) {
                            showToast("You didn't enter your name yet!");
                        } else {
                            logEvent("Dismiss register dialog");
                            dialog.dismiss();
                            logEvent("Switch to Game Play Activity.");
                            Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(PLAYER_NAME, userName);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        showToast(e.getMessage());
                    }
                }
            });
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logEvent("Dismiss register dialog");
                    dialog.dismiss();
                }
            });
        } else {
            TextView txMessage = (TextView) dialog.findViewById(R.id.txMessage);
            Player currentPlayer = list.get(0);
            txMessage.setText(String.format("Are you %s ?", currentPlayer.getName()));
            btnLeft.setText(String.format("Continue as %s", currentPlayer.getName()));
            btnRight.setText("Play New");

            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logEvent("Dismiss register dialog");
                    dialog.dismiss();
                    logEvent("Switch to Game Play Activity.");
                    Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(PLAYER_NAME, list.get(0).getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EditText txUserName = (EditText) dialog.findViewById(R.id.edUsername);
                        String userName;
                        if ((userName = txUserName.getText().toString().trim()).isEmpty()) {
                            showToast("You didn't enter your name yet!");
                        } else {
                            logEvent("Dismiss register dialog");
                            dialog.dismiss();
                            logEvent("Switch to Game Play Activity.");
                            Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(PLAYER_NAME, userName);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        showToast(e.getMessage());
                    }
                }
            });
        }

        logEvent("Show dialog");
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //animation
        overridePendingTransition(R.anim.trans_back_in, R.anim.trans_back_out);
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
