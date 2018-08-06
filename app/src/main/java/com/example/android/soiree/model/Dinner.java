package com.example.android.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dinner implements Parcelable {

    private String dinnerName;
    private String starterId;
    private String starterName;
    private String starterUri;
    private String starterImage;
    private String starterNotes;

    private String mainId;
    private String mainName;
    private String mainUri;
    private String mainImage;
    private String mainNotes;

    private String puddingId;
    private String puddingName;
    private String puddingUri;
    private String puddingImage;
    private String puddingNotes;

    private String guestList;

    public Dinner(String name,
                  String starter, String sName, String sUri, String sImage, String sNotes,
                  String main, String mName, String mUri, String mImage, String mNotes,
                  String pudding, String pName, String pUri, String pImage, String pNotes,
                  String guests) {
        dinnerName = name;
        starterId = starter;
        starterName = sName;
        starterUri = sUri;
        starterImage = sImage;
        starterNotes = sNotes;
        mainId = main;
        mainName = mName;
        mainUri = mUri;
        mainImage = mImage;
        mainNotes = mNotes;
        puddingId = pudding;
        puddingName = pName;
        puddingUri = pUri;
        puddingImage = pImage;
        puddingNotes = pNotes;
        guestList = guests;
    }


    protected Dinner(Parcel parcel) {
        dinnerName = parcel.readString();
        starterId = parcel.readString();
        starterName = parcel.readString();
        starterUri = parcel.readString();
        starterImage = parcel.readString();
        starterNotes = parcel.readString();
        mainId = parcel.readString();
        mainName = parcel.readString();
        mainUri = parcel.readString();
        mainImage = parcel.readString();
        mainNotes = parcel.readString();
        puddingId = parcel.readString();
        puddingName = parcel.readString();
        puddingUri = parcel.readString();
        puddingImage = parcel.readString();
        puddingNotes = parcel.readString();
        guestList = parcel.readString();
    }

    public String getDinnerName() { return dinnerName; }
    public String getStarterId() {
        return starterId;
    }
    public String getStarterName() {return starterName;}
    public String getStarterUri() { return starterUri;}
    public String getStarterImage() { return starterImage;}
    public String getStarterNotes() { return starterNotes;}
    public String getMainId() {
        return mainId;
    }
    public String getMainName() {return mainName;}
    public String getMainUri() { return mainUri;}
    public String getMainImage() { return mainImage;}
    public String getMainNotes() { return mainNotes;}
    public String getPuddingId() {
        return puddingId;
    }
    public String getPuddingName() { return puddingName;}
    public String getPuddingUri() { return puddingUri; }
    public String getPuddingImage() { return puddingImage;}
    public String getPuddingNotes() { return puddingNotes;}
    public String getGuestList() { return guestList; }

    public String setStarterNotes(String notes) {
        starterNotes = notes;
        return starterNotes;
    }

    public String setMainNotes(String notes) {
        mainNotes = notes;
        return mainNotes;
    }

    public String setPuddingNotes(String notes) {
        puddingNotes = notes;
        return puddingNotes;
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
        dest.writeString(starterImage);
        dest.writeString(starterNotes);
        dest.writeString(mainId);
        dest.writeString(mainName);
        dest.writeString(mainUri);
        dest.writeString(mainImage);
        dest.writeString(mainNotes);
        dest.writeString(puddingId);
        dest.writeString(puddingName);
        dest.writeString(puddingUri);
        dest.writeString(puddingImage);
        dest.writeString(puddingNotes);
        dest.writeString(guestList);
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
