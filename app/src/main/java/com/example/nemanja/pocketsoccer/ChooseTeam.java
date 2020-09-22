package com.example.nemanja.pocketsoccer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ChooseTeam extends AppCompatActivity {

    private EditText player1Name;
    private EditText player2Name;
    private ViewPager flagPlayer1;
    private ViewPager flagPlayer2;
    private CheckBox isComputer;
    private Button playButton;
    private Button backButton;
    private FlagsAdapter flagsAdapter;



    private static final String TEAM1 = "team1";
    private static final String TEAM2 = "team2";
    private static final String TEAMNAME1 = "teamname1";
    private static final String TEAMNAME2 = "teamname2";
    private static final String ISCOMPUTER = "iscomputer";
    private static final String PLAYBUTTON = "playbutton";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team);

        player1Name = findViewById(R.id.player1_name);
        player2Name = findViewById(R.id.player2_name);
        flagPlayer1 = findViewById(R.id.flagPlayer1);
        flagPlayer2 = findViewById(R.id.flagPlayer2);
        playButton = findViewById(R.id.play_choose_button);
        backButton = findViewById(R.id.back_choose_button);
        isComputer = findViewById(R.id.isComputer);

        flagsAdapter = new FlagsAdapter(this);

        flagPlayer1.setAdapter(flagsAdapter);
        flagPlayer2.setAdapter(flagsAdapter);



        isComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isComputer.isChecked()){
                    player2Name.setVisibility(View.INVISIBLE);
                }else {
                    player2Name.setVisibility(View.VISIBLE);
                }

            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isComputer.isChecked()){
                    if (!player1Name.getText().toString().isEmpty() && !player2Name.getText().toString().isEmpty()){
                        Intent intent = new Intent(v.getContext(), GameActivity.class);
                        intent.putExtra(TEAM1, flagsAdapter.getSlideImage(flagPlayer1.getCurrentItem()));
                        intent.putExtra(TEAM2, flagsAdapter.getSlideImage(flagPlayer2.getCurrentItem()));
                        intent.putExtra(TEAMNAME1, player1Name.getText().toString());
                        intent.putExtra(TEAMNAME2, player2Name.getText().toString());
                        intent.putExtra(ISCOMPUTER, isComputer.isChecked());
                        intent.putExtra(PLAYBUTTON, true);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(ChooseTeam.this, "Please insert player name", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (!player1Name.getText().toString().isEmpty()){
                        Intent intent = new Intent(v.getContext(), GameActivity.class);
                        intent.putExtra(TEAM1, flagsAdapter.getSlideImage(flagPlayer1.getCurrentItem()));
                        intent.putExtra(TEAM2, flagsAdapter.getSlideImage(flagPlayer2.getCurrentItem()));
                        intent.putExtra(TEAMNAME1, player1Name.getText().toString());
                        intent.putExtra(TEAMNAME2, "Comp");
                        intent.putExtra(ISCOMPUTER, isComputer.isChecked());
                        intent.putExtra(PLAYBUTTON, true);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(ChooseTeam.this, "Please insert player name", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
