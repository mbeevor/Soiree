package com.example.android.soiree;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.soiree.Adapters.DinnerCursorAdapter;
import com.example.android.soiree.Adapters.OnItemClickHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.data.DinnerContract.DinnerEntry.CONTENT_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.GUEST_LIST;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.RECIPE_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry._ID;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DINNER_LOADER = 0;

    @BindView(R.id.new_fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.dinner_party_list_recyclerview)
    RecyclerView dinnerPartyListRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyTextView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private DinnerCursorAdapter dinnerCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showError();
        loadSavedDinners();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DinnerActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadSavedDinners() {
        getLoaderManager().initLoader(DINNER_LOADER, null, this);
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

    // loader methods for handling displaying database fields
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                _ID,
                DINNER_NAME,
                STARTER_ID,
                STARTER_NAME,
                STARTER_URI,
                MAIN_ID,
                MAIN_NAME,
                MAIN_URI,
                PUDDING_ID,
                PUDDING_NAME,
                PUDDING_URI,
                GUEST_LIST,
                RECIPE_NOTES,
        };

        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {

        dinnerCursorAdapter = new DinnerCursorAdapter(cursor, new OnItemClickHandler() {

            @Override
            public void onItemClick(View item, int position) {

                cursor.moveToPosition(position);
                Intent dinnerIntent = new Intent(getApplicationContext(), DinnerActivity.class);
                Uri currentDinnerUri = ContentUris.withAppendedId(CONTENT_URI, position + 1);
                dinnerIntent.setData(currentDinnerUri);
                startActivity(dinnerIntent);
            }
        });

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        dinnerPartyListRecyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        dinnerPartyListRecyclerView.setHasFixedSize(true);
        dinnerPartyListRecyclerView.setAdapter(dinnerCursorAdapter);
        // show error view if no movies saved to favourites
        if (dinnerCursorAdapter.getItemCount() == 0) {
            showError();
        } else {
            showDinnerParties();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dinnerCursorAdapter.swapCursor(null);
    }


}
