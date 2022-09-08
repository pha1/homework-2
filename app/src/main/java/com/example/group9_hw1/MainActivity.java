/**
 * Homework 2
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

    //public static double bac = 0;
    public static ArrayList<Drink> drinks = new ArrayList<>();
    public static boolean notSafe = false;
    //public static boolean weightSet = false;

    TextView weightDisplay;
    Profile profile = new Profile();
    Drink drink = new Drink();

    // Receives the results from Set Profile activity
    ActivityResultLauncher<Intent> setProfileResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getParcelableExtra((SetProfileActivity.PROFILE_KEY)) != null){
                    profile = result.getData().getParcelableExtra(SetProfileActivity.PROFILE_KEY);
                    String display = String.valueOf(profile.weight) + " lbs (" + profile.gender + ")";
                    weightDisplay.setText(display);
                }
            }
        }
    });

    // Receives the results from View Drinks activity
    ActivityResultLauncher<Intent> startForDrinks = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getParcelableExtra((ViewDrinksActivity.VIEW_DRINKS_KEY)) != null){
                    // ** UPDATE BAC VALUES HERE **
                }
            }
        }
    });

    // Receives the results from Add Drink activity
    ActivityResultLauncher<Intent> addDrinkResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getParcelableExtra((AddDrinkActivity.ADD_DRINK_KEY)) != null){
                    drink = result.getData().getParcelableExtra(AddDrinkActivity.ADD_DRINK_KEY);
                    // Add Drink to ArrayList
                    drinks.add(drink);
                    // ** UPDATE BAC VALUES HERE **
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
                weightDisplay = findViewById(R.id.weightDisplay);
                setProfileResult.launch(profileIntent);
            }
        });


        // View Drinks
        findViewById(R.id.viewDrinksButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewDrinksIntent = new Intent(MainActivity.this, ViewDrinksActivity.class);
                startForDrinks.launch(viewDrinksIntent);
            }
        });

        TextView numDrinkDisplay = findViewById(R.id.numDrinkDisplay);
        TextView BACnum = findViewById(R.id.BACNum);
        Button addDrinkButton = findViewById(R.id.addDrinkButton2);

        // Add Drink Button
        findViewById(R.id.addDrinkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDrinkIntent = new Intent(MainActivity.this, AddDrinkActivity.class);
                addDrinkResult.launch(addDrinkIntent);
            }
        });

        // Reset all content
        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set components' values back to default
                weightDisplay.setText(getResources().getText(R.string.weight_display));
                numDrinkDisplay.setText(getResources().getText(R.string.num_drinks));
                drinks.clear();
                //addDrinkButton.setEnabled(true);
                notSafe = false;

                // Set status message to default via strings.xml
                TextView status = findViewById(R.id.status);
                status.setText(getResources().getText(R.string.status));
                status.setBackgroundColor(getResources().getColor(R.color.green));

                // Set BAC Level to 0.000 via strings.xml
                TextView BACNum = findViewById(R.id.BACNum);
                BACNum.setText(getResources().getText(R.string.BAC_num));
            }
        });
    }



    /**
     * This method calculates the BAC Level with the given formula
     * % BAC = A * 5.14 / Weight * r
     * @param gender the gender selected
     * @param weight the weight entered
     * @return double value of the % BAC
     */
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

    /**
     * This method checks the BAC Level to determine the status message
     * It also sets the boolean notSafe to true if the BAC level is higher than .25
     * @param bac double value used to determine the status
     */
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
}