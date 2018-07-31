package com.example.android.soiree;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.example.android.soiree.model.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    public String searchQuery;
    @BindView(R.id.search_bar) SearchView searchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_search);
        ButterKnife.bind(this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);

            Fragment searchResultsFragment = new SearchResultsFragment();
            Bundle queryForSearchResults = new Bundle();
            queryForSearchResults.putString(Keys.QUERY, searchQuery);
            searchResultsFragment.setArguments(queryForSearchResults);

            fragmentManager.beginTransaction().replace(R.id.search_results_container, searchResultsFragment).commit();

        }

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

    }

}
