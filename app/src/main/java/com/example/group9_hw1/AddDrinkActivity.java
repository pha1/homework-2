package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddDrinkActivity extends AppCompatActivity {

    public static double alcohol_percentage = 0;
    public static int drinkSize = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        setTitle("Add Drink");
        // SEEKBAR/ALCOHOL PERCENTAGE
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView progress = findViewById(R.id.viewProgress);

        RadioGroup drink_size_group = findViewById(R.id.drink_size_group);

        // When the user clicks on a new size, the drink size is updated
        drink_size_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // If 1 oz is checked
                if (checkedId == R.id.one_oz){
                    drinkSize = 1;
                }
                // If 5 oz is checked
                else if (checkedId == R.id.five_oz){
                    drinkSize = 5;
                }
                // If 12 oz is checked
                else if (checkedId == R.id.twelve_oz){
                    drinkSize = 12;
                }
            }
        });

        // Initiate the text value for the progress
        progress.setText("0%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            // As the seekbar is used, the percentage displayed is updated
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress.setText(i + "%");
                alcohol_percentage = (double)i/100.0;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}