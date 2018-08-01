package com.example.android.soiree.Utils;

import android.text.TextUtils;

import com.example.android.soiree.model.Keys;
import com.example.android.soiree.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.soiree.data.RecipeContract.METHOD_URL;
import static com.example.android.soiree.data.RecipeContract.RECIPE_ID;
import static com.example.android.soiree.data.RecipeContract.RECIPE_IMAGE;
import static com.example.android.soiree.data.RecipeContract.RECIPE_NAME;
import static com.example.android.soiree.data.RecipeContract.RECIPE_RANK;

public class QueryUtils {

    public static ArrayList<Recipe> getRecipeListFromJson(String recipesJson) {

        if (TextUtils.isEmpty(recipesJson)) {
            return null;
        }

        ArrayList<Recipe> recipesList = new ArrayList<>();

        try {

            JSONObject recipeObject = new JSONObject(recipesJson);

            JSONArray recipesArray = recipeObject.getJSONArray(Keys.RECIPES);

            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject currentRecipe = recipesArray.getJSONObject(i);

                String recipeTitle = currentRecipe.getString(RECIPE_NAME);
                String methodUrl = currentRecipe.getString(METHOD_URL);
                String recipeId = currentRecipe.getString(RECIPE_ID);
                String recipeImage = currentRecipe.getString(RECIPE_IMAGE);
                String recipeRank = currentRecipe.getString(RECIPE_RANK);

                Recipe recipe = new Recipe(recipeTitle, methodUrl, recipeId, recipeImage,recipeRank);
                recipesList.add(recipe);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipesList;
    }
}
