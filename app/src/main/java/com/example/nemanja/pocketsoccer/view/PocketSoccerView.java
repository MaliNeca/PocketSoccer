package com.example.nemanja.pocketsoccer.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;


import com.example.nemanja.pocketsoccer.Cords;
import com.example.nemanja.pocketsoccer.Player;
import com.example.nemanja.pocketsoccer.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;


public class PocketSoccerView extends View implements View.OnTouchListener {

    private Bitmap field;
    private Bitmap sBall;
    private Player ball;
    private Bitmap team1Logo;
    private Bitmap team2Logo;
    private Paint scorePaint = new Paint();
    private CopyOnWriteArrayList<Player> players;
    private boolean init;
    private boolean move = true; //true home, false away
    private float startX, startY, endX, endY;
    private Player currentPlayer = null;
    private Rect fieldRect;
    private Rect leftGoalRect, rightGoalRect;
    private float fElapsedTime = 0.1f;
    private int condition = 1;
    private String p1Name, p2Name;
    private boolean isComputer;
    private Integer team1Goals;
    private Integer team2Goals;
    private int conditionNum;

    private String timerText;
    private Paint transparentpaint;
    private CountDownTimer timeToPlay;
    private CountDownTimer gameTime;
    final MediaPlayer gooal;
    final MediaPlayer ballSound;
    private boolean status = false;
    private long countTime;
    private boolean waitTime = false;


    private static final String SHARED_PREFS = "sharedPref";
    private static final String SPEED = "speed";
    public static final String TYPE = "type";
    public static final String NUMBER = "number";
    public static final String COURT = "court";


