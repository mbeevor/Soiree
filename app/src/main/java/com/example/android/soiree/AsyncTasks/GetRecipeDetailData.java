package com.example.android.soiree.AsyncTasks;

import android.os.AsyncTask;

import com.example.android.soiree.Utils.NetworkUtils;
import com.example.android.soiree.Utils.QueryUtils;
import com.example.android.soiree.model.RecipeDetail;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetRecipeDetailData extends AsyncTask<URL, Void, ArrayList<RecipeDetail>> {

    private ArrayList<RecipeDetail> recipeDetailList;
    private IngredientsAsyncTaskListener delegate = null;

    public GetRecipeDetailData(IngredientsAsyncTaskListener asyncTaskListener) {
        delegate = asyncTaskListener;
    }

    @Override
    protected ArrayList<RecipeDetail> doInBackground(URL... urls) {

        try {

            if (urls != null) {

                URL url = urls[0];
                String query = NetworkUtils.getResponseFromHttpUrl(url);
                if (query != null) {

                    recipeDetailList = QueryUtils.getRecipeDetailFromJson(query);
                    return recipeDetailList;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeDetailList;
    }

    @Override
    protected void onPostExecute(ArrayList<RecipeDetail> recipeDetail) {
        recipeDetailList = recipeDetail;

        if (recipeDetailList != null) {
            delegate.onTaskComplete(recipeDetailList);
        }
    }
}