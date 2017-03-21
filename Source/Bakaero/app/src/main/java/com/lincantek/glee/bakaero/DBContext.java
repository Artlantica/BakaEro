package com.lincantek.glee.bakaero;

import com.lincantek.glee.bakaero.model.Player;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by luyen on 14/03/2017.
 */

public class DBContext {
    private Realm realm;

    public DBContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DBContext inst;

    public static DBContext getInst() {
        if (inst == null) {
            return new DBContext();
        }
        return inst;
    }

    public void savePlayer(Player model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public void clear(){
        // obtain the results of a query

        RealmResults<Player> results = realm.where(Player.class).findAll();

        // All changes to data must happen in a transaction
        realm.beginTransaction();

        // Delete all matches
        realm.deleteAll();

        realm.commitTransaction();
    }

    public List<Player> getAllRecordSortByScore() {
        return realm.where(Player.class).findAll().sort("score", Sort.DESCENDING);
    }

    public List<Player> getAllRecordSortByTime() {
        return realm.where(Player.class).findAll().sort("score", Sort.DESCENDING);
    }

    public List<Player> getTopRecordByPlayer() {
        List<Player> res = new ArrayList<>();
        List<Player> scoreList = getAllRecordSortByScore();
        List<String> nameList = new ArrayList<>();
        for (Player model :
                scoreList) {
            if (!nameList.contains(model.getName())){
                nameList.add(model.getName());
                res.add(model);
            }
        }
        return res;
    }

    public Player getByID(int id) {
        return realm.where(Player.class).equalTo("id",id).findFirst();
    }
}
