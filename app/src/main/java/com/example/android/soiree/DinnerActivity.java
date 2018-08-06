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
import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;
import static com.example.android.soiree.model.Keys.DINNER;
import static com.example.android.soiree.model.Keys.MAIN;
import static com.example.android.soiree.model.Keys.PUDDING;
import static com.example.android.soiree.model.Keys.STARTER;


public class DinnerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DINNER_LOADER = 0;

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
    private String courseName;
    @BindView(R.id.starter_card)
    CardView starterCard;
    @BindView(R.id.main_card)
    CardView mainCard;
    @BindView(R.id.pudding_card)
    CardView puddingCard;
    @BindView(R.id.guest_card)
    CardView guestCard;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.starter_image)
    ImageView starterImage;
    @BindView(R.id.main_image)
    ImageView mainImage;
    @BindView(R.id.pudding_image)
    ImageView puddingImage;
    @BindView(R.id.guest_image)
    ImageView guestImage;
    @BindView(R.id.starter_label)
    TextView starterLabel;
    @BindView(R.id.main_label) TextView mainLabel;
    @BindView(R.id.pudding_label) TextView puddingLabel;
    private Uri currentDinnerUri;


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
            // create layout for existing dinner
        } else {
            loadSavedDinner();
        }
    }

    private void loadSavedDinner() {

        getLoaderManager().initLoader(EXISTING_DINNER_LOADER, null, this);

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

        return new CursorLoader(this, currentDinnerUri, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor.moveToFirst()) {

            dinnerName = cursor.getString(cursor.getColumnIndex(DINNER_NAME));
            starterId = cursor.getString(cursor.getColumnIndex(STARTER_ID));
            starterName = cursor.getString(cursor.getColumnIndex(STARTER_NAME));
            starterUri = cursor.getString(cursor.getColumnIndex(STARTER_URI));
            mainId = cursor.getString(cursor.getColumnIndex(MAIN_ID));
            mainName = cursor.getString(cursor.getColumnIndex(MAIN_NAME));
            mainUri = cursor.getString(cursor.getColumnIndex(MAIN_URI));
            puddingId = cursor.getString(cursor.getColumnIndex(PUDDING_ID));
            puddingName = cursor.getString(cursor.getColumnIndex(PUDDING_NAME));
            puddingUri = cursor.getString(cursor.getColumnIndex(PUDDING_URI));
            guestList =  cursor.getString(cursor.getColumnIndex(GUEST_LIST));
            recipeNotes = cursor.getString(cursor.getColumnIndex(RECIPE_NOTES));

            // Load saved dinner
            dinner = new Dinner(dinnerName, starterId, starterName, starterUri, mainId, mainName,
                    mainUri, puddingId, puddingName, puddingUri, guestList, recipeNotes);
            setTitle(dinnerName);

            starterLabel.setText(starterName);
            starterCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseName = STARTER;
                    showSelectedCourseIntent(dinner, courseName, currentDinnerUri);
                }
            });

            mainLabel.setText(mainName);
            mainCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseName = MAIN;
                    showSelectedCourseIntent(dinner, courseName, currentDinnerUri);
                }
            });

            puddingLabel.setText(puddingName);
            puddingCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseName = PUDDING;
                    showSelectedCourseIntent(dinner, courseName, currentDinnerUri);
                }
            });

            guestCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseName = getString(R.string.guest_card_label);
                    showSelectedCourseIntent(dinner, courseName, currentDinnerUri);
                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    // method to launch intent when selecting a course from a saved dinner
    public void showSelectedCourseIntent(Dinner dinner, String course, Uri uri) {

        Intent showSelectedCourseIntent = new Intent(getApplicationContext(), CourseActivity.class);
        showSelectedCourseIntent.putExtra(DINNER, dinner);
        showSelectedCourseIntent.putExtra(COURSE, course);
        showSelectedCourseIntent.setData(uri);
        startActivity(showSelectedCourseIntent);

    }

}
