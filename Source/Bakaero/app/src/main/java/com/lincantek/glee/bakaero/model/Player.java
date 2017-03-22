package com.lincantek.glee.bakaero.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by luyen on 12/03/2017.
 */

public class Player extends RealmObject{
    private String name;
    private int score;

    @PrimaryKey
    private String timeKey;
    private Date timeRecord;
    private String killer;

    public Player(){

    }

    public Player(String name) {
        this.name = name;
        score=0;
        timeRecord = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("hhmmssddMMyyyy");
        timeKey = formater.format(timeRecord);
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

    public String getTimeKey() {
        return timeKey;
    }
}
