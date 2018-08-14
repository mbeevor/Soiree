package uk.co.beevorwhite.soiree.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeDetail implements Parcelable {

    private ArrayList<Ingredient> ingredientsList;
    private String recipeUrl;

    public RecipeDetail(ArrayList<Ingredient> item, String url) {
        ingredientsList = item;
        recipeUrl = url;
    }

    protected RecipeDetail(Parcel in) {
        in.readList(ingredientsList, Ingredient.class.getClassLoader());
        recipeUrl = in.readString();
    }

    public ArrayList<Ingredient> getIngredientsList() {
        return ingredientsList;
    }
    public String getRecipeUrl() {return recipeUrl;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(ingredientsList);
        dest.writeString(recipeUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeDetail> CREATOR = new Creator<RecipeDetail>() {
        @Override
        public RecipeDetail createFromParcel(Parcel in) {
            return new RecipeDetail(in);
        }

        @Override
        public RecipeDetail[] newArray(int size) {
            return new RecipeDetail[size];
        }
    };
}
