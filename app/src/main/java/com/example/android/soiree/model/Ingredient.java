package com.example.android.soiree.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private String ingredientItem;

    public Ingredient(String item) {
        ingredientItem = item;
    }

    protected Ingredient(Parcel in) {
        ingredientItem = in.readString();
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

    public String getIngredientItem() {return ingredientItem;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientItem);
    }
}
