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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static double bac = 0;
    public static ArrayList<Drink> drinks = new ArrayList<>();
    final public static String DRINKS_KEY = "DRINKS_KEY";

    TextView weightDisplay;
    TextView numDrinkDisplay;
    TextView bacLevel;
    TextView status;

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

                    // Clear Drinks List, BAC and UI
                    clearUI();
                    // Enable View Drinks and Add Drink button
                    findViewById(R.id.viewDrinksButton).setEnabled(true);
                    findViewById(R.id.addDrinkButton).setEnabled(true);
                }
            }
        }
    });

    // Receives the results from View Drinks activity
    ActivityResultLauncher<Intent> startForDrinks = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getParcelableArrayListExtra((ViewDrinksActivity.VIEW_DRINKS_KEY)) != null){

                    // Update the ArrayList
                    drinks = result.getData().getParcelableArrayListExtra(ViewDrinksActivity.VIEW_DRINKS_KEY);

                    // Update BAC value
                    bac = calculateBAC(profile, drinks);

                    // Display new values and status message
                    // If BAC < 0.25 enable "Add Drink", else disable "Add Drink"
                    updateBacUI(bac);
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

                    // Update BAC value
                    bac = calculateBAC(profile, drinks);

                    // Update UI as needed
                    // If BAC >= 0.25, disable "Add Drink" and display a Toast Message "No more drinks for you."
                    updateBacUI(bac);
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightDisplay = findViewById(R.id.weightDisplay);
        numDrinkDisplay = findViewById(R.id.numDrinkDisplay);

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

                if (drinks.size() > 0) {
                    // If there are drinks, send the ArrayList to ViewDrinksActivity
                    Intent viewDrinksIntent = new Intent(MainActivity.this, ViewDrinksActivity.class);

                    // Need to add the extras (ArrayList) to the Intent
                    viewDrinksIntent.putExtra(DRINKS_KEY, drinks);
                    startForDrinks.launch(viewDrinksIntent);
                }
                // If no drinks in the ArrayList - Toast Message "You've had no drinks."
                else {
                    Toast.makeText(MainActivity.this, "You have no drinks", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

                // Clear the User Profile
                profile = new Profile();

                // Set components' values back to default
                weightDisplay.setText(getResources().getText(R.string.weight_display));
                numDrinkDisplay.setText(getResources().getText(R.string.num_drinks));

                // Disable buttons
                findViewById(R.id.viewDrinksButton).setEnabled(false);
                findViewById(R.id.addDrinkButton).setEnabled(false);

                clearUI();
            }
        });
    }

    /**
     * This method clears the UI when setting the profile
     */
    public void clearUI(){
        // Clear Drinks List
        drinks.clear();

        // Set status message to default via strings.xml
        status = findViewById(R.id.status);
        status.setText(getResources().getText(R.string.status));
        status.setBackgroundColor(getResources().getColor(R.color.green));

        // Set BAC Level to 0.000 via strings.xml
        bacLevel = findViewById(R.id.bacLevel);
        bacLevel.setText(getResources().getText(R.string.BAC_num));
        bac = 0;
    }

    /**
     * This method calculates the BAC Level with the given formula
     * % BAC = A * 5.14 / Weight * r
     * @param profile the profile of the user
     * @param drinks the list of drinks
     * @return double value of the % BAC
     */
    public double calculateBAC(Profile profile, ArrayList<Drink> drinks){
        double bac;
        double r;
        double a = 0;

        // Set the r value for female
        if (profile.gender.equals("Female")){
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
        bac = (a * 5.14) / (profile.weight * r);

        return bac;
    }

    /**
     * This method checks the BAC Level to determine the status message and updates the UI
     * @param bac double value used to determine the status
     */
    public void updateBacUI(double bac){

        TextView numDrinks = findViewById(R.id.numDrinkDisplay);
        TextView status = findViewById(R.id.status);

        // NUMBER OF DRINKS AND BAC LEVEL
        numDrinks.setText(String.valueOf(drinks.size()));
        bacLevel.setText(String.valueOf(String.format("%.3f", bac)));

        // STATUS MESSAGE

        // If the bac drops to 0.08 or lower, status is set to green and text: "You're safe."
        if (0 <= bac && bac <= 0.08) {
            status.setText(getResources().getText(R.string.status));
            status.setBackgroundColor(getResources().getColor(R.color.green));
            findViewById(R.id.addDrinkButton).setEnabled(true);
        }
        // Sets the status to "Be careful." and changes the color to orange
        else if (0.08 < bac && bac <= 0.2){
            status.setText(getResources().getText(R.string.status2));
            status.setBackgroundColor(getResources().getColor(R.color.orange));
            findViewById(R.id.addDrinkButton).setEnabled(true);
        }

        // Sets the status to "Over the limit!" and changes the color to red
        else {
            status.setText(getResources().getText(R.string.status3));
            status.setBackgroundColor(getResources().getColor(R.color.red));

            // Once the BAC reaches over 0.25, display Toast Message "No more drinks for you
            // This will disable the "Add Drink" button
            if (bac >= 0.25) {
                Toast.makeText(this, "No more drinks for you.", Toast.LENGTH_LONG).show();
                findViewById(R.id.addDrinkButton).setEnabled(false);
            }
        }
    }
}