package uk.co.beevorwhite.soiree.Utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.beevorwhite.soiree.model.Ingredient;
import uk.co.beevorwhite.soiree.model.Keys;
import uk.co.beevorwhite.soiree.model.Recipe;

import static uk.co.beevorwhite.soiree.model.Keys.METHOD_URL;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_IMAGE;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_NAME;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_PORTIONS;

public class QueryUtils {

    public static ArrayList<Recipe> getRecipeListFromJson(String recipesJson) {

        if (TextUtils.isEmpty(recipesJson)) {
            return null;
        }

        ArrayList<Recipe> recipesList = new ArrayList<>();

        try {

            JSONObject resultsObject = new JSONObject(recipesJson);

            JSONArray hitsArray = resultsObject.getJSONArray(Keys.HITS);

            for (int i = 0; i < hitsArray.length(); i++) {
                JSONObject recipeList = hitsArray.getJSONObject(i);

                JSONObject currentRecipe = recipeList.getJSONObject(RECIPE);

                String recipeTitle = currentRecipe.getString(RECIPE_NAME);
                String methodUrl = currentRecipe.getString(METHOD_URL);
                String recipeImage = currentRecipe.getString(RECIPE_IMAGE);
                String recipePortions = currentRecipe.getString(RECIPE_PORTIONS);

                Recipe recipe = new Recipe(recipeTitle, methodUrl, recipeImage, recipePortions);
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
