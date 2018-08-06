package com.example.android.soiree;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.android.soiree.model.Dinner;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                starterNotes = dinner.getStarterNotes();
                mainNotes = dinner.getMainNotes();
                puddingNotes = dinner.getPuddingNotes();

            } else {
                starterNotes = DEFAULT_VALUE;
                mainNotes = DEFAULT_VALUE;
                puddingNotes = DEFAULT_VALUE;
            }
        }

        Intent backToCourseIntent = new Intent(getContext(), CourseActivity.class);
        String notes = editText.getText().toString();

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
        // return to course details after adding recipe
        dinner = new Dinner(dinnerName, starterId, starterName, starterUri, starterImage, starterNotes, mainId, mainName,
                mainUri, mainImage, mainNotes, puddingId, puddingName, puddingUri, puddingImage, puddingNotes, guestList);
        backToCourseIntent.putExtra(COURSE, courseName);
        backToCourseIntent.putExtra(DINNER, dinner);
        backToCourseIntent.setData(currentDinnerUri);
        startActivity(backToCourseIntent);

        return rootView;
    }

}
