package com.example.nemanja.pocketsoccer.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MatchDao {

    @Insert
    void insert(Match match);

    @Update
    void update(Match match);

    @Delete
    void delete(Match match);

    @Query("DELETE FROM match_table")
    void deleteAllMatches();
    @Query("DELETE FROM match_table WHERE (p1Name = :p1Name AND p2Name = :p2Name) OR (p1Name = :p2Name AND p2Name = :p1Name)")
    void deleteMatchesByPlayer(String p1Name, String p2Name);

    @Query("SELECT * FROM match_table ORDER BY id ")
    LiveData<List<Match>> getAllMatches();

    @Query("SELECT * FROM match_table WHERE (p1Name = :p1Name AND p2Name = :p2Name) OR (p1Name = :p2Name AND p2Name = :p1Name) ORDER BY id desc")
    LiveData<List<Match>> getMatchesByPlayer(String p1Name, String p2Name);



}
