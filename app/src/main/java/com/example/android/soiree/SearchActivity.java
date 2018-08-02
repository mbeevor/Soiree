package com.example.android.soiree;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.example.android.soiree.model.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.model.Keys.COURSE;

public class SearchActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    public String searchQuery;
    @BindView(R.id.search_bar) SearchView searchView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    String courseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_search);
        ButterKnife.bind(this);
        Intent getCourseName = getIntent();
        if (getCourseName != null) {
            courseName = getCourseName.getStringExtra(COURSE);
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

        Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
        Bundle queryForSearchResults = new Bundle();
        queryForSearchResults.putString(Keys.QUERY, query);
        searchResultsIntent.putExtras(queryForSearchResults);
        startActivity(searchResultsIntent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(searchQuery);

        }
    }

}
