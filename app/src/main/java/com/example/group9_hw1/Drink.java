/**
 * Homework 2
 * Group9_HW2
 * Phi Ha
 * Srinath Dittakavi
 */
package com.example.group9_hw1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Drink implements Parcelable {

    double alcohol_percentage;
    int size;
    String date;

    /**
     * Default Constructor
     */
    public Drink(){}

    /**
     * Constructor given alcohol percentage and size
     * @param alcohol_percentage alcohol percentage of the drink as a double
     * @param size size of the drink as an int
     */
    public Drink(double alcohol_percentage, int size, String date){
        this.alcohol_percentage = alcohol_percentage;
        this.size = size;
        this.date = date;
    }

    protected Drink(Parcel in) {
        alcohol_percentage = in.readDouble();
        size = in.readInt();
        date = in.readString();
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.alcohol_percentage);
        parcel.writeInt(this.size);
        parcel.writeString(this.date);
    }
}
