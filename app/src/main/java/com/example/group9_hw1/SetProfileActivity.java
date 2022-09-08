package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SetProfileActivity extends AppCompatActivity {

    public static int weight = 0;
    public static String gender;
    final public static String PROFILE_KEY = "PROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        setTitle("Set Weight/Gender");

        EditText editText = findViewById(R.id.editWeight);
        RadioGroup genderGroup = findViewById(R.id.gender_group);

        gender = "Female";

        // Check to see which radio button is checked
        // and apply the gender to the display
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioFemale){
                    gender = "Female";
                }
                else if (checkedId == R.id.radioMale){
                    gender = "Male";
                }
            }
        });

        // SET WEIGHT
        findViewById(R.id.setWeight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Check if the value can be parsed into an int
                    weight = Integer.parseInt(editText.getText().toString());

                    // If the number is less than 0, show the Toast message
                    if (weight < 0) {
                        throw new IllegalArgumentException();
                    }

                    Profile profile = new Profile(gender, weight);

                    Intent intent = new Intent(SetProfileActivity.this, MainActivity.class);
                    intent.putExtra(PROFILE_KEY, profile);
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (Exception e)
                {
                    // Toast message when a positive number is not entered
                    Toast.makeText(SetProfileActivity.this, "Please enter a valid positive" +
                            " number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Close Set Profile Activity without any information
        findViewById(R.id.setWeight2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });
    }
}