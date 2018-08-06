package com.example.android.soiree.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.soiree.R;

import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;

public class Dinner implements Parcelable {

    private String dinnerName;
    private String starterId;
    private String starterName;
    private String starterUri;
    private String mainId;
    private String mainName;
    private String mainUri;
    private String puddingId;
    private String puddingName;
    private String puddingUri;
    private String guestList;
    private String recipeNotes;

    public Dinner(String name, String starter, String sName, String sUri, String main, String mName, String mUri,
                  String pudding, String pName, String pUri, String guests, String notes) {
        dinnerName = name;
        starterId = starter;
        starterName = sName;
        starterUri = sUri;
        mainId = main;
        mainName = mName;
        mainUri = mUri;
        puddingId = pudding;
        puddingName = pName;
        puddingUri = pUri;
        guestList = guests;
        recipeNotes = notes;
    }


    protected Dinner(Parcel parcel) {
        dinnerName = parcel.readString();
        starterId = parcel.readString();
        starterName = parcel.readString();
        starterUri = parcel.readString();
        mainId = parcel.readString();
        mainName = parcel.readString();
        mainUri = parcel.readString();
        puddingId = parcel.readString();
        puddingName = parcel.readString();
        puddingUri = parcel.readString();
        guestList = parcel.readString();
        recipeNotes = parcel.readString();
    }

    public String getDinnerName() { return dinnerName; }
    public String getStarterId() {
        return starterId;
    }
    public String getStarterName() {return starterName;}
    public String getStarterUri() { return starterUri;}
    public String getMainId() {
        return mainId;
    }
    public String getMainName() {return mainName;}
    public String getMainUri() { return mainUri;}
    public String getPuddingId() {
        return puddingId;
    }
    public String getPuddingName() { return puddingName;}
    public String getPuddingUri() { return puddingUri; }
    public String getGuestList() { return guestList; }
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
        dest.writeString(starterName);
        dest.writeString(starterUri);
        dest.writeString(mainId);
        dest.writeString(mainName);
        dest.writeString(mainUri);
        dest.writeString(puddingId);
        dest.writeString(puddingName);
        dest.writeString(puddingUri);
        dest.writeString(guestList);
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
