package com.lincantek.glee.bakaero.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by luyen on 12/03/2017.
 */

public class Player extends RealmObject{
    @PrimaryKey
    private AtomicInteger id;
    private String name;
    private int score;
    private Date timeRecord;
    private String killer;

    public Player(String name) {
        this.name = name;
        score=0;
        timeRecord = new Date();
    }


    /**
     * Call when player get one score
     */
    public void incScore(){
        score++;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Date getTimeRecord() {
        return timeRecord;
    }

    public String getKiller() {
        return killer;
    }

    public void setKiller(String killer) {
        this.killer = killer;
    }
}
