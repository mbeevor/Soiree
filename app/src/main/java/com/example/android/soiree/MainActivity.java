package com.example.android.soiree;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.soiree.Adapters.DinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.dinner_party_list_recyclerview)
    RecyclerView dinnerPartyListRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyTextView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private DinnerAdapter dinnerAdapter;
    public DinnerAdapter.DinnerPartyClickListener dinnerPartyClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // set up adapter for recyclerview
        dinnerAdapter = new DinnerAdapter(getApplicationContext(), new DinnerAdapter.DinnerPartyClickListener() {
            @Override
            public void onDinnerSelected(int position) {
                dinnerPartyClickListener.onDinnerSelected(position);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        dinnerPartyListRecyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        dinnerPartyListRecyclerView.setHasFixedSize(true);

        dinnerPartyListRecyclerView.setAdapter(dinnerAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
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

    // method to hide error message when list of dinner parties is populated
    private void showDinnerParties() {

        progressBar.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.INVISIBLE);
        dinnerPartyListRecyclerView.setVisibility(View.VISIBLE);

    }

    // method to show error message when list of dinner parties is empty
    private void showError() {

        progressBar.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
        dinnerPartyListRecyclerView.setVisibility(View.INVISIBLE);

    }

}
