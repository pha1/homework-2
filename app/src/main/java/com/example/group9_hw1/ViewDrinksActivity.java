package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {

    public static int numDrinks = 0;
    final public static String VIEW_DRINKS_KEY = "VIEW_DRINKS";
    public ArrayList<Drink> drinks;
    public int current = 0;
    public Drink drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);
        setTitle("View Drinks");

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.DRINKS_KEY)){
            drinks = getIntent().getParcelableExtra(MainActivity.DRINKS_KEY);
            drink = drinks.get(current);
        }



        // Click next to get the next drink in the ArrayList
        // If the current drink is the last drink then show the first drink next
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinks.get(current) == drinks.get(drinks.size()-1)){
                    drink = drinks.get(0);
                }
                else {
                    drink = drinks.get(current++);
                }
            }
        });

        // Click the trash icon to delete the current drink and then show the previous drink
        findViewById(R.id.trashButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinks != null){
                    drinks.remove(current);
                    drink = drinks.get(current--);
                }
                else {
                    Intent returnDrinks = new Intent(ViewDrinksActivity.this, MainActivity.class);
                    returnDrinks.putExtra(VIEW_DRINKS_KEY, drinks);
                    setResult(RESULT_OK, returnDrinks);
                    finish();
                }

            }
        });

        // Click the previous button to get the previous drink in the ArrayList
        // If the current drink is the first drink, show the last drink
        findViewById(R.id.previousButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinks != null){
                    if(drinks.get(current) == drinks.get(0)){
                        drink = drinks.get(drinks.size()-1);
                    }
                }
                else{
                    drink = drinks.get(current--);
                }
            }
        });

        // The close button finishes the activity without returning any extras
        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to send the (updated) drinks ArrayList back to the Main Activity
                Intent returnDrinks = new Intent(ViewDrinksActivity.this, MainActivity.class);
                returnDrinks.putExtra(VIEW_DRINKS_KEY, drinks);
                setResult(RESULT_OK, returnDrinks);
                finish();
            }
        });
    }
}