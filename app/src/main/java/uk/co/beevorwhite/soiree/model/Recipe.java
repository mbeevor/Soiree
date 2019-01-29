package uk.co.beevorwhite.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    private String recipeTitle;
    private String methodUrl;
    private String recipeImage;
    private String recipePortions;

    public Recipe(String title, String url, String image, String portions) {
        recipeTitle = title;
        methodUrl = url;
        recipeImage = image;
        recipePortions = portions;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getMethodUrl() {
        return methodUrl;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipePortions() {
        return recipePortions;
    }

    public void setRecipeTitle(String title) {
        recipeTitle = title;
    }

    public void setMethodUrl(String url) {
        methodUrl = url;
    }

    public void setRecipeImage(String image) {
        recipeImage = image;
    }

    public void setRecipePortions(String rank) {
        recipePortions = rank;
    }

    protected Recipe(Parcel in) {

        recipeTitle = in.readString();
        methodUrl = in.readString();
        recipeImage = in.readString();
        recipePortions = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(recipeTitle);
        dest.writeString(methodUrl);
        dest.writeString(recipeImage);
        dest.writeString(recipePortions);

    }
}
