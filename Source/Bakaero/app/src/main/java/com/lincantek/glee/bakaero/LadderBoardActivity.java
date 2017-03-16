package com.lincantek.glee.bakaero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.lincantek.glee.bakaero.model.Player;

import java.util.List;

public class LadderBoardActivity extends AppCompatActivity {
    ListViewApdapter apdapter;
    ListView listView;
    DBContext dbContext;

    List<Player> listPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder_board);

        dbContext = DBContext.getInst();
        listPlayer = dbContext.getAllRecordSortByScore();

        listView = (ListView) findViewById(R.id.listPlayer);

        // load playerList
        apdapter = new ListViewApdapter(this, listPlayer);
        listView.setAdapter(apdapter);
    }


}
