package com.lincantek.glee.bakaero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lincantek.glee.bakaero.model.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyen on 14/03/2017.
 */

public class ListViewApdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Player> list;
    private Player model;

    public ListViewApdapter(Context context, List<Player> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_on_list, parent, false);

        if (view != null) {
            model = list.get(position);
            if (model != null) {
                TextView txScore = (TextView) view.findViewById(R.id.txScore);
                TextView txName = (TextView) view.findViewById(R.id.txName);
                TextView txDate = (TextView) view.findViewById(R.id.txDate);
                TextView txKiller = (TextView) view.findViewById(R.id.txKiller);

                txScore.setText(String.valueOf(model.getScore()));
                txName.setText(model.getName());

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                txDate.setText(formatter.format(model.getTimeRecord()));

                txKiller.setText(model.getKiller());
            }
        }

        return view;
    }

}
