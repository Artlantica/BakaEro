package com.lincantek.glee.bakaero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lincantek.glee.bakaero.model.Player;

import java.util.List;

public class LadderBoardActivity extends AppCompatActivity {
    ListViewApdapter apdapter;
    ListView listView;
    DBContext dbContext;
    Button btnDelete;

    List<Player> listPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder_board);

        dbContext = DBContext.getInst();
        listPlayer = dbContext.getTopRecordByPlayer();

        listView = (ListView) findViewById(R.id.listPlayer);

        btnDelete = (Button) findViewById(R.id.btnDelete);

        if (listPlayer.isEmpty()){
            btnDelete.setText(R.string.no_records);
        }


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbContext.clear();
                btnDelete.setText(R.string.no_records);
                loadScoreList();

            }
        });

        loadScoreList();


        //animation
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
    }

    private void loadScoreList(){
        listPlayer = dbContext.getTopRecordByPlayer();
        apdapter = new ListViewApdapter(this, listPlayer);
        listView.setAdapter(apdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //animation
        overridePendingTransition(R.anim.trans_back_in, R.anim.trans_back_out);
    }

}
