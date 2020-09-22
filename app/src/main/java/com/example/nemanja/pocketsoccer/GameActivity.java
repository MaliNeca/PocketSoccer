package com.example.nemanja.pocketsoccer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.nemanja.pocketsoccer.dao.Match;
import com.example.nemanja.pocketsoccer.dao.Victory;
import com.example.nemanja.pocketsoccer.model.ContinueGameModel;
import com.example.nemanja.pocketsoccer.view.MatchViewModel;
import com.example.nemanja.pocketsoccer.view.PocketSoccerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GameActivity extends AppCompatActivity {

    private PocketSoccerView gameView;
    private static final String GAME_PREF = "gamePref";
    private static final String JSON = "json";
    private static final String TEAM1 = "team1";
    private static final String TEAM2 = "team2";
    private static final String TEAMNAME1 = "teamname1";
    private static final String TEAMNAME2 = "teamname2";
    private static final String ISCOMPUTER = "iscomputer";
    private static final String PLAYBUTTON = "playbutton";

    private MatchViewModel matchViewModel;
    private int p1, p2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        Intent intent = getIntent();

        if (intent.getBooleanExtra(PLAYBUTTON, false)){
            p1 = intent.getIntExtra(TEAM1, 2131099764);
            p2 = intent.getIntExtra(TEAM2, 2131099765);
            gameView = new PocketSoccerView(this, p1, p2, intent.getStringExtra(TEAMNAME1), intent.getStringExtra(TEAMNAME2), intent.getBooleanExtra(ISCOMPUTER, false),true);

        }

        SharedPreferences sharedPreferences = getSharedPreferences(GAME_PREF, MODE_PRIVATE);
        String json = sharedPreferences.getString(JSON, "");
        if (!json.isEmpty()){
            ContinueGameModel continueGameModel = gson.fromJson(json,ContinueGameModel.class);
            p1 = continueGameModel.getP1();
            p2 = continueGameModel.getP2();
            gameView = new PocketSoccerView(this, p1, p2, continueGameModel.getP1Name(), continueGameModel.getP2Name(), continueGameModel.isComputer(),continueGameModel.isMove());

            gameView.setPositions(continueGameModel.getPositions());
            gameView.setInit(continueGameModel.isInit());
            gameView.setMove(continueGameModel.isMove());
            gameView.setCurrentPlayer(continueGameModel.getCurrentPlayer());
            gameView.setfElapsedTime(continueGameModel.getfElapsedTime());
            gameView.setCondition(continueGameModel.getCondtition());
            gameView.setComputer(continueGameModel.isComputer());
            gameView.setTeam1Goals(continueGameModel.getTeam1Goals());
            gameView.setTeam2Goals(continueGameModel.getTeam2Goals());
            gameView.setConditionNum(continueGameModel.getConditionNum());
            if (continueGameModel.getCondtition() == 2){
                gameView.setGameTime(continueGameModel.getCountTime());
            }
            gameView.setStatus(continueGameModel.isStatus());
       }

        setContentView(gameView);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameView.isStatus()) {
                    //do nothing
                }
                finishGame();

            }
        });
        thread.start();

    }

    public void finishGame() {

        //clear shared preference
        SharedPreferences sharedPreferences = getSharedPreferences(GAME_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy  HH:mm:ss");
        String currentTime = sdf.format(new Date());

        Victory victory = matchViewModel.getVictoryByPlayer(gameView.getP1Name(), gameView.getP2Name());

        if (victory != null) {
            if (gameView.getTeam1Goals() != gameView.getTeam2Goals()) {
                if (gameView.getTeam1Goals() > gameView.getTeam2Goals()) {
                    victory.setP1Win(victory.getP1Win() + 1);
                } else {
                    victory.setP2Win(victory.getP2Win() + 1);
                }

                matchViewModel.update(victory);
            }
        } else {
            if (gameView.getTeam1Goals() != gameView.getTeam2Goals()) {
                if (gameView.getTeam1Goals() > gameView.getTeam2Goals()) {
                    matchViewModel.insert(new Victory(gameView.getP1Name(), gameView.getP2Name(), 1, 0));

                } else {
                    matchViewModel.insert(new Victory(gameView.getP1Name(), gameView.getP2Name(), 0, 1));
                }
            }else {
                matchViewModel.insert(new Victory(gameView.getP1Name(), gameView.getP2Name(), 0, 0));
            }
        }

        Match match = new Match(gameView.getP1Name(), gameView.getP2Name(), currentTime, gameView.getTeam1Goals(), gameView.getTeam2Goals());
        matchViewModel.insert(match);


        Intent intent = new Intent(this, StatisticsDetail.class);
        intent.putExtra("p1Name", gameView.getP1Name());
        intent.putExtra("p2Name", gameView.getP2Name());

        startActivity(intent);
        finish();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();



    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!gameView.isStatus()){
            saveData();
        }

    }



    public void saveData(){
        ContinueGameModel continueModel = new ContinueGameModel();
        continueModel.setP1(p1);
        continueModel.setP2(p2);
        continueModel.setPositions(gameView.getPositions());
        continueModel.setInit(gameView.isInit());
        continueModel.setMove(gameView.isMove());
        continueModel.setCurrentPlayer(gameView.getCurrentPlayer());
        continueModel.setfElapsedTime(gameView.getfElapsedTime());
        continueModel.setCondtition(gameView.getCondition());
        continueModel.setP1Name(gameView.getP1Name());
        continueModel.setP2Name(gameView.getP2Name());
        continueModel.setComputer(gameView.isComputer());
        continueModel.setTeam1Goals(gameView.getTeam1Goals());
        continueModel.setTeam2Goals(gameView.getTeam2Goals());
        continueModel.setConditionNum(gameView.getConditionNum());
        continueModel.setStatus(gameView.isStatus());
        if (gameView.getCondition() == 2){
            continueModel.setCountTime(gameView.getGameTime());
            gameView.resetTimer();
        }

        Gson gson = new Gson();
        String json = gson.toJson(continueModel);
        SharedPreferences sharedPreferences = getSharedPreferences(GAME_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JSON, json);
        editor.apply();
    }
}
