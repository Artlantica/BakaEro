package com.lincantek.glee.bakaero;

import com.lincantek.glee.bakaero.model.Player;

import java.util.List;

import io.realm.Realm;

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

    public List<Player> getAllRecord() {
        return realm.where(Player.class).findAll();
    }

    public Player getByID(int id) {
        return realm.where(Player.class).equalTo("id",id).findFirst();
    }
}
