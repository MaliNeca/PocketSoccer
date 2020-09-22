package com.example.nemanja.pocketsoccer;


import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



import com.example.nemanja.pocketsoccer.dao.Match;
import com.example.nemanja.pocketsoccer.dao.Victory;
import com.example.nemanja.pocketsoccer.view.MatchViewModel;



import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {

    private TableLayout tableLayout;
    private ScrollView scrollView;
    private Button backButton;
    private Button resetButton;
    private TextView p1Name;
    private TextView p2Name;
    private TextView p1Win;
    private TextView p2Win;
    private TableRow tableRow;

    private MatchViewModel matchViewModel;
    private List<Victory> allVictory = new ArrayList<>();
    private List<Match> allMatches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistisc);
        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getAllMatches().observe(this, new Observer<List<Match>>() {

            @Override
            public void onChanged(@Nullable List<Match> matches) {
                setMatches(matches);

            }
        });

        matchViewModel.getAllVictory().observe(this, new Observer<List<Victory>>() {
            @Override
            public void onChanged(@Nullable List<Victory> victories) {
                setVictories(victories);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tableLayout = findViewById(R.id.tableStatistics);
        scrollView = findViewById(R.id.scrollStatistics);
        backButton = findViewById(R.id.back_statistics_button);
        resetButton = findViewById(R.id.reset_statistics_button);
        p1Name = findViewById(R.id.p1_name_statistics);
        p2Name = findViewById(R.id.p2_name_statistics);
        p1Win = findViewById(R.id.p1_wons_statistics);
        p2Win = findViewById(R.id.p2_wons_statistics);
        tableRow = findViewById(R.id.row_statistics);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeViews(1, allVictory.size());
                matchViewModel.deleteAllVictory();
                matchViewModel.deleteAllMatches();

            }
        });
    }


    public void setVictories(List<Victory> victories) {
        this.allVictory = victories;
        updateTable();
    }

    public void setMatches(List<Match> matches) {
        this.allMatches = matches;
        // updateTable();
    }

    public void updateTable() {


        while (tableLayout.getChildCount() > 1)
            tableLayout.removeView(tableLayout.getChildAt(tableLayout.getChildCount() - 1));


        for (int i = 0; i < allVictory.size(); i++) {

            final TableRow tableRow1 = new TableRow(this);
            tableRow1.setLayoutParams(tableRow.getLayoutParams());

            final TextView p1Name1 = new TextView(this);
            TextView p2Name2 = new TextView(this);
            TextView p1Win1 = new TextView(this);
            TextView p2Win2 = new TextView(this);


            p1Name1.setLayoutParams(p1Name.getLayoutParams());
            p1Name1.setBackground(p1Name.getBackground());
            p1Name1.setGravity(p1Name.getGravity());


            p2Name2.setLayoutParams(p2Name.getLayoutParams());
            p2Name2.setBackground(p2Name.getBackground());
            p2Name2.setGravity(p2Name.getGravity());

            p1Win1.setLayoutParams(p1Win.getLayoutParams());
            p1Win1.setBackground(p1Win.getBackground());
            p1Win1.setGravity(p1Win.getGravity());

            p2Win2.setLayoutParams(p2Win.getLayoutParams());
            p2Win2.setBackground(p2Win.getBackground());
            p2Win2.setGravity(p2Win.getGravity());


            p1Name1.setText(allVictory.get(i).getP1Name());
            p2Name2.setText(allVictory.get(i).getP2Name());
            p1Win1.setText("" + allVictory.get(i).getP1Win());
            p2Win2.setText("" + allVictory.get(i).getP2Win());


            tableRow1.addView(p1Name1);
            tableRow1.addView(p2Name2);
            tableRow1.addView(p1Win1);
            tableRow1.addView(p2Win2);

            tableLayout.addView(tableRow1);


            tableRow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), StatisticsDetail.class);

                    TextView view = (TextView) tableRow1.getChildAt(0);
                    intent.putExtra("p1Name", view.getText().toString());
                    view = (TextView) tableRow1.getChildAt(1);
                    intent.putExtra("p2Name", view.getText().toString());


                    //Toast.makeText(Statistics.this, view.getText().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tableLayout.removeViews(1, allVictory.size());
        matchViewModel.deleteAllVictory();
        matchViewModel.deleteAllMatches();

        return super.onOptionsItemSelected(item);
    }
}
