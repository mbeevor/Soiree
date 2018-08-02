package com.example.android.soiree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.model.Keys.COURSE;


public class DinnerActivity extends AppCompatActivity {

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
    String courseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        ButterKnife.bind(this);

        // assign toolbar to activity, and enable back button in action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        starterImage.setImageResource(R.drawable.plate);
        mainImage.setImageResource(R.drawable.pasta);
        puddingImage.setImageResource(R.drawable.raspberry);

        starterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courseName = getString(R.string.starter);
                showSelectedCourseIntent(courseName);


            }
        });

        mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courseName = getString(R.string.main);
                showSelectedCourseIntent(courseName);


            }
        });

        puddingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courseName = getString(R.string.pudding);
                showSelectedCourseIntent(courseName);

            }
        });
    }

    // method to launch intent when selecting a course
    public void showSelectedCourseIntent(String courseName) {

        Intent showSelectedCourseIntent = new Intent(getApplicationContext(), SearchActivity.class);
        showSelectedCourseIntent.putExtra(COURSE, courseName);
        startActivity(showSelectedCourseIntent);

    }
}
