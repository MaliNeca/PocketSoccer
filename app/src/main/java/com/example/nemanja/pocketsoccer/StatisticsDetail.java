package com.example.nemanja.pocketsoccer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.nemanja.pocketsoccer.dao.Match;
import com.example.nemanja.pocketsoccer.view.MatchViewModel;



import java.util.ArrayList;
import java.util.List;

public class StatisticsDetail extends AppCompatActivity {

    private TableLayout tableLayout;
    private ScrollView scrollView;
    private Button backButton;
    private Button resetButton;
    private TextView p1Name;
    private TextView p2Name;
    private TextView p1Wons ;
    private TextView p2Wons;
    private TextView time;
    private TextView p1Goals;
    private TextView p2Goals;
    private TableRow tableRow;
    private String player1Name;
    private String player2Name;
    private int player1Wons = 0;
    private int player2Wons = 0;



    private MatchViewModel matchViewModel;
    private List<Match> allMatchesByPlayer = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_detail);

        Intent intent = getIntent();
        player1Name = intent.getStringExtra("p1Name");
        player2Name = intent.getStringExtra("p2Name");

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getMatchesByPlayer(intent.getStringExtra("p1Name"), intent.getStringExtra("p2Name"))
                .observe(this, new Observer<List<Match>>() {

                    @Override
                    public void onChanged(@Nullable List<Match> matches) {
                        setMatches(matches);

                    }
                });

        p1Name = findViewById(R.id.p1_name_statistics_detail);
        p2Name = findViewById(R.id.p2_name_statistics_detail);
        p1Wons = findViewById(R.id.p1_name_statistics_detail_wons);
        p2Wons =findViewById(R.id.p2_name_statistics_detail_wons);
        time = findViewById(R.id.time_statistics_detail);
        p1Goals = findViewById(R.id.p1_goals_statistics_detail);
        p2Goals = findViewById(R.id.p2_goals_statistics_detail);
        tableRow = findViewById(R.id.row_statistics_detail);
        tableLayout = findViewById(R.id.tableStatistics_detail);
        scrollView = findViewById(R.id.scrollStatistics_detail);
        backButton = findViewById(R.id.back_statistics_button_detail);
        resetButton = findViewById(R.id.reset_statistics_button_detail);

        p1Name.setText(intent.getStringExtra("p1Name"));
        p2Name.setText(intent.getStringExtra("p2Name"));





        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeViews(3, allMatchesByPlayer.size());
                p1Wons.setVisibility(View.INVISIBLE);
                p2Wons.setVisibility(View.INVISIBLE);
                p1Name.setVisibility(View.INVISIBLE);
                p2Name.setVisibility(View.INVISIBLE);
                matchViewModel.deleteVictoryByPlayer(player1Name, player2Name);
                matchViewModel.deleteMatchesByPlayer(player1Name,player2Name);

            }
        });

    }

    public void setMatches(List<Match> matches) {
        this.allMatchesByPlayer = matches;
        updateTable();
    }

    public void updateTable() {

        for (Match m: allMatchesByPlayer){
            if (m.getP1Goals() != m.getP2Goals()){
            if (m.getP1Name().equals(player1Name) && m.getP2Name().equals(player2Name)){
                if (m.getP1Goals()>m.getP2Goals()){
                    player1Wons++;
                }else {
                    player2Wons++;
                }
            }

            if (m.getP1Name().equals(player2Name) && m.getP2Name().equals(player1Name)){
                if (m.getP1Goals()>m.getP2Goals()){
                    player2Wons++;
                }else {
                    player1Wons++;
                }
            }}


        }

        for (int i = 0; i < allMatchesByPlayer.size(); i++) {

            final TableRow tableRow1 = new TableRow(this);
            tableRow1.setLayoutParams(tableRow.getLayoutParams());

            TextView time1 = new TextView(this);
            TextView p1Goals1 = new TextView(this);
            TextView p2Goals2 = new TextView(this);

            p1Wons.setText("" + player1Wons);
            p2Wons.setText("" + player2Wons);

            time1.setLayoutParams(time.getLayoutParams());
            time1.setBackground(time.getBackground());
            time1.setGravity(time.getGravity());


            p1Goals1.setLayoutParams(p1Goals.getLayoutParams());
            p1Goals1.setBackground(p1Goals.getBackground());
            p1Goals1.setGravity(p1Goals.getGravity());

            p2Goals2.setLayoutParams(p2Goals.getLayoutParams());
            p2Goals2.setBackground(p2Goals.getBackground());
            p2Goals2.setGravity(p2Goals.getGravity());


            time1.setText(allMatchesByPlayer.get(i).getTime());
            if (allMatchesByPlayer.get(i).getP1Name().equals(player1Name)){
                p1Goals1.setText("" +allMatchesByPlayer.get(i).getP1Goals());
                p2Goals2.setText(""+allMatchesByPlayer.get(i).getP2Goals());
            }else {
                p1Goals1.setText("" +allMatchesByPlayer.get(i).getP2Goals());
                p2Goals2.setText(""+allMatchesByPlayer.get(i).getP1Goals());
            }






            tableRow1.addView(time1);
            tableRow1.addView(p1Goals1);
            tableRow1.addView(p2Goals2);


            tableLayout.addView(tableRow1);


        }
    }
}
