package com.example.nemanja.pocketsoccer.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

import com.example.nemanja.pocketsoccer.Cords;
import com.example.nemanja.pocketsoccer.Player;

import java.io.Serializable;
import java.util.List;

public class ContinueGameModel implements Serializable {

    private int p1;
    private int p2;
    private List<Cords> positions;
    private boolean init;
    private boolean move;
    private Player currentPlayer;
    private float fElapsedTime;
    private int condtition;
    private String p1Name;
    private String p2Name;
    private boolean isComputer;
    private Integer team1Goals;
    private Integer team2Goals;
    private int conditionNum;

    private boolean status;
    private long countTime;

    public ContinueGameModel() {
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    public List<Cords> getPositions() {
        return positions;
    }

    public void setPositions(List<Cords> positions) {
        this.positions = positions;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public float getfElapsedTime() {
        return fElapsedTime;
    }

    public void setfElapsedTime(float fElapsedTime) {
        this.fElapsedTime = fElapsedTime;
    }

    public int getCondtition() {
        return condtition;
    }

    public void setCondtition(int condtition) {
        this.condtition = condtition;
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

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public Integer getTeam1Goals() {
        return team1Goals;
    }

    public void setTeam1Goals(Integer team1Goals) {
        this.team1Goals = team1Goals;
    }

    public Integer getTeam2Goals() {
        return team2Goals;
    }

    public void setTeam2Goals(Integer team2Goals) {
        this.team2Goals = team2Goals;
    }

    public int getConditionNum() {
        return conditionNum;
    }

    public void setConditionNum(int conditionNum) {
        this.conditionNum = conditionNum;
    }

    public long getCountTime() {
        return countTime;
    }

    public void setCountTime(long countTime) {
        this.countTime = countTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
