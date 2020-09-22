package com.example.nemanja.pocketsoccer.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Match.class, Victory.class}, version = 2, exportSchema = false)
public abstract class MatchDatabase extends RoomDatabase {

    private static MatchDatabase instance;

    public abstract MatchDao matchDao();
    public abstract VictoryDao victoryDao();


    public static synchronized MatchDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MatchDatabase.class, "match_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(matchCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback matchCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MatchDao matchDao;
        private VictoryDao victoryDao;

        public PopulateDbAsyncTask(MatchDatabase matchDatabase) {
            this.matchDao = matchDatabase.matchDao();
            this.victoryDao = matchDatabase.victoryDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            matchDao.insert(new Match("Nemanja", "Nikola", "danas", 4, 0));
            matchDao.insert(new Match("Nemanja", "Nikolaaaa", "danas", 3, 2));
            matchDao.insert(new Match("Nemanjaaaa", "Nikola", "danas", 1, 4));
            return null;
        }
    }
}
