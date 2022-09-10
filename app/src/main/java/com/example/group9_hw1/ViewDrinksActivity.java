package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {

    public static int numDrinks = 0;
    final public static String VIEW_DRINKS_KEY = "VIEW_DRINKS";
    public static ArrayList<Drink> drinks = new ArrayList<Drink>();
    public int current = 0;
    public Drink drink = new Drink();

    TextView currentDrink;
    TextView totalDrinks;
    TextView drinkSize;
    TextView alcoholPercentage;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);
        setTitle("View Drinks");

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.DRINKS_KEY)){
            drinks = getIntent().getParcelableArrayListExtra(MainActivity.DRINKS_KEY);
            drink = drinks.get(current);
            updateUI();
        }

            // Click next to get the next drink in the ArrayList
        // If the current drink is the last drink then show the first drink next
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinks.get(current).equals(drinks.get(drinks.size()-1))){
                    current = 0;
                    drink = drinks.get(0);
                    updateUI();
                }
                else {
                    current++;
                    drink = drinks.get(current);
                    updateUI();
                }
            }
        });

        // Click the trash icon to delete the current drink and then show the previous drink
        findViewById(R.id.trashButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinks.remove(current);

                if (drinks.size() > 1){
                    if (current == 0)
                    {
                        current = drinks.size() - 1;
                    }
                    else {
                        current--;
                    }
                    drink = drinks.get(current);
                    updateUI();
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
                if(drinks.get(current).equals(drinks.get(0))){
                    current = drinks.size() - 1;
                    drink = drinks.get(drinks.size()-1);
                    updateUI();
                }
                else{
                    current--;
                    drink = drinks.get(current);
                    updateUI();
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

    public void updateUI(){

        currentDrink = findViewById(R.id.textView2);
        currentDrink.setText(String.valueOf(current + 1));

        totalDrinks = findViewById(R.id.textView4);
        totalDrinks.setText(String.valueOf(drinks.size()));

        drinkSize = findViewById(R.id.textView6);
        drinkSize.setText(String.valueOf(drink.size));

        alcoholPercentage = findViewById(R.id.textView7);
        alcoholPercentage.setText(String.valueOf(drink.alcohol_percentage));

        date = findViewById(R.id.textView10);
        date.setText(String.valueOf(drink.date));

    }
}