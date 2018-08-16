package uk.co.beevorwhite.soiree.Utils;

import android.text.TextUtils;

import uk.co.beevorwhite.soiree.model.Ingredient;
import uk.co.beevorwhite.soiree.model.Keys;
import uk.co.beevorwhite.soiree.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static uk.co.beevorwhite.soiree.model.Keys.METHOD_URL;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_ID;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_IMAGE;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_NAME;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_RANK;

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

                Recipe recipe = new Recipe(recipeTitle, methodUrl, recipeId, recipeImage, recipeRank);
                recipesList.add(recipe);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipesList;
    }

    public static ArrayList<Ingredient> getRecipeDetailFromJson(String resultJson) {

        if (TextUtils.isEmpty(resultJson)) {
            return null;
        }

        ArrayList<Ingredient> recipeDetailList = null;

        try {

            JSONObject resultsObject = new JSONObject(resultJson);

            JSONObject recipeObject = resultsObject.getJSONObject(Keys.RECIPE);

            JSONArray ingredientsArray = recipeObject.getJSONArray(Keys.RECIPE_INGREDIENTS);

           recipeDetailList = new ArrayList<>(ingredientsArray.length());

            for (int i = 0; i < ingredientsArray.length(); i++) {

                String ingredientItem = ingredientsArray.getString(i);

                Ingredient ingredient = new Ingredient(ingredientItem);
                recipeDetailList.add(ingredient);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeDetailList;
    }

}