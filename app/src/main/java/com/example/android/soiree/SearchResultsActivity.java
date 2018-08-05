package com.example.android.soiree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.soiree.Adapters.OnItemClickHandler;
import com.example.android.soiree.Adapters.ResultsListAdapter;
import com.example.android.soiree.AsyncTasks.AsyncTaskListener;
import com.example.android.soiree.AsyncTasks.GetRecipeData;
import com.example.android.soiree.Utils.NetworkUtils;
import com.example.android.soiree.data.DBHandler;
import com.example.android.soiree.model.Dinner;
import com.example.android.soiree.model.Keys;
import com.example.android.soiree.model.Recipe;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.COURSE_MAIN;
import static com.example.android.soiree.model.Keys.COURSE_PUDDING;
import static com.example.android.soiree.model.Keys.COURSE_STARTER;
import static com.example.android.soiree.model.Keys.COURSE_UNKNOWN;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;
import static com.example.android.soiree.model.Keys.DINNER;

public class SearchResultsActivity extends AppCompatActivity {

    final Context context = this;
    private String searchQuery;
    private ArrayList<Recipe> recipesList;
    private Recipe recipe;
    @BindView(R.id.search_results_recyclerview)
    RecyclerView resultsRecyclerView;
    private ResultsListAdapter resultsListAdapter;
    private int currentCourse;
    private Uri currentDinnerUri;
    private Dinner dinner;
    private String dinnerName;
    private String starterId;
    private String mainId;
    private String puddingId;
    private String guestList;
    private String recipeNotes;
    private String courseName;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);
        Intent searchResultsIntent = getIntent();
        courseName = searchResultsIntent.getStringExtra(COURSE);
        searchQuery = searchResultsIntent.getStringExtra(Keys.QUERY);
        dinner = searchResultsIntent.getParcelableExtra(DINNER);
        currentDinnerUri = searchResultsIntent.getData();

        if (searchQuery == null) {
            searchQuery = "";
        }

        if (courseName != null) {
            if (courseName.equals(getString(R.string.starter))) {
                currentCourse = COURSE_STARTER;
            } else if (courseName.equals(getString(R.string.main))) {
                currentCourse = COURSE_MAIN;
            } else if (courseName.equals(getString(R.string.pudding))) {
                currentCourse = COURSE_PUDDING;
            } else {
                currentCourse = COURSE_UNKNOWN;
            }
        }

        if (dinner != null) {
            dinnerName = dinner.getDinnerName();
            starterId = dinner.getStarterId();
            mainId = dinner.getMainId();
            puddingId = dinner.getPuddingId();
            guestList = dinner.getGuestList();
            recipeNotes = dinner.getRecipeNotes();
        } else {
            dinnerName = DEFAULT_VALUE;
            starterId = DEFAULT_VALUE;
            mainId = DEFAULT_VALUE;
            puddingId = DEFAULT_VALUE;
            guestList = DEFAULT_VALUE;
            recipeNotes = DEFAULT_VALUE;
        }

        // create GridLayoutManager for results
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        resultsRecyclerView.setLayoutManager(gridLayoutManager);
        resultsRecyclerView.setHasFixedSize(true);

        // initialise database
        dbHandler = new DBHandler(this);

        // create new adapter and assign on click listener to show alert dialog to add to dinner party
        resultsListAdapter = new ResultsListAdapter(context, new OnItemClickHandler() {
            @Override
            public void onItemClick(View item, int position) {

                recipe = recipesList.get(position);

                // alert dialog when recipe selected
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.add_recipe_to_dinner_party);

                builder.setPositiveButton(R.string.confirm_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent backToCourseIntent = new Intent(context, CourseActivity.class);

                        switch (currentCourse) {
                            case COURSE_STARTER:
                                starterId = recipe.getRecipeId();
                                break;

                            case COURSE_MAIN:
                                mainId = recipe.getRecipeId();
                                break;

                            case COURSE_PUDDING:
                                puddingId = recipe.getRecipeId();
                                break;

                            default:

                        }

                        // return to course details after adding recipe
                        dinner = new Dinner(dinnerName, starterId, mainId, puddingId, guestList, recipeNotes);
                        backToCourseIntent.putExtra(COURSE, courseName);
                        backToCourseIntent.putExtra(DINNER, dinner);
                        backToCourseIntent.setData(currentDinnerUri);
                        startActivity(backToCourseIntent);
                        Toast.makeText(getApplicationContext(), R.string.recipe_added_to_dinner_party, Toast.LENGTH_SHORT).show();
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

            recipesList = list;

            if (recipesList != null) {
                resultsRecyclerView.setAdapter(resultsListAdapter);
                resultsListAdapter.updateData(recipesList);

            }
        }
    }

}
