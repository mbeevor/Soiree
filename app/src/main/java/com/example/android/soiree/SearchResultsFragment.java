package com.example.android.soiree;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.soiree.Adapters.ResultsListAdapter;
import com.example.android.soiree.AsyncTasks.AsyncTaskListener;
import com.example.android.soiree.AsyncTasks.GetRecipeData;
import com.example.android.soiree.Utils.NetworkUtils;
import com.example.android.soiree.data.DBHandler;
import com.example.android.soiree.model.Keys;
import com.example.android.soiree.model.Recipe;

import java.net.URL;
import java.util.ArrayList;

import static com.example.android.soiree.data.DinnerContract.RecipeEntry.METHOD_URL;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_ID;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_IMAGE;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_NAME;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_RANK;

public class SearchResultsFragment extends Fragment {

    private String searchQuery;
    private Recipe recipe;
    private RecyclerView resultsRecyclerView;
    private ResultsListAdapter resultsListAdapter;
    private DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        savedInstanceState = getArguments();
        if (savedInstanceState != null ) {
            searchQuery = savedInstanceState.getString(Keys.QUERY);
        } else {
            searchQuery = "";
        }

        final View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        resultsRecyclerView = rootView.findViewById(R.id.search_results_recyclerview);

        // create GridLayoutManager for results
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), setGridColumns());
        resultsRecyclerView.setLayoutManager(gridLayoutManager);
        resultsRecyclerView.setHasFixedSize(true);

        // initialise database
        dbHandler = new DBHandler(getContext());
        SQLiteDatabase database = dbHandler.getWritableDatabase();

        // create new adapter and assign on click listener to show alert dialog to add to dinner party
        resultsListAdapter = new ResultsListAdapter(getContext(), new ResultsListAdapter.OnItemClickHandler() {
            @Override
            public void onItemClick(View item, int position) {

                // alert dialog when recipe selected
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.add_recipe_to_dinner_party);

                builder.setPositiveButton(R.string.confirm_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // add recipe to dinner party
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(RECIPE_ID, recipe.getRecipeId());
                        contentValues.put(RECIPE_IMAGE, recipe.getRecipeImage());
                        contentValues.put(METHOD_URL, recipe.getMethodUrl());
                        contentValues.put(RECIPE_NAME, recipe.getRecipeTitle());
                        contentValues.put(RECIPE_RANK, recipe.getRecipeRank());

                        Uri newUri = getContentResolver().
                        Toast.makeText(getContext(), R.string.recipe_added_to_dinner_party, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicks cancel
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        resultsRecyclerView.setAdapter(resultsListAdapter);
        loadResults(searchQuery);

        return rootView;
    }


    // query API using search term entered
    private void loadResults(String url) {

        if (url != null) {
            URL searchUrl = NetworkUtils.queryUrl(url);
            new GetRecipeData(new GetRecipeDataListener()).execute(searchUrl);
        }

    }

    // method to calculate size of Grid based on device configuration
    public int setGridColumns() {

        int gridColumns = 0;

        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                gridColumns = 1;
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                gridColumns = 2;
                break;
        }
        return gridColumns;
    }

    // initiate AsyncTask to return list of recipes and attach to adapter
    public class GetRecipeDataListener implements AsyncTaskListener {

        @Override
        public void onTaskComplete(ArrayList<Recipe> list) {

            resultsRecyclerView.setAdapter(resultsListAdapter);
            resultsListAdapter.updateData(list);

        }
    }

}
