/**
 * Homework 1
 * Phi Ha
 * Srinath Dittakavi
 */

package com.example.group9_hw1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static double bac = 0;
    public static ArrayList<Drink> drinks = new ArrayList<>();
    public static boolean notSafe = false;
    public static boolean weightSet = false;

    Profile profile;

    // Receives the results from department activity
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getStringExtra(SetProfileActivity.PROFILE_KEY) != null){
                    profile = result.getData().getParcelableExtra(SetProfileActivity.PROFILE_KEY);
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Profile Activity
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(MainActivity.this, SetProfileActivity.class);
                startForResult.launch(profileIntent);
            }
        });

        TextView weightDisplay = findViewById(R.id.weightDisplay);
        TextView numDrinkDisplay = findViewById(R.id.numDrinkDisplay);
        TextView BACnum = findViewById(R.id.BACNum);
        Button addDrinkButton = findViewById(R.id.addDrinkButton2);

        // Add Drink Button
        // Adds a drink to the number of drinks
        // Also calls on a method that calculates the BAC Level and displays it
        /*
        findViewById(R.id.addDrinkButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!weightSet){
                        throw new Exception();
                    }

                    numDrinks++;
                    // Create a new drink object
                    Drink drink = new Drink(alcohol_percentage, drinkSize);
                    // Add the drink object to the ArrayList
                    drinks.add(drink);
                    // Display the new number of drinks
                    numDrinkDisplay.setText(String.valueOf(numDrinks));
                    // Calculate BAC and display

                    bac = calculateBAC(output, weight);
                    BACnum.setText(String.format("%.3f", bac));
                    // Check to see if no more drinks should be given
                    checkBACLevel(bac);

                    // If the BAC Level is above 0.25, notSafe is true
                    // Disable Add Drink button
                    if (notSafe) {
                        Toast.makeText(MainActivity.this, "No more drinks for you.", Toast.LENGTH_LONG).show();
                        addDrinkButton.setEnabled(false);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Please set a weight and gender", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Reset all content
        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Global Variables set back to default
                output = "";
                drinkSize = 1;
                alcohol_percentage = 0;
                numDrinks = 0;
                weight = 0;
                bac = 0;
                notSafe = false;
                weightSet = false;

                // Set components' values back to default
                weightDisplay.setText(getResources().getText(R.string.weight_display));
                numDrinkDisplay.setText(getResources().getText(R.string.num_drinks));
                genderGroup.clearCheck();
                genderGroup.check(R.id.radioFemale);
                drink_size_group.check(R.id.one_oz);
                drinks.clear();
                addDrinkButton.setEnabled(true);

                // Set status message to default via strings.xml
                TextView status = findViewById(R.id.status);
                status.setText(getResources().getText(R.string.status));
                status.setBackgroundColor(getResources().getColor(R.color.green));

                // Set BAC Level to 0.000 via strings.xml
                TextView BACNum = findViewById(R.id.BACNum);
                BACNum.setText(getResources().getText(R.string.BAC_num));
            }
        });
        */
    }



    /**
     * This method calculates the BAC Level with the given formula
     * % BAC = A * 5.14 / Weight * r
     * @param gender the gender selected
     * @param weight the weight entered
     * @return double value of the % BAC
     */
    /*
    public double calculateBAC(String gender, int weight){
        double bac;
        double r;
        double a = 0;

        // Set the r value for female
        if (gender.equals("Female")){
            r = .66;
        }
        // Set the r value for male
        else {
            r = .73;
        }

        for (Drink drink : drinks){
            a += drink.size * drink.alcohol_percentage;
        }

        // BAC Formula
        bac = (a * 5.14) / (weight * r);

        return bac;
    }


     */

    /**
     * This method checks the BAC Level to determine the status message
     * It also sets the boolean notSafe to true if the BAC level is higher than .25
     * @param bac double value used to determine the status
     */
    /*
    public void checkBACLevel(double bac){

        TextView status = findViewById(R.id.status);

        // Sets the status to "Be careful." and changes the color to orange
        if (0.08 <= bac && bac <= 0.2){
            status.setText(getResources().getText(R.string.status2));
            status.setBackgroundColor(getResources().getColor(R.color.orange));
        }

        // Sets the status to "Over the limit!" and changes the color to red
        else if (bac > 0.2 && bac < 0.25){
            status.setText(getResources().getText(R.string.status3));
            status.setBackgroundColor(getResources().getColor(R.color.red));
        }
        // Once the BAC reaches over 0.25, change the boolean value of notSafe
        // This will disable the "Add Drink" button
        else if (bac >= 0.25)
        {
            notSafe = true;

        }
    }
     */
}