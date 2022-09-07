package com.example.group9_hw1;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    String gender;
    int weight;

    public Profile(){
    }

    public Profile(String gender, int weight){
        this.gender = gender;
        this.weight = weight;
    }

    protected Profile(Parcel in) {
        gender = in.readString();
        weight = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.gender);
        parcel.writeInt(this.weight);
    }
}
