package com.example.android.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ingredient implements Parcelable {

    private String sourceUrl;
    private ArrayList<Ingredient> ingredientsList;


    protected Ingredient(Parcel in) {
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
