package com.example.android.soiree;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.COURSE_MAIN;
import static com.example.android.soiree.model.Keys.COURSE_PUDDING;
import static com.example.android.soiree.model.Keys.COURSE_STARTER;
import static com.example.android.soiree.model.Keys.COURSE_UNKNOWN;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;
import static com.example.android.soiree.model.Keys.DINNER;
import static com.example.android.soiree.model.Keys.MAIN;
import static com.example.android.soiree.model.Keys.PUDDING;
import static com.example.android.soiree.model.Keys.STARTER;
import static com.example.android.soiree.model.Keys.URI;

public class NotesFragment extends Fragment {

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
    private int currentCourse;
    private Uri currentDinnerUri;
    @BindView(R.id.notes_edit_text)
    EditText editText;
    String notes;

    public NotesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, rootView);

        CourseActivity activity = (CourseActivity) getActivity();
        Bundle getData = activity.dataForFragment();
        if (getData != null) {

            courseName = getData.getString(COURSE);
            dinner = getData.getParcelable(DINNER);
            currentDinnerUri = getData.getParcelable(URI);

            if (courseName != null) {
                if (courseName.equals(STARTER)) {
                    currentCourse = COURSE_STARTER;
                } else if (courseName.equals(MAIN)) {
                    currentCourse = COURSE_MAIN;
                } else if (courseName.equals(PUDDING)) {
                    currentCourse = COURSE_PUDDING;
                } else {
                    currentCourse = COURSE_UNKNOWN;
                }
            }

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

        switch (currentCourse) {
            case COURSE_STARTER:
                starterNotes = dinner.getStarterNotes();
                if (starterNotes.equals(DEFAULT_VALUE)) {
                    editText.setText(null);
                } else {
                    editText.setText(starterNotes);
                }
                break;

            case COURSE_MAIN:
                mainNotes = dinner.getMainNotes();
                if (mainNotes.equals(DEFAULT_VALUE)) {
                    editText.setText(null);
                } else {
                    editText.setText(mainNotes);
                }
                break;

            case COURSE_PUDDING:
                puddingNotes = dinner.getPuddingNotes();
                if (puddingNotes.equals(DEFAULT_VALUE)) {
                    editText.setText(null);
                } else {
                    editText.setText(puddingNotes);
                }
                break;

            default:
        }

        notes = editText.getText().toString();
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                notes = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (currentCourse) {
                    case COURSE_STARTER:
                        starterNotes = dinner.setStarterNotes(notes);
                        break;

                    case COURSE_MAIN:
                        mainNotes = dinner.setMainNotes(notes);
                        break;

                    case COURSE_PUDDING:
                        puddingNotes = dinner.setPuddingNotes(notes);
                        break;
                    default:
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateDinner();
            }
        });


        return rootView;
    }

    public void updateDinner() {
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
            int rowsAffected = getActivity().getContentResolver().update(currentDinnerUri, contentValues, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(getActivity(), R.string.dinner_update_failed, Toast.LENGTH_SHORT).show();
            }
        } else {
            currentDinnerUri = getActivity().getContentResolver().insert(CONTENT_URI, contentValues);
        }

    }

}
