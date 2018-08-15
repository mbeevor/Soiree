package uk.co.beevorwhite.soiree;

import android.app.LoaderManager;
import android.content.ContentUris;
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
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.beevorwhite.soiree.model.Dinner;

import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.CONTENT_URI;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.GUEST_LIST;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.MAIN_ID;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.MAIN_IMAGE;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.MAIN_NAME;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.MAIN_NOTES;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.MAIN_URI;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.PUDDING_ID;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.PUDDING_IMAGE;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.PUDDING_NAME;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.PUDDING_NOTES;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.PUDDING_URI;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.STARTER_ID;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.STARTER_IMAGE;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.STARTER_NAME;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.STARTER_NOTES;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry.STARTER_URI;
import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry._ID;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE;
import static uk.co.beevorwhite.soiree.model.Keys.DEFAULT_VALUE;
import static uk.co.beevorwhite.soiree.model.Keys.DINNER;
import static uk.co.beevorwhite.soiree.model.Keys.MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.STARTER;


public class DinnerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DINNER_LOADER = 0;

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
    ImageView starterImageView;
    @BindView(R.id.main_image)
    ImageView mainImageView;
    @BindView(R.id.pudding_image)
    ImageView puddingImageView;
    @BindView(R.id.guest_image)
    ImageView guestImageView;
    @BindView(R.id.starter_label)
    TextView starterLabel;
    @BindView(R.id.main_label)
    TextView mainLabel;
    @BindView(R.id.pudding_label)
    TextView puddingLabel;
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

        // set up transitions
        Transition enterTrans = new Explode();
        getWindow().setEnterTransition(enterTrans);

        Transition returnTrans = new Slide();
        getWindow().setReturnTransition(returnTrans);

        // get data from main activity
        Intent intent = getIntent();
        String uriLong = intent.getData().getLastPathSegment();
        currentDinnerUri = ContentUris.withAppendedId(CONTENT_URI, (Long.parseLong(uriLong)) + 1);

        // create layout for new dinner
        if (currentDinnerUri != null) {
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
            String selection = dinnerName + starterId + mainId + puddingId;
            String[] selectionArgs = {dinner.getDinnerName() + dinner.getStarterId() + dinner.getMainId() + dinner.getPuddingId()};
            int rowsDeleted = getContentResolver().delete(currentDinnerUri, selection, selectionArgs);
            getLoaderManager().restartLoader(EXISTING_DINNER_LOADER, null, this);
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
                STARTER_IMAGE,
                STARTER_NOTES,
                MAIN_ID,
                MAIN_NAME,
                MAIN_URI,
                MAIN_IMAGE,
                MAIN_NOTES,
                PUDDING_ID,
                PUDDING_NAME,
                PUDDING_URI,
                PUDDING_IMAGE,
                PUDDING_NOTES,
                GUEST_LIST,
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
            starterImage = cursor.getString(cursor.getColumnIndex(STARTER_IMAGE));
            starterNotes = cursor.getString(cursor.getColumnIndex(STARTER_NOTES));
            mainId = cursor.getString(cursor.getColumnIndex(MAIN_ID));
            mainName = cursor.getString(cursor.getColumnIndex(MAIN_NAME));
            mainUri = cursor.getString(cursor.getColumnIndex(MAIN_URI));
            mainImage = cursor.getString(cursor.getColumnIndex(MAIN_IMAGE));
            mainNotes = cursor.getString(cursor.getColumnIndex(MAIN_NOTES));
            puddingId = cursor.getString(cursor.getColumnIndex(PUDDING_ID));
            puddingName = cursor.getString(cursor.getColumnIndex(PUDDING_NAME));
            puddingUri = cursor.getString(cursor.getColumnIndex(PUDDING_URI));
            puddingImage = cursor.getString(cursor.getColumnIndex(PUDDING_IMAGE));
            puddingNotes = cursor.getString(cursor.getColumnIndex(PUDDING_NOTES));
            guestList = cursor.getString(cursor.getColumnIndex(GUEST_LIST));

            // Load saved dinner
            dinner = new Dinner(dinnerName, starterId, starterName, starterUri, starterImage, starterNotes,
                    mainId, mainName, mainUri, mainImage, mainNotes, puddingId,
                    puddingName, puddingUri, puddingImage, puddingNotes, guestList);
            setTitle(dinnerName);

            // use saved images for each course, or use placeholders
            if (starterImage.equals(null) || starterImage.equals(DEFAULT_VALUE)) {
                starterImageView.setImageResource(R.drawable.plate);
            } else {
                Picasso.with(this)
                        .load(starterImage)
                        .into(starterImageView);
            }

            if (mainImage.equals(null) || mainImage.equals(DEFAULT_VALUE)) {
                mainImageView.setImageResource(R.drawable.pasta);
            } else {
                Picasso.with(this)
                        .load(mainImage)
                        .into(mainImageView);
            }

            if (puddingImage.equals(null) || puddingImage.equals(DEFAULT_VALUE)) {
                puddingImageView.setImageResource(R.drawable.raspberry);
            } else {
                Picasso.with(this)
                        .load(puddingImage)
                        .into(puddingImageView);
            }

            guestImageView.setImageResource(R.drawable.party);

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
                    Intent showGuestsIntent = new Intent(getApplicationContext(), GuestActivity.class);
                    showGuestsIntent.putExtra(DINNER, dinner);
                    showGuestsIntent.setData(currentDinnerUri);
                    startActivity(showGuestsIntent);
                }
            });
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
                loader.reset();
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
