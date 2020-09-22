package com.example.nemanja.pocketsoccer.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MatchRepository {
    private MatchDao matchDao;
    private LiveData<List<Match>> allMatches;
    private LiveData<List<Match>> matches;

    private VictoryDao victoryDao;
    private LiveData<List<Victory>> allVictory;


    public MatchRepository(Application application) {
        MatchDatabase database = MatchDatabase.getInstance(application);
        matchDao = database.matchDao();
        allMatches = matchDao.getAllMatches();
        victoryDao = database.victoryDao();
        allVictory = victoryDao.getAllVictory();
    }

    public void insert(Match match) {
        new InsertMatchAsyncTask(matchDao).execute(match);
    }

    public void update(Match match) {
        new UpdateMatchAsyncTask(matchDao).execute(match);
    }

    public void delete(Match match) {
        new DeleteMatchAsyncTask(matchDao).execute(match);

    }

    public void deleteAllMatches() {
        new DeleteAllMatchesAsyncTask(matchDao).execute();
    }

    public void deleteMatchesByPlayer(String p1Name, String p2Name) {
        new DeleteMatchesByPlayerAsyncTask(matchDao).execute(p1Name, p2Name);
    }

    public LiveData<List<Match>> getAllMatches() {
        return allMatches;
    }

    public LiveData<List<Match>> getMatchesByPlayer(String p1Name, String p2Name) {
        return matchDao.getMatchesByPlayer(p1Name, p2Name);
    }

    private static class InsertMatchAsyncTask extends AsyncTask<Match, Void, Void> {

        private MatchDao matchDao;

        private InsertMatchAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.insert(matches[0]);
            return null;
        }
    }

    private static class UpdateMatchAsyncTask extends AsyncTask<Match, Void, Void> {

        private MatchDao matchDao;

        private UpdateMatchAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.update(matches[0]);
            return null;
        }
    }

    private static class DeleteMatchAsyncTask extends AsyncTask<Match, Void, Void> {

        private MatchDao matchDao;

        private DeleteMatchAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.delete(matches[0]);
            return null;
        }
    }

    private static class DeleteAllMatchesAsyncTask extends AsyncTask<Void, Void, Void> {

        private MatchDao matchDao;

        private DeleteAllMatchesAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            matchDao.deleteAllMatches();
            return null;
        }
    }

    private static class DeleteMatchesByPlayerAsyncTask extends AsyncTask<String, String, Void> {
        private MatchDao matchDao;

        private DeleteMatchesByPlayerAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            matchDao.deleteMatchesByPlayer(strings[0], strings[1]);
            return null;
        }
    }

    //Victory

    public void insert(Victory victory) {
        new InsertVictoryAsyncTask(victoryDao).execute(victory);
    }

    public void update(Victory victory) {
        new UpdateVictoryAsyncTask(victoryDao).execute(victory);
    }

    public void delete(Victory victory) {
        new DeleteVictoryAsyncTask(victoryDao).execute(victory);

    }

    public void deleteAllVictory() {
        new DeleteAllVictoryAsyncTask(victoryDao).execute();
    }

    public LiveData<List<Victory>> getAllVictory(){
        return allVictory;
    }

    public Victory getVictoryByPlayer(String p1Name, String p2Name){
        return victoryDao.getVictoryByPlayer(p1Name,p2Name);
    }

    public void deleteVictoryByPlayer(String p1Name, String p2Name) {
        new DeleteVictoryByPlayerAsyncTask(victoryDao).execute(p1Name, p2Name);
    }

    private static class InsertVictoryAsyncTask extends AsyncTask<Victory, Void, Void> {

        private VictoryDao victoryDao;

        private InsertVictoryAsyncTask(VictoryDao victoryDao) {
            this.victoryDao = victoryDao;
        }


        @Override
        protected Void doInBackground(Victory... victories) {
            victoryDao.insert(victories[0]);
            return null;
        }
    }

    private static class UpdateVictoryAsyncTask extends AsyncTask<Victory, Void, Void> {

        private VictoryDao victoryDao;

        private UpdateVictoryAsyncTask(VictoryDao victoryDao) {
            this.victoryDao = victoryDao;
        }


        @Override
        protected Void doInBackground(Victory... victories) {
            victoryDao.update(victories[0]);
            return null;
        }
    }

    private static class DeleteVictoryAsyncTask extends AsyncTask<Victory, Void, Void> {

        private VictoryDao victoryDao;

        private DeleteVictoryAsyncTask(VictoryDao victoryDao) {
            this.victoryDao = victoryDao;
        }


        @Override
        protected Void doInBackground(Victory... victories) {
            victoryDao.delete(victories[0]);
            return null;
        }
    }

    private static class DeleteAllVictoryAsyncTask extends AsyncTask<Void, Void, Void>{

        private VictoryDao victoryDao;

        public DeleteAllVictoryAsyncTask(VictoryDao victoryDao){
            this.victoryDao = victoryDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            victoryDao.deleteAllVictory();
            return null;
        }
    }

    private static class DeleteVictoryByPlayerAsyncTask extends AsyncTask<String,String, Void>{

        private VictoryDao victoryDao;

        public DeleteVictoryByPlayerAsyncTask(VictoryDao victoryDao){
            this.victoryDao = victoryDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            victoryDao.deleteVictoryByPlayer(strings[0], strings[1]);
            return null;
        }
    }


}
