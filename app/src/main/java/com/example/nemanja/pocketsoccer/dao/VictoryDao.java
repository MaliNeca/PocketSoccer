package com.example.nemanja.pocketsoccer.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VictoryDao {

    @Insert
    void insert(Victory victory);

    @Update
    void update(Victory victory);

    @Delete
    void delete(Victory victory);

    @Query("DELETE FROM victory_table")
    void deleteAllVictory();

    @Query("SELECT * FROM victory_table ORDER BY id ")
    LiveData<List<Victory>> getAllVictory();

    @Query("SELECT * FROM victory_table WHERE p1Name = :p1Name AND p2Name = :p2Name")
    Victory getVictoryByPlayer(String p1Name, String p2Name);

    @Query("DELETE FROM victory_table WHERE (p1Name = :p1Name AND p2Name = :p2Name) OR (p1Name = :p2Name AND p2Name = :p1Name)")
    void deleteVictoryByPlayer(String p1Name, String p2Name);

}
