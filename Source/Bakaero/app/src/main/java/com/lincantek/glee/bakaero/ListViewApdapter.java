package com.lincantek.glee.bakaero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lincantek.glee.bakaero.model.Player;

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
                TextView txtId = (TextView) view.findViewById(R.id.txt_id);
                TextView txtName = (TextView) view.findViewById(R.id.txt_name);
                TextView txtAddress = (TextView) view.findViewById(R.id.txt_address);
                txtId.setText(String.valueOf(model.getId()));
                txtName.setText(model.getName());
                txtAddress.setText(model.getAddresss());
            }
        }

        return view;
    }

    public void swap(List<Player> list1){
        this.list.clear();
        this.list.addAll(list1);
        notifyDataSetChanged();
    }
}
