package com.example.android.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    private String recipeTitle;
    private String methodUrl;
    private String recipeId;
    private String recipeImage;
    private String recipeRank;

    public Recipe(String title, String url, String id, String image, String rank) {
        recipeTitle = title;
        methodUrl = url;
        recipeId = id;
        recipeImage = image;
        recipeRank = rank;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getMethodUrl() {
        return methodUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipeRank() {
        return recipeRank;
    }

    public void setRecipeTitle(String title) {
        recipeTitle = title;
    }

    public void setMethodUrl(String url) {
        methodUrl = url;
    }

    public void setRecipeId(String id) {
        recipeId = id;
    }

    public void setRecipeImage(String image) {
        recipeImage = image;
    }

    public void setRecipeRank(String rank) {
        recipeRank = rank;
    }

    protected Recipe(Parcel in) {

        recipeTitle = in.readString();
        methodUrl = in.readString();
        recipeId = in.readString();
        recipeImage = in.readString();
        recipeRank = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[0];
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
        dest.writeString(recipeId);
        dest.writeString(recipeImage);
        dest.writeString(recipeRank);

    }
}
