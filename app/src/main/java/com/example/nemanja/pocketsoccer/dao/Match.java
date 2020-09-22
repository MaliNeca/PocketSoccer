package com.example.nemanja.pocketsoccer.dao;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "match_table")
public class Match {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String p1Name;

    private String p2Name;

    private String time;

    private int p1Goals;

    private int p2Goals;




    public Match(String p1Name, String p2Name, String time, int p1Goals, int p2Goals) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.time = time;
        this.p1Goals = p1Goals;
        this.p2Goals = p2Goals;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public String getTime() {
        return time;
    }

    public int getP1Goals() {
        return p1Goals;
    }

    public int getP2Goals() {
        return p2Goals;
    }
}
