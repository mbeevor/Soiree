package uk.co.beevorwhite.soiree;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.beevorwhite.soiree.Adapters.OnItemClickHandler;
import uk.co.beevorwhite.soiree.Adapters.ResultsListAdapter;
import uk.co.beevorwhite.soiree.AsyncTasks.AsyncTaskListener;
import uk.co.beevorwhite.soiree.AsyncTasks.GetRecipeData;
import uk.co.beevorwhite.soiree.Utils.NetworkUtils;
import uk.co.beevorwhite.soiree.data.DBHandler;
import uk.co.beevorwhite.soiree.model.Dinner;
import uk.co.beevorwhite.soiree.model.Keys;
import uk.co.beevorwhite.soiree.model.Recipe;

import static uk.co.beevorwhite.soiree.model.Keys.COURSE;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_STARTER;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_UNKNOWN;
import static uk.co.beevorwhite.soiree.model.Keys.DEFAULT_VALUE;
import static uk.co.beevorwhite.soiree.model.Keys.DINNER;
import static uk.co.beevorwhite.soiree.model.Keys.MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.STARTER;

public class SearchResultsActivity extends AppCompatActivity {

    final Context context = this;
    private Dinner dinner;
    private String dinnerName;
    private String starterId;
    private String starterName;
    private String starterUri;
    private String starterImage;
    private String starterNotes;

    private String mainId;
    private String mainName;
    private String mainUri;
    private String mainImage;
    private String mainNotes;

    private String puddingId;
    private String puddingName;
    private String puddingUri;
    private String puddingImage;
    private String puddingNotes;

    private String guestList;
    private String courseName;
    private String searchQuery;
    private ArrayList<Recipe> recipesList;
    private Recipe recipe;
    @BindView(R.id.search_progress_bar)
    ProgressBar searchProgressBar;
    @BindView(R.id.search_results_recyclerview)
    RecyclerView resultsRecyclerView;
    private ResultsListAdapter resultsListAdapter;
    private int currentCourse;
    private Uri currentDinnerUri;
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
            if (courseName.equals(STARTER)) {
                currentCourse = COURSE_STARTER;
            } else if (courseName.equals(MAIN)) {
                currentCourse = COURSE_MAIN;
            } else if (courseName.equals(PUDDING)) {
                currentCourse = COURSE_PUDDING;
            } else {
                currentCourse = COURSE_UNKNOWN;
            }
        }

        if (dinner != null) {
            dinnerName = dinner.getDinnerName();
            starterId = dinner.getStarterId();
            starterName = dinner.getStarterName();
            starterUri = dinner.getStarterUri();
            starterImage = dinner.getStarterImage();
            starterNotes = dinner.getStarterNotes();

            mainId = dinner.getMainId();
            mainName = dinner.getMainName();
            mainUri = dinner.getMainUri();
            mainImage = dinner.getMainImage();
            mainNotes = dinner.getMainNotes();

            puddingId = dinner.getPuddingId();
            puddingName = dinner.getPuddingName();
            puddingUri = dinner.getPuddingUri();
            puddingImage = dinner.getPuddingImage();
            puddingNotes = dinner.getPuddingNotes();

            guestList = dinner.getGuestList();
        } else {
            starterId = DEFAULT_VALUE;
            starterName = STARTER;
            starterUri = DEFAULT_VALUE;
            starterImage = DEFAULT_VALUE;
            starterNotes = DEFAULT_VALUE;
            mainId = DEFAULT_VALUE;
            mainName = MAIN;
            mainUri = DEFAULT_VALUE;
            mainImage = DEFAULT_VALUE;
            mainNotes = DEFAULT_VALUE;
            puddingId = DEFAULT_VALUE;
            puddingName = PUDDING;
            puddingUri = DEFAULT_VALUE;
            puddingImage = DEFAULT_VALUE;
            puddingNotes = DEFAULT_VALUE;

            guestList = DEFAULT_VALUE;
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
                                starterName = recipe.getRecipeTitle();
                                starterUri = recipe.getMethodUrl();
                                starterImage = recipe.getRecipeImage();
                                break;

                            case COURSE_MAIN:
                                mainName = recipe.getRecipeTitle();
                                mainUri = recipe.getMethodUrl();
                                mainImage = recipe.getRecipeImage();
                                break;

                            case COURSE_PUDDING:
                                puddingName = recipe.getRecipeTitle();
                                puddingUri = recipe.getMethodUrl();
                                puddingImage = recipe.getRecipeImage();
                                break;

                            default:

                        }

                        // return to course details after adding recipe
                        dinner = new Dinner(dinnerName, starterId, starterName, starterUri, starterImage, starterNotes, mainId, mainName,
                                mainUri, mainImage, mainNotes, puddingId, puddingName, puddingUri, puddingImage, puddingNotes, guestList);
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

        searchProgressBar.setVisibility(View.VISIBLE);
        resultsRecyclerView.setAdapter(resultsListAdapter);
        loadResults(searchQuery);

    }


    // query API using search term entered
    private void loadResults(String url) {

        if (url != null) {
            URL searchUrl = NetworkUtils.queryUrl(url);
            new GetRecipeData(new GetRecipeDataListener()).execute(searchUrl);
            searchProgressBar.setVisibility(View.GONE);
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

            } else {
                Toast.makeText(getApplicationContext(), "There is is nothing to show!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
