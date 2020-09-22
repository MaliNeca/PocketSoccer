package com.example.nemanja.pocketsoccer;

import android.graphics.Bitmap;

public class Player {

    private String name;
    private Bitmap bitmap;
    private float x;
    private float y;
    private float vX, vY;
    private float aX, aY;
    private boolean isBall;


    public boolean isBall() {
        return isBall;
    }

    public void setBall(boolean ball) {
        isBall = ball;
    }

    public Player(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.vX = 0;
        this.vY = 0;
        this.aX = 0;
        this.aY = 0;
        this.isBall = false;

    }

    public Player( float x, float y) {

        this.x = x;
        this.y = y;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }



    public void setY(int y) {
        this.y = y;
    }

    public float getvX() {
        return vX;
    }

    public void setvX(float vX) {
        this.vX = vX;
    }

    public float getvY() {
        return vY;
    }

    public void setvY(float vY) {
        this.vY = vY;
    }

    public float getaX() {
        return aX;
    }

    public void setaX(float aX) {
        this.aX = aX;
    }

    public float getaY() {
        return aY;
    }

    public void setaY(float aY) {
        this.aY = aY;
    }


}
