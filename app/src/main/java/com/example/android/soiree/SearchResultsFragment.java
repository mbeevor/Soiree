package com.example.android.soiree;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soiree.Adapters.ResultsListAdapter;
import com.example.android.soiree.AsyncTasks.AsyncTaskListener;
import com.example.android.soiree.AsyncTasks.GetRecipeData;
import com.example.android.soiree.Utils.NetworkUtils;
import com.example.android.soiree.model.Keys;
import com.example.android.soiree.model.Recipe;

import java.net.URL;
import java.util.ArrayList;

public class SearchResultsFragment extends Fragment {

    private String searchQuery;
    private RecyclerView resultsRecyclerView;
    private ResultsListAdapter resultsListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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

        resultsListAdapter = new ResultsListAdapter(getContext());
        resultsRecyclerView.setAdapter(resultsListAdapter);
        loadResults(searchQuery);

        return rootView;
    }


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

    public class GetRecipeDataListener implements AsyncTaskListener {

        @Override
        public void onTaskComplete(ArrayList<Recipe> list) {

            resultsRecyclerView.setAdapter(resultsListAdapter);
            resultsListAdapter.updateData(list);

        }
    }

}
