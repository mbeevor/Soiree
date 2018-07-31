package com.example.android.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DinnerParty implements Parcelable {

    private String dinnerPartyDate;
    private Recipe starter;
    private Recipe mainCourse;
    private Recipe pudding;

    public DinnerParty(String date, Recipe thisStarter, Recipe thisMainCourse, Recipe thisPudding) {
        dinnerPartyDate = date;
        starter = thisStarter;
        mainCourse = thisMainCourse;
        pudding = thisPudding;
    }


    protected DinnerParty(Parcel parcel) {
        dinnerPartyDate = parcel.readString();

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dinnerPartyDate);
    }

    public static final Parcelable.Creator<DinnerParty> CREATOR = new Parcelable.Creator<DinnerParty>() {
        @Override
        public DinnerParty createFromParcel(Parcel parcel) {
            return new DinnerParty(parcel);
        }

        @Override
        public DinnerParty[] newArray(int size) {
            return new DinnerParty[0];
        }
    };
}
