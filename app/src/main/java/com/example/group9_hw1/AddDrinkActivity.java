package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddDrinkActivity extends AppCompatActivity {

    public static double alcohol_percentage = 0;
    public static int drinkSize = 1;
    final public static String ADD_DRINK_KEY = "ADD_DRINK";
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;

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

        findViewById(R.id.addDrinkButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
                date = simpleDateFormat.format(calendar.getTime());

                // Create Drink object with selected data
                Drink drink = new Drink(alcohol_percentage, drinkSize, date);


                // Intent to send the drink object back to the Main Activity
                Intent returnDrink = new Intent(AddDrinkActivity.this, MainActivity.class);
                returnDrink.putExtra(ADD_DRINK_KEY, drink);
                setResult(RESULT_OK, returnDrink);
                finish();
            }
        });

        findViewById(R.id.cancelButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}