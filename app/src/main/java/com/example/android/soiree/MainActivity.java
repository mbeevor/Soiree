package com.example.android.soiree;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.soiree.Adapters.DinnerCursorAdapter;
import com.example.android.soiree.Adapters.OnItemClickHandler;
import com.example.android.soiree.model.Dinner;

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
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DINNER_LOADER = 0;

    private Dinner dinner;
    private String dinnerName;
    private String starterId;
    private String starterName;
    private String starterUri;
    private String mainId;
    private String mainName;
    private String mainUri;
    private String puddingId;
    private String puddingName;
    private String puddingUri;
    private String guestList;
    private String recipeNotes;
    @BindView(R.id.new_fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.dinner_party_list_recyclerview)
    RecyclerView dinnerPartyListRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyTextView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private Uri currentDinnerUri;
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
                showInputDialog();
            }
        });
    }
    
    public void showInputDialog() {

        // use custom layout for prompt dialog
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText nameEditText = promptView.findViewById(R.id.edit_text);

        // setup a dialog window
        alertDialogBuilder.setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dinnerName = nameEditText.getText().toString().trim();
                createNewDinner();
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            // cancel adding new dinner
            public void onClick (DialogInterface dialog,int id){
                dialog.cancel();
            }
        });

        // create and show the alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void createNewDinner() {

        starterId = DEFAULT_VALUE;
        starterName = getString(R.string.starter);
        starterUri = DEFAULT_VALUE;
        mainId = DEFAULT_VALUE;
        mainName = getString(R.string.main);
        mainUri = DEFAULT_VALUE;
        puddingId = DEFAULT_VALUE;
        puddingName = getString(R.string.pudding);
        puddingUri = DEFAULT_VALUE;
        guestList = DEFAULT_VALUE;
        recipeNotes = DEFAULT_VALUE;

        // create new dinner, using default values as no recipes have been selected yet
        dinner = new Dinner(dinnerName, starterId, starterName, starterUri, mainId, mainName,
                mainUri, puddingId, puddingName, puddingUri, guestList, recipeNotes);

        // build new dinner for database
        ContentValues contentValues = new ContentValues();
        contentValues.put(DINNER_NAME, dinner.getDinnerName());
        contentValues.put(STARTER_ID, dinner.getStarterId());
        contentValues.put(STARTER_NAME, dinner.getStarterName());
        contentValues.put(STARTER_URI, dinner.getStarterUri());
        contentValues.put(MAIN_ID, dinner.getMainId());
        contentValues.put(MAIN_NAME, dinner.getMainName());
        contentValues.put(MAIN_URI, dinner.getMainUri());
        contentValues.put(PUDDING_ID, dinner.getPuddingId());
        contentValues.put(PUDDING_NAME, dinner.getPuddingName());
        contentValues.put(PUDDING_URI, dinner.getPuddingUri());
        contentValues.put(GUEST_LIST, dinner.getGuestList());
        contentValues.put(RECIPE_NOTES, dinner.getRecipeNotes());

        // add dinner to database
        getContentResolver().insert(CONTENT_URI, contentValues);

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
                currentDinnerUri = ContentUris.withAppendedId(CONTENT_URI, position + 1);
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
