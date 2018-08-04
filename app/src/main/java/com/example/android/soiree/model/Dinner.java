package com.example.android.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dinner implements Parcelable {

    private String dinnerName;
    private String starterId;
    private String mainId;
    private String puddingId;
    private String recipeNotes;

    public Dinner(String name, String starter, String main, String pudding, String notes) {
        dinnerName = name;
        starterId = starter;
        mainId = main;
        puddingId = pudding;
        recipeNotes = notes;
    }


    protected Dinner(Parcel parcel) {
        dinnerName = parcel.readString();
        starterId = parcel.readString();
        mainId = parcel.readString();
        puddingId = parcel.readString();
        recipeNotes = parcel.readString();
    }

    public String getDinnerName() { return dinnerName; }
    public String getStarterId() {
        return starterId;
    }
    public String getMainId() {
        return mainId;
    }
    public String getPuddingId() {
        return puddingId;
    }
    public String getRecipeNotes() {
        return recipeNotes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dinnerName);
        dest.writeString(starterId);
        dest.writeString(mainId);
        dest.writeString(puddingId);
        dest.writeString(recipeNotes);
    }

    public static final Parcelable.Creator<Dinner> CREATOR = new Parcelable.Creator<Dinner>() {
        @Override
        public Dinner createFromParcel(Parcel parcel) {
            return new Dinner(parcel);
        }

        @Override
        public Dinner[] newArray(int size) {
            return new Dinner[size];
        }
    };
}
