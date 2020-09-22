package com.example.nemanja.pocketsoccer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final String GAME_PREF = "gamePref";
    private static final String JSON = "json";

    private Button continueButton;
    private Button playButton;
    private Button statisticsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        continueButton = findViewById(R.id.continue_button);
        playButton = findViewById(R.id.play_button);
        statisticsButton = findViewById(R.id.statistic_button);
        settingsButton = findViewById(R.id.settings_button);

        SharedPreferences sharedPreferences = getSharedPreferences(GAME_PREF, MODE_PRIVATE);
        String json = sharedPreferences.getString(JSON, "");

        if (!json.isEmpty()){
            continueButton.setVisibility(View.VISIBLE);
        }else {
            continueButton.setVisibility(View.INVISIBLE);
        }


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(GAME_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(v.getContext(), ChooseTeam.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);

            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Statistics.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(GAME_PREF, MODE_PRIVATE);
        String json = sharedPreferences.getString(JSON, "");

        if (!json.isEmpty()){
            continueButton.setVisibility(View.VISIBLE);
        }else {
            continueButton.setVisibility(View.INVISIBLE);
        }
    }
}
