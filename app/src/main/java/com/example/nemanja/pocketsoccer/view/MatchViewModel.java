package com.example.nemanja.pocketsoccer.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.nemanja.pocketsoccer.dao.Match;
import com.example.nemanja.pocketsoccer.dao.MatchRepository;
import com.example.nemanja.pocketsoccer.dao.Victory;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {

    private MatchRepository matchRepository;
    private LiveData<List<Match>> allMatches;
    private LiveData<List<Victory>> allVictory;


    public MatchViewModel(@NonNull Application application) {
        super(application);

        matchRepository = new MatchRepository(application);
        allMatches = matchRepository.getAllMatches();
        allVictory = matchRepository.getAllVictory();
    }

    //Match
    public void insert(Match match) {
        matchRepository.insert(match);
    }

    public void update(Match match) {
        matchRepository.update(match);
    }

    public void delete(Match match) {
        matchRepository.delete(match);
    }

    public void deleteAllMatches() {
        matchRepository.deleteAllMatches();

    }

    public void deleteMatchesByPlayer(String p1Name, String p2Name) {
        matchRepository.deleteMatchesByPlayer(p1Name, p2Name);
    }

    public LiveData<List<Match>> getAllMatches() {
        return allMatches;
    }

    public LiveData<List<Match>> getMatchesByPlayer(String p1Name, String p2Name) {
        return matchRepository.getMatchesByPlayer(p1Name, p2Name);
    }

    //Victory

    public void insert(Victory victory) {
        matchRepository.insert(victory);
    }

    public void update(Victory victory) {
        matchRepository.update(victory);
    }

    public void delete(Victory victory) {
        matchRepository.delete(victory);
    }

    public void deleteAllVictory() {
        matchRepository.deleteAllVictory();

    }

    public LiveData<List<Victory>> getAllVictory() {
        return allVictory;
    }

    public Victory getVictoryByPlayer(String p1Name, String p2Name) {
        return matchRepository.getVictoryByPlayer(p1Name, p2Name);
    }
    public void deleteVictoryByPlayer(String p1Name, String p2Name) {
        matchRepository.deleteVictoryByPlayer(p1Name, p2Name);
    }

}
