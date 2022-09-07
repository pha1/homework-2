package com.example.group9_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewDrinksActivity extends AppCompatActivity {

    public static int numDrinks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);
        setTitle("View Drinks");
    }
}