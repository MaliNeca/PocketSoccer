package com.example.nemanja.pocketsoccer;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private ViewPager courtPager;
    private SliderAdapter sliderAdapter;
    private Spinner spinnerGoals;
    private Spinner spinnerSpeed;
    private Button saveButton;
    private Button backButton;
    private RadioGroup radioGroup;
    private RadioButton radioButtonGoal;
    private RadioButton radioButtonTime;
    private Button resetButton;


    private  static final String SHARED_PREFS = "sharedPref";
    private  static final String SPEED = "speed";
    public static final String TYPE ="type";
    public static final String NUMBER = "number";
    public static final String COURT = "court";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        courtPager = findViewById(R.id.courtPager);
        spinnerGoals = findViewById(R.id.spinnerGoals);
        spinnerSpeed = findViewById(R.id.spinnerSpeed);
        saveButton = findViewById(R.id.save_settings_button);
        backButton = findViewById(R.id.back_settings_button);
        radioGroup = findViewById(R.id.radioGroupSettings);
        radioButtonGoal = findViewById(R.id.radioGoal);
        radioButtonTime = findViewById(R.id.radioTime);
        resetButton = findViewById(R.id.reset_settings_button);

        sliderAdapter = new SliderAdapter(this);

        courtPager.setAdapter(sliderAdapter);

        List goals = new ArrayList();
        goals.add(1);
        goals.add(3);
        goals.add(5);
        goals.add(7);
        goals.add(10);

        ArrayAdapter adapterGoals =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, goals);
        adapterGoals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGoals.setAdapter(adapterGoals);

        List speed = new ArrayList();
        speed.add(1);
        speed.add(2);
        speed.add(3);
        speed.add(4);
        speed.add(5);

        ArrayAdapter adapterSpeed =  new ArrayAdapter(this, android.R.layout.simple_spinner_item, speed);
        adapterSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSpeed.setAdapter(adapterSpeed);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDefaultData();
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();

    }

    public void loadDefaultData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putInt(TYPE, 1);
        editor.putInt(NUMBER, 0);
        editor.putInt(SPEED, 2);
        editor.putInt(COURT, 2131099739);
        editor.apply();



    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (radioButtonGoal.isChecked()){
            editor.putInt(TYPE, 1);
        }else if (radioButtonTime.isChecked()){
            editor.putInt(TYPE, 2);
        }else {
            editor.putInt(TYPE, 3);
        }


        editor.putInt(NUMBER, spinnerGoals.getSelectedItemPosition());
        editor.putInt(SPEED, spinnerSpeed.getSelectedItemPosition());
        editor.putInt(COURT, sliderAdapter.getSlideImage(courtPager.getCurrentItem()));
        editor.apply();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);


        if (sharedPreferences.getInt(TYPE,1 ) == 1){
            radioGroup.check(radioButtonGoal.getId());
        }else if (sharedPreferences.getInt(TYPE,1) == 2){
            radioGroup.check(radioButtonTime.getId());
        }
        //radioGroup.check(sharedPreferences.getInt(TYPE,1));
        spinnerGoals.setSelection(sharedPreferences.getInt(NUMBER,0));
        spinnerSpeed.setSelection(sharedPreferences.getInt(SPEED,2));
        courtPager.setCurrentItem(sliderAdapter.getPositionImage(sharedPreferences.getInt(COURT,2131099741)));


    }

}