    public PocketSoccerView(Context context, int p1, int p2, final String p1Name, final String p2Name, final boolean isComputer, boolean movee) {
        super(context);
        setOnTouchListener(this);

        this.move = movee;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.isComputer = isComputer;
        this.team1Goals = 0;
        this.team2Goals = 0;
        timerText = "";


        gooal = MediaPlayer.create(getContext(), R.raw.gooal);
        ballSound = MediaPlayer.create(getContext(), R.raw.ballsound);

        timeToPlay = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (!isComputer) {
                    if (move) {
                        move = false;
                        timeToPlay.start();
                    } else {
                        move = true;
                        timeToPlay.start();
                    }
                } else {
                    if (move) {
                        move = false;
                        computerPlay();
                        timeToPlay.start();
                    } else {
                        move = true;

                        timeToPlay.start();
                    }
                }

            }
        };

        if (isComputer && !move){
            computerPlay();
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        condition = sharedPreferences.getInt(TYPE, 1);


        switch (sharedPreferences.getInt(NUMBER, 0)) {
            case 0:
                conditionNum = 1;
                break;
            case 1:
                conditionNum = 3;
                break;
            case 2:
                conditionNum = 5;
                break;
            case 3:
                conditionNum = 7;
                break;
            case 4:
                conditionNum = 10;
                break;
            default:
                conditionNum = 1;
                break;
        }

        gameTime = new CountDownTimer(60000 * conditionNum, 1000) {
            public void onTick(long millisUntilFinished) {
                countTime = millisUntilFinished;
                if ((millisUntilFinished / 1000) / 60 > 0) {
                    timerText = "" + (millisUntilFinished / 60000) + " : " + ((millisUntilFinished / 1000) - (millisUntilFinished / 60000) * 60);
                } else {
                    timerText = "" + "0 : " + millisUntilFinished / 1000;
                }
            }

            public void onFinish() {
                status = true;
            }
        };

        if (condition == 2) {
            gameTime.start();
        }

        switch (sharedPreferences.getInt(SPEED, 2)) {
            case 0:
                fElapsedTime = 0.001f;
                break;
            case 1:
                fElapsedTime = 0.005f;
                break;
            case 2:
                fElapsedTime = 0.01f;
                break;
            case 3:
                fElapsedTime = 0.05f;
                break;
            case 4:
                fElapsedTime = 0.1f;
                break;
        }

        field = BitmapFactory.decodeResource(getResources(), sharedPreferences.getInt(COURT, 2131099739));
        sBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball10);
        sBall = Bitmap.createScaledBitmap(sBall, sBall.getWidth() * 2, sBall.getHeight() * 2, true);

        ball = new Player(sBall, 0, 0);
        ball.setBall(true);
        fieldRect = new Rect(0, 0, 0, 0);
        leftGoalRect = new Rect(0, 0, 0, 0);
        rightGoalRect = new Rect(0, 0, 0, 0);
        players = new CopyOnWriteArrayList<>();

        team1Logo = BitmapFactory.decodeResource(getResources(), p1);
        team1Logo = Bitmap.createScaledBitmap(team1Logo, team1Logo.getWidth() * 3, team1Logo.getHeight() * 3, true);
        team2Logo = BitmapFactory.decodeResource(getResources(), p2);
        team2Logo = Bitmap.createScaledBitmap(team2Logo, team2Logo.getWidth() * 3, team2Logo.getHeight() * 3, true);
        transparentpaint = new Paint();
        transparentpaint.setAlpha(100); // 0 - 255


        players.add(new Player(BitmapFactory.decodeResource(getResources(), p1), 0, 0));
        players.add(new Player(BitmapFactory.decodeResource(getResources(), p1), 0, 0));
        players.add(new Player(BitmapFactory.decodeResource(getResources(), p1), 0, 0));
        players.add(new Player(BitmapFactory.decodeResource(getResources(), p2), 0, 0));
        players.add(new Player(BitmapFactory.decodeResource(getResources(), p2), 0, 0));
        players.add(new Player(BitmapFactory.decodeResource(getResources(), p2), 0, 0));
        players.add(ball);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(70);

        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        init = true;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        field = Bitmap.createScaledBitmap(field, canvas.getWidth(), canvas.getHeight() - 60, true);
        fieldRect.set(0, 0, canvas.getWidth(), canvas.getHeight());


        canvas.drawBitmap(field, 0, 60, null);

        leftGoalRect.set(0, (canvas.getHeight() - 60) * 4 / 10, canvas.getWidth() / 16, (canvas.getHeight() - 60) * 7 / 10);
        rightGoalRect.set(canvas.getWidth() * 15 / 16, (canvas.getHeight() - 60) * 4 / 10, canvas.getWidth(), (canvas.getHeight() - 60) * 7 / 10);


        canvas.drawText(p1Name, 0, 60, scorePaint);
        canvas.drawText(p2Name, (canvas.getWidth() / 8) * 7, 60, scorePaint);
        canvas.drawText(team1Goals.toString(), canvas.getWidth() / 4, 60, scorePaint);
        canvas.drawText(team2Goals.toString(), (canvas.getWidth() / 4) * 3, 60, scorePaint);
        canvas.drawText(timerText, canvas.getWidth() / 2 - 70, 60, scorePaint);


        if (init) {
            players.get(0).setX(canvas.getWidth() / 10);
            players.get(0).setY(canvas.getHeight() / 2 - players.get(0).getBitmap().getHeight() / 2);
            players.get(1).setX(canvas.getWidth() / 4);
            players.get(1).setY(canvas.getHeight() / 4 - players.get(1).getBitmap().getHeight() / 2);
            players.get(2).setX(canvas.getWidth() / 4);
            players.get(2).setY(canvas.getHeight() * 3 / 4 - players.get(2).getBitmap().getHeight() / 2);

            players.get(3).setX(canvas.getWidth() * 9 / 10 - players.get(3).getBitmap().getWidth());
            players.get(3).setY(canvas.getHeight() / 2 - players.get(3).getBitmap().getHeight() / 2);
            players.get(4).setX(canvas.getWidth() * 3 / 4 - players.get(4).getBitmap().getWidth());
            players.get(4).setY(canvas.getHeight() / 4 - players.get(4).getBitmap().getHeight() / 2);
            players.get(5).setX(canvas.getWidth() * 3 / 4 - players.get(5).getBitmap().getWidth());
            players.get(5).setY(canvas.getHeight() * 3 / 4 - players.get(5).getBitmap().getHeight() / 2);

            ball.setX(canvas.getWidth() / 2 - ball.getBitmap().getWidth() / 2);
            ball.setY(canvas.getHeight() / 2 - ball.getBitmap().getHeight() / 4);

            for (Player p : players) {
                p.setaX(0);
                p.setaY(0);
                p.setvX(0);
                p.setvY(0);

            }

            init = false;

        }
        if (move) {
            canvas.drawBitmap(team1Logo, canvas.getWidth() / 8, (canvas.getHeight() / 2) - (team1Logo.getHeight() / 2), transparentpaint);
        } else {
            canvas.drawBitmap(team2Logo, (canvas.getWidth() * 6) / 10, (canvas.getHeight() / 2) - (team2Logo.getHeight() / 2), transparentpaint);
        }
        /*if (isComputer && !move) {
            computerPlay();
        }*/
        makeMove(canvas);

        canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), null);

        for (Player pHome : players
        ) {
            canvas.drawBitmap(pHome.getBitmap(), pHome.getX(), pHome.getY(), null);
        }


        invalidate();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isComputer) {
                    if (move) {
                        startX = event.getX();
                        startY = event.getY();

                        currentPlayer = checkHomeTeam(event.getX(), event.getY());
                        if (currentPlayer != null) {
                            timeToPlay.cancel();
                            // move = false;
                            return true;
                        }
                    } else {
                        startX = event.getX();
                        startY = event.getY();
                        currentPlayer = checkAwayTeam(event.getX(), event.getY());
                        if (currentPlayer != null) {
                            timeToPlay.cancel();
                            //move = true;

                            return true;
                        }
                    }

                } else {
                    //computer play

                    if (move) {
                        startX = event.getX();
                        startY = event.getY();

                        currentPlayer = checkHomeTeam(event.getX(), event.getY());
                        if (currentPlayer != null) {
                            timeToPlay.cancel();
                            // move = false;
                            return true;
                        }
                    }


                }
                break;

            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();
                currentPlayer.setvX(5.0f * (event.getX() - startX));
                currentPlayer.setvY(5.0f * (event.getY() - startY));
                if (move) {
                    move = false;
                } else {
                    move = true;
                }
                timeToPlay.start();
                computerPlay();
                break;

        }
        return false;
    }


    public void computerPlay() {


        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                int r = (int) (Math.random() * (6 - 3)) + 3;
                currentPlayer = players.get(r);

                move = true;


                currentPlayer.setvX(5.0f * (players.get(6).getX() - currentPlayer.getX()));
                currentPlayer.setvY(5.0f * (players.get(6).getY() - currentPlayer.getY()));

            }
        });
        thread.start();
    }


    public boolean checkCollision(Player p1, Player p2) {

        if (p2.isBall() || p1.isBall()) {

            float r = p1.getBitmap().getWidth() / 2;
            float r1 = p2.getBitmap().getWidth() / 2;


            float x = p1.getX() + r;
            float y = p1.getY() + r;

            float x1 = p2.getX() + r1;
            float y1 = p2.getY() + r1;

            if ((Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)) + 20) <= (r + r1)) {
                ballSound.start();
                return true;
            }


        } else {


            float r = p1.getBitmap().getWidth() / 2;
            float r1 = p2.getBitmap().getWidth() / 2;


            float x = p1.getX() + r;
            float y = p1.getY() + r;

            float x1 = p2.getX() + r1;
            float y1 = p2.getY() + r1;

            if ((Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1))) <= (r + r1)) {
                return true;
            }
        }

        return false;
    }

    public void makeMove(Canvas c) {


        Vector<Pair<Player, Player>> vec = new Vector<>();


        //move
        for (Player player : players) {

            player.setaX(-player.getvX() * 0.8f);
            player.setaY(-player.getvY() * 0.8f);


            player.setvX(player.getvX() + player.getaX() * fElapsedTime);
            player.setvY(player.getvY() + player.getaY() * fElapsedTime);
            player.setX(player.getX() + player.getvX() * fElapsedTime);
            player.setY(player.getY() + player.getvY() * fElapsedTime);


            //screenCollision
            if (player.getX() < fieldRect.left) {
                player.setX(fieldRect.left);
                player.setvX(-player.getvX());

            }
            if ((player.getX() + player.getBitmap().getWidth()) >= fieldRect.right) {
                player.setX(fieldRect.right - player.getBitmap().getWidth());
                player.setvX(-player.getvX());
            }
            if (player.getY() - 60 < fieldRect.top) {
                player.setY(fieldRect.top + 60);
                player.setvY(-player.getvY());
            }
            if ((player.getY() + player.getBitmap().getHeight()) >= fieldRect.bottom) {
                player.setY(fieldRect.bottom - player.getBitmap().getHeight());
                player.setvY(-player.getvY());
            }


            //crossbar Collision
            if ((player.getX() <= leftGoalRect.right) && ((player.getX() + player.getBitmap().getWidth()) > leftGoalRect.right) && (player.getY() <= leftGoalRect.top) && ((player.getY() + player.getBitmap().getHeight()) >= leftGoalRect.top)) {
                player.setX(leftGoalRect.right);
                player.setvX(-player.getvX());

            }

            if ((player.getX() <= leftGoalRect.right) && ((player.getX() + player.getBitmap().getWidth()) > leftGoalRect.right) && (player.getY() <= leftGoalRect.bottom) && ((player.getY() + player.getBitmap().getHeight()) >= leftGoalRect.bottom)) {
                player.setX(leftGoalRect.right);
                player.setvX(-player.getvX());
            }

            if ((player.getX() <= rightGoalRect.left) && ((player.getX() + player.getBitmap().getWidth()) > rightGoalRect.left) && (player.getY() <= rightGoalRect.top) && ((player.getY() + player.getBitmap().getHeight()) >= rightGoalRect.top)) {
                player.setX(rightGoalRect.left - player.getBitmap().getWidth());
                player.setvX(-player.getvX());
            }

            if ((player.getX() <= rightGoalRect.left) && ((player.getX() + player.getBitmap().getWidth()) > rightGoalRect.left) && (player.getY() <= rightGoalRect.bottom) && ((player.getY() + player.getBitmap().getHeight()) >= rightGoalRect.bottom)) {
                player.setX(rightGoalRect.left - player.getBitmap().getWidth());
                player.setvX(-player.getvX());
            }


            //left goal
            if ((player.getX() < (leftGoalRect.right - 50)) && (player.getY() > leftGoalRect.top) && ((player.getY() + player.getBitmap().getHeight()) < leftGoalRect.bottom)) {

                if (player.isBall()) {
                    //goal
                    gooal.start();
                    team2Goals = team2Goals + 1;

                    init = true;
                    move = true;
                    if (condition == 1 && conditionNum == team2Goals) {
                        status = true;
                    }
                }


            } else if ((player.getX() < (leftGoalRect.right - 50)) && ((player.getY() + player.getBitmap().getHeight()) >= leftGoalRect.top) && ((player.getY() + player.getBitmap().getHeight()) < leftGoalRect.bottom)) {
                //top side
                player.setY(leftGoalRect.top - player.getBitmap().getHeight());
                player.setvY(-player.getvY());
            } else if ((player.getX() < (leftGoalRect.right - 50)) && (player.getY() <= leftGoalRect.bottom) && (player.getY() > leftGoalRect.top)) {
                player.setY(leftGoalRect.bottom);
                player.setvY(-player.getvY());
            }


            //right goal
            if (((player.getX() + player.getBitmap().getWidth()) > rightGoalRect.left) && (player.getY() > rightGoalRect.top) && ((player.getY() + player.getBitmap().getHeight()) < rightGoalRect.bottom)) {

                if (player.isBall()) {
                    //goal
                    gooal.start();
                    team1Goals = team1Goals + 1;
                    init = true;
                    move = true;
                    if (condition == 1 && conditionNum == team1Goals) {
                        status = true;
                    }
                }


            } else if (((player.getX() + player.getBitmap().getWidth()) > rightGoalRect.left) && ((player.getY() + player.getBitmap().getHeight()) >= rightGoalRect.top) && ((player.getY() + player.getBitmap().getHeight()) < rightGoalRect.bottom)) {
                //top side
                player.setY(rightGoalRect.top - player.getBitmap().getHeight());
                player.setvY(-player.getvY());
            } else if (((player.getX() + player.getBitmap().getWidth()) > rightGoalRect.left) && (player.getY() <= rightGoalRect.bottom) && (player.getY() > rightGoalRect.top)) {
                player.setY(rightGoalRect.bottom);
                player.setvY(-player.getvY());
            }


            if (Math.abs(player.getvX() * player.getvX() + player.getvY() * player.getvY()) < 0.01f) {
                player.setvX(0);
                player.setvY(0);
            }

        }


        //collision
        for (Player cPlayer : players) {

            for (Player tPlayer : players) {

                if (cPlayer != tPlayer) {
                    if (checkCollision(cPlayer, tPlayer)) {


                        vec.add(new Pair<Player, Player>(cPlayer, tPlayer));

                        float c1X = cPlayer.getX() + cPlayer.getBitmap().getWidth() / 2;
                        float c1Y = cPlayer.getY() + cPlayer.getBitmap().getWidth() / 2;

                        float c2X = tPlayer.getX() + tPlayer.getBitmap().getWidth() / 2;
                        float c2Y = tPlayer.getY() + tPlayer.getBitmap().getWidth() / 2;


                        float fDistance = (float) Math.sqrt((c1X - c2X) * (c1X - c2X) + (c1Y - c2Y) * (c1Y - c2Y));
                        float fOverlap = 0.5f * (fDistance - (cPlayer.getBitmap().getWidth() / 2) - (tPlayer.getBitmap().getWidth() / 2));

                        cPlayer.setX(cPlayer.getX() - (fOverlap * (c1X - c2X) / fDistance));
                        cPlayer.setY(cPlayer.getY() - (fOverlap * (c1Y - c2Y) / fDistance));

                        tPlayer.setX(tPlayer.getX() + (fOverlap * (c1X - c2X) / fDistance));
                        tPlayer.setY(tPlayer.getY() + (fOverlap * (c1Y - c2Y) / fDistance));


                    }
                }
            }
        }


        //dinamic collision

        for (Pair p : vec) {
            Player p1 = (Player) p.first;
            Player p2 = (Player) p.second;

            if (p1.isBall() || p2.isBall()) {
                ballSound.start();
            }

            float c1X = p1.getX() + p1.getBitmap().getWidth() / 2;
            float c1Y = p1.getY() + p1.getBitmap().getWidth() / 2;

            float c2X = p2.getX() + p2.getBitmap().getWidth() / 2;
            float c2Y = p2.getY() + p2.getBitmap().getWidth() / 2;


            float fDistance = (float) Math.sqrt((c1X - c2X) * (c1X - c2X) + (c1Y - c2Y) * (c1Y - c2Y));
            float nx = (c2X - c1X) / fDistance;
            float ny = (c2Y - c1Y) / fDistance;

            float tx = -ny;
            float ty = nx;


            float dpTan1 = p1.getvX() * tx + p1.getvY() * ty;
            float dpTan2 = p2.getvX() * tx + p2.getvY() * ty;

            float dpNorm1 = p1.getvX() * nx + p1.getvY() * ny;
            float dpNorm2 = p2.getvX() * nx + p2.getvY() * ny;

            float m1Mass = p1.getBitmap().getWidth() * 5;
            float m2Mass = p1.getBitmap().getWidth() * 5;


            float m1 = (dpNorm1 * (m1Mass - m2Mass) + 2.0f * m2Mass * dpNorm2) / (m1Mass + m2Mass);
            float m2 = (dpNorm2 * (m2Mass - m1Mass) + 2.0f * m1Mass * dpNorm1) / (m1Mass + m2Mass);


            p1.setvX(tx * dpTan1 + nx * m1);
            p1.setvY(ty * dpTan1 + ny * m1);
            p2.setvX(tx * dpTan2 + nx * m2);
            p2.setvY(ty * dpTan2 + ny * m2);


        }

        vec.clear();


    }


    public Player checkHomeTeam(float x, float y) {


        for (int i = 0; i < 3; i++) {
            Player pHome = players.get(i);
            if (x > pHome.getX() && x < (pHome.getX() + pHome.getBitmap().getWidth()) && y > pHome.getY() && y < (pHome.getY() + pHome.getBitmap().getHeight())) {

                return pHome;
            }
        }

        return null;
    }

    public Player checkAwayTeam(float x, float y) {
        for (int i = 3; i < players.size() - 1; i++) {
            Player pAway = players.get(i);
            if (x > pAway.getX() && x < (pAway.getX() + pAway.getBitmap().getWidth()) && y > pAway.getY() && y < (pAway.getY() + pAway.getBitmap().getHeight())) {

                return pAway;
            }
        }
        return null;
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

    public Integer getTeam1Goals() {
        return team1Goals;
    }

    public Integer getTeam2Goals() {
        return team2Goals;
    }

    public long getGameTime() {
        return countTime;
    }

    public void setGameTime(final long countTimee) {
        this.gameTime.cancel();
        this.gameTime = new CountDownTimer(countTimee, 1000) {
            public void onTick(long millisUntilFinished) {
                countTime = millisUntilFinished;
                if ((millisUntilFinished / 1000) / 60 > 0) {
                    timerText = "" + (millisUntilFinished / 60000) + " : " + ((millisUntilFinished / 1000) - (millisUntilFinished / 60000) * 60);
                } else {
                    timerText = "" + "0 : " + millisUntilFinished / 1000;

                }


            }

            public void onFinish() {
                status = true;
            }
        };
        this.gameTime.start();

    }

    public void resetTimer() {
        gameTime.cancel();
    }

    public List<Cords> getPositions() {
        List<Cords> postitions = new ArrayList();
        for (Player p : players) {
            postitions.add(new Cords(p.getX(), p.getY()));
        }

        return postitions;

    }

    public void setPositions(List<Cords> positions) {

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setX(positions.get(i).getX());
            players.get(i).setY(positions.get(i).getY());


        }
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

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public void setTeam1Goals(Integer team1Goals) {
        this.team1Goals = team1Goals;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
