package com.example.android.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ingredients implements Parcelable {

    private ArrayList<String> ingredientItem;

    public Ingredients(ArrayList<String> item) {
        ingredientItem = item;
    }

    protected Ingredients(Parcel in) {
        ingredientItem= (ArrayList<String>) in.readSerializable();
    }

    public ArrayList<String> getIngredientItem() {
        return ingredientItem;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(ingredientItem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}
