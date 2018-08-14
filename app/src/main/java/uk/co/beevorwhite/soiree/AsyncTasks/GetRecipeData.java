package uk.co.beevorwhite.soiree.AsyncTasks;

import android.os.AsyncTask;

import uk.co.beevorwhite.soiree.Utils.NetworkUtils;
import uk.co.beevorwhite.soiree.Utils.QueryUtils;
import uk.co.beevorwhite.soiree.model.Recipe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetRecipeData extends AsyncTask<URL, Void, ArrayList<Recipe>> {

    private ArrayList<Recipe> recipesList;
    private AsyncTaskListener delegate = null;

    public GetRecipeData(AsyncTaskListener asyncTaskListener) {
        delegate = asyncTaskListener;
    }

    @Override
    protected ArrayList<Recipe> doInBackground(URL... urls) {

        try {

            if (urls != null) {

                URL url = urls[0];
                String query = NetworkUtils.getResponseFromHttpUrl(url);
                if (query != null) {

                    recipesList = QueryUtils.getRecipeListFromJson(query);
                    return recipesList;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipesList;
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        recipesList = recipes;

        if (recipesList != null) {
            delegate.onTaskComplete(recipesList);
        }
    }
}
