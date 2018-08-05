package com.example.android.soiree;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.soiree.model.Dinner;
import com.example.android.soiree.model.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.DINNER;

public class SearchActivity extends AppCompatActivity {

    public String searchQuery;
    private Dinner dinner;
    @BindView(R.id.search_bar)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_progress_bar)
    ProgressBar searchProgressBar;
    private Uri currentDinnerUri;
    private String courseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_search);
        ButterKnife.bind(this);
        Intent getIntent = getIntent();
        dinner = getIntent.getParcelableExtra(DINNER);
        courseName = getIntent.getStringExtra(COURSE);
        currentDinnerUri = getIntent.getData();

        if (courseName != null) {
            setTitle(courseName);
        }

        // assign toolbar to activity, and enable back button in action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);

    }

    // use this method to ensure 'singletop' is replicated when using search
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void doMySearch(String query) {

        if (courseName != null) {
            Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
            searchResultsIntent.putExtra(Keys.QUERY, query);
            searchResultsIntent.putExtra(COURSE, courseName);
            searchResultsIntent.putExtra(DINNER, dinner);
            searchResultsIntent.setData(currentDinnerUri);
            searchProgressBar.setVisibility(View.GONE);
            startActivity(searchResultsIntent);

        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(searchQuery);

        }
    }

}
