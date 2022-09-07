package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddDrinkActivity extends AppCompatActivity {

    public static double alcohol_percentage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        // SEEKBAR/ALCOHOL PERCENTAGE
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView progress = findViewById(R.id.viewProgress);

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