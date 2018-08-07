package com.example.android.soiree;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.android.soiree.model.Dinner;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.guests_edit_text)
    EditText editText;
    String notes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guests);
        ButterKnife.bind(this);

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

    }


}
