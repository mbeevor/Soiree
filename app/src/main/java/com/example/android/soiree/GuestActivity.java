package com.example.android.soiree;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.soiree.model.Dinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.data.DinnerContract.DinnerEntry.CONTENT_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.GUEST_LIST;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_IMAGE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_IMAGE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_IMAGE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_URI;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;
import static com.example.android.soiree.model.Keys.DINNER;
import static com.example.android.soiree.model.Keys.MAIN;
import static com.example.android.soiree.model.Keys.PUDDING;
import static com.example.android.soiree.model.Keys.STARTER;

public class GuestActivity extends AppCompatActivity {

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
    private Uri currentDinnerUri;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.guests_edit_text)
    EditText editText;
    private String notes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guests);
        ButterKnife.bind(this);

        // assign toolbar to activity, and enable back button in action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get intent from launching activity
        Intent getIntent = getIntent();
        dinner = getIntent.getParcelableExtra(DINNER);
        currentDinnerUri = getIntent.getData();

        if (dinner != null) {
            dinnerName = dinner.getDinnerName();
            starterId = dinner.getStarterId();
            starterName = dinner.getStarterName();
            starterUri = dinner.getStarterUri();
            starterImage = dinner.getStarterImage();
            starterNotes = dinner.getStarterNotes();

            mainId = dinner.getMainId();
            mainName = dinner.getMainName();
            mainUri = dinner.getMainUri();
            mainImage = dinner.getMainImage();
            mainNotes = dinner.getMainNotes();

            puddingId = dinner.getPuddingId();
            puddingName = dinner.getPuddingName();
            puddingUri = dinner.getPuddingUri();
            puddingImage = dinner.getPuddingImage();
            puddingNotes = dinner.getPuddingNotes();

            guestList = dinner.getGuestList();
        } else {
            starterId = DEFAULT_VALUE;
            starterName = STARTER;
            starterUri = DEFAULT_VALUE;
            starterImage = DEFAULT_VALUE;
            starterNotes = DEFAULT_VALUE;

            mainId = DEFAULT_VALUE;
            mainName = MAIN;
            mainUri = DEFAULT_VALUE;
            mainImage = DEFAULT_VALUE;
            mainNotes = DEFAULT_VALUE;

            puddingId = DEFAULT_VALUE;
            puddingName = PUDDING;
            puddingUri = DEFAULT_VALUE;
            puddingImage = DEFAULT_VALUE;
            puddingNotes = DEFAULT_VALUE;

            guestList = DEFAULT_VALUE;
        }

        setTitle(dinnerName);

        if (guestList.equals(DEFAULT_VALUE)) {
            editText.setText(null);
        } else {
            editText.setText(guestList);
        }


        notes = editText.getText().toString();
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                notes = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                guestList = dinner.setGuestList(notes);
            }


            @Override
            public void afterTextChanged(Editable s) {
                updateGuestList();
            }


        });
    }

    public void updateGuestList() {
        dinner = new Dinner(dinnerName, starterId, starterName, starterUri, starterImage, starterNotes, mainId, mainName,
                mainUri, mainImage, mainNotes, puddingId, puddingName, puddingUri, puddingImage, puddingNotes, guestList);

        // update current dinner in database
        ContentValues contentValues = new ContentValues();
        contentValues.put(DINNER_NAME, dinner.getDinnerName());
        contentValues.put(STARTER_ID, dinner.getStarterId());
        contentValues.put(STARTER_NAME, dinner.getStarterName());
        contentValues.put(STARTER_URI, dinner.getStarterUri());
        contentValues.put(STARTER_IMAGE, dinner.getStarterImage());
        contentValues.put(STARTER_NOTES, dinner.getStarterNotes());
        contentValues.put(MAIN_ID, dinner.getMainId());
        contentValues.put(MAIN_NAME, dinner.getMainName());
        contentValues.put(MAIN_URI, dinner.getMainUri());
        contentValues.put(MAIN_IMAGE, dinner.getMainImage());
        contentValues.put(MAIN_NOTES, dinner.getMainNotes());
        contentValues.put(PUDDING_ID, dinner.getPuddingId());
        contentValues.put(PUDDING_NAME, dinner.getPuddingName());
        contentValues.put(PUDDING_URI, dinner.getPuddingUri());
        contentValues.put(PUDDING_IMAGE, dinner.getPuddingImage());
        contentValues.put(PUDDING_NOTES, dinner.getPuddingNotes());
        contentValues.put(GUEST_LIST, dinner.getGuestList());

        if (currentDinnerUri != null) {
            // update dinner in database
            int rowsAffected = getContentResolver().update(currentDinnerUri, contentValues, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, R.string.dinner_update_failed, Toast.LENGTH_SHORT).show();
            }
        } else {
            currentDinnerUri = getContentResolver().insert(CONTENT_URI, contentValues);
        }
    }
}
