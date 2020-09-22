package com.example.nemanja.pocketsoccer.dao;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "victory_table")
public class Victory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String p1Name;

    private String p2Name;

    private int p1Win;

    private int p2Win;

    public Victory(String p1Name, String p2Name, int p1Win, int p2Win) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1Win = p1Win;
        this.p2Win = p2Win;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public int getP1Win() {
        return p1Win;
    }

    public void setP1Win(int p1Win) {
        this.p1Win = p1Win;
    }

    public int getP2Win() {
        return p2Win;
    }

    public void setP2Win(int p2Win) {
        this.p2Win = p2Win;
    }
}
