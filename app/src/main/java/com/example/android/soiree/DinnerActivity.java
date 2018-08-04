package com.example.android.soiree;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.soiree.Adapters.DinnerCursorAdapter;
import com.example.android.soiree.Adapters.OnItemClickHandler;
import com.example.android.soiree.model.Dinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.data.DinnerContract.DinnerEntry.CONTENT_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.RECIPE_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry._ID;
import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;


public class DinnerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DINNER_LOADER = 0;

    private Dinner dinner;
    private String starterId;
    private String mainId;
    private String puddingId;
    private String recipeNotes;
    @BindView(R.id.save_button)
    Button saveButton;
    @BindView(R.id.starter_card)
    CardView starterCard;
    @BindView(R.id.main_card)
    CardView mainCard;
    @BindView(R.id.pudding_card)
    CardView puddingCard;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.starter_image)
    ImageView starterImage;
    @BindView(R.id.main_image)
    ImageView mainImage;
    @BindView(R.id.pudding_image)
    ImageView puddingImage;
    @BindView(R.id.guest_image) ImageView guestImage;
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.entered_name)
    TextView enteredName;
    String courseName;
    private DinnerCursorAdapter dinnerCursorAdapter;
    private Uri currentDinnerUri;
    private String dinnerName;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_delete_dinner:
                confirmDeletion();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        ButterKnife.bind(this);

        // assign toolbar to activity, and enable back button in action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get data from main activity
        Intent intent = getIntent();
        currentDinnerUri = intent.getData();

        starterImage.setImageResource(R.drawable.plate);
        mainImage.setImageResource(R.drawable.pasta);
        puddingImage.setImageResource(R.drawable.raspberry);
        guestImage.setImageResource(R.drawable.party);

        // create layout for new dinner
        if (currentDinnerUri == null) {
            setTitle(R.string.new_dinner_party);
            invalidateOptionsMenu();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validate a name has been entered
                dinnerName = nameEditText.getText().toString().trim();
                if (TextUtils.isEmpty(dinnerName)) {
                    nameEditText.setError(getString(R.string.edit_text_empty_warning));
                } else {
                    createNewDinner();
                    nameEditText.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);
                    setTitle(dinnerName);
                }
            }
        });

        // create layout for existing dinner
        } else {
            setTitle(R.string.existing_dinner_party);
            nameEditText.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            enteredName.setVisibility(View.GONE);
            loadSavedDinner();
        }


//        starterCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                courseName = getString(R.string.starter);
//                showSelectedCourseIntent(courseName);
//
//
//            }
//        });
//
//        mainCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                courseName = getString(R.string.main);
//                showSelectedCourseIntent(courseName);
//
//
//            }
//        });
//
//        puddingCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                courseName = getString(R.string.pudding);
//                showSelectedCourseIntent(courseName);
//
//            }
//        });
    }

    private void createNewDinner() {

        // create new dinner, using default values as no recipes have been selected yet
        dinner = new Dinner(dinnerName, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);

        // build new dinner for database
        ContentValues contentValues = new ContentValues();
        contentValues.put(DINNER_NAME, dinner.getDinnerName());
        contentValues.put(STARTER_ID, dinner.getStarterId());
        contentValues.put(MAIN_ID, dinner.getMainId());
        contentValues.put(PUDDING_ID, dinner.getPuddingId());
        contentValues.put(RECIPE_NOTES, dinner.getRecipeNotes());

        // add dinner to database
        getContentResolver().insert(CONTENT_URI, contentValues);

    }

    private void loadSavedDinner() {

        getLoaderManager().initLoader(EXISTING_DINNER_LOADER, null, this);

    }

    // method to launch intent when selecting a course
    public void showSelectedCourseIntent(String courseName) {

        Intent showSelectedCourseIntent = new Intent(getApplicationContext(), CourseActivity.class);
        showSelectedCourseIntent.putExtra(COURSE, courseName);
        startActivity(showSelectedCourseIntent);

    }

    private void confirmDeletion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_warning_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            // Deletion confirmed
            public void onClick(DialogInterface dialog, int id) {
                deleteDinner();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            // Deletion cancelled
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteDinner() {

        if (dinner == null) {
            Toast.makeText(this, R.string.unable_to_delete, Toast.LENGTH_SHORT).show();
        } else if (currentDinnerUri != null) {
            String[] currentDinnerNameArray = {dinner.getDinnerName()};
            int rowsDeleted = getContentResolver().delete(CONTENT_URI, DINNER_NAME + "=?", currentDinnerNameArray);
            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.unable_to_delete, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.delete_successful, Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                _ID,
                DINNER_NAME,
                STARTER_ID,
                MAIN_ID,
                PUDDING_ID,
                RECIPE_NOTES,
        };

        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {

        if (cursor.moveToFirst()) {
            int dinnerNameIndex = cursor.getColumnIndex(DINNER_NAME);
            int starterIdIndex = cursor.getColumnIndex(STARTER_ID);
            int mainIdIndex = cursor.getColumnIndex(MAIN_ID);
            int puddingIdIndex = cursor.getColumnIndex(PUDDING_ID);
            int notesIdIndex = cursor.getColumnIndex(RECIPE_NOTES);

            dinnerName = cursor.getString(dinnerNameIndex);
            starterId = cursor.getString(starterIdIndex);
            mainId = cursor.getString(mainIdIndex);
            puddingId = cursor.getString(puddingIdIndex);
            recipeNotes = cursor.getString(notesIdIndex);

            dinner = new Dinner(dinnerName, starterId, mainId, puddingId, recipeNotes);
            setTitle(dinnerName);
        }


        dinnerCursorAdapter = new DinnerCursorAdapter(cursor, new OnItemClickHandler() {
            @Override
            public void onItemClick(View item, int position) {

                cursor.moveToPosition(position);

            }
        });


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
