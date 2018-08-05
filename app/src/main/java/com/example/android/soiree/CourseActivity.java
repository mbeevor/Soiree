package com.example.android.soiree;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.android.soiree.model.Dinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.data.DinnerContract.DinnerEntry.CONTENT_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.GUEST_LIST;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.RECIPE_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry._ID;
import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;
import static com.example.android.soiree.model.Keys.DINNER;

public class CourseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DINNER_LOADER = 5;

    private Dinner dinner;
    private String dinnerName;
    private String starterId;
    private String mainId;
    private String puddingId;
    private String guestList;
    private String recipeNotes;
    private String courseName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.search_fab)
    FloatingActionButton floatingActionButton;
    private Uri currentDinnerUri;
    private Intent searchActivity;
    Context context = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);

        // get intent from launching activity
        Intent getIntent = getIntent();
        dinner = getIntent.getParcelableExtra(DINNER);
        courseName = getIntent.getStringExtra(COURSE);
        currentDinnerUri = getIntent.getData();

        searchActivity = new Intent(getApplicationContext(), SearchActivity.class);

        // assign toolbar to activity, and enable back button in action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set up view pager and tablayout
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        if (dinner != null) {
            dinnerName = dinner.getDinnerName();
            starterId = dinner.getStarterId();
            mainId = dinner.getMainId();
            puddingId = dinner.getPuddingId();
            guestList = dinner.getGuestList();
            recipeNotes = dinner.getRecipeNotes();
        } else {
            dinnerName = DEFAULT_VALUE;
            starterId = DEFAULT_VALUE;
            mainId = DEFAULT_VALUE;
            puddingId = DEFAULT_VALUE;
            guestList = DEFAULT_VALUE;
            recipeNotes = DEFAULT_VALUE;
        }

        if (courseName != null) {
            setTitle(courseName);
        }

        if (currentDinnerUri != null) {
            loadSavedDinner();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* if no recipe has been previously selected, the IDs for each course will be set to the default value,
                 * and we can run a search to find a recipe. Otherwise, you will be warned that data will be lost before
                 * you start a new search.
                 */
                if ((starterId != null && starterId.equals(DEFAULT_VALUE)) ||
                        (mainId != null && mainId.equals(DEFAULT_VALUE)) ||
                        (puddingId != null && puddingId.equals(DEFAULT_VALUE))) {
                    searchRecipes();
                } else {
                    warnReplaceRecipe();
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (currentDinnerUri != null) {
            updateSavedDinner();
        }
    }

    private void loadSavedDinner() {

        getLoaderManager().initLoader(EXISTING_DINNER_LOADER, null, this);

    }

    private void setupViewPager(ViewPager pager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IngredientsFragment(), getString(R.string.ingredients_header));
        adapter.addFragment(new NotesFragment(), getString(R.string.notes_header));
        pager.setAdapter(adapter);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                _ID,
                DINNER_NAME,
                STARTER_ID,
                MAIN_ID,
                PUDDING_ID,
                GUEST_LIST,
                RECIPE_NOTES,
        };

        return new CursorLoader(this, currentDinnerUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor.moveToFirst()) {
            int dinnerNameIndex = cursor.getColumnIndex(DINNER_NAME);
            int starterIdIndex = cursor.getColumnIndex(STARTER_ID);
            int mainIdIndex = cursor.getColumnIndex(MAIN_ID);
            int puddingIdIndex = cursor.getColumnIndex(PUDDING_ID);
            int guestIdIndex = cursor.getColumnIndex(GUEST_LIST);
            int notesIdIndex = cursor.getColumnIndex(RECIPE_NOTES);

            dinnerName = cursor.getString(dinnerNameIndex);
            starterId = cursor.getString(starterIdIndex);
            mainId = cursor.getString(mainIdIndex);
            puddingId = cursor.getString(puddingIdIndex);
            guestList = cursor.getString(guestIdIndex);
            recipeNotes = cursor.getString(notesIdIndex);

            dinner = new Dinner(dinnerName, starterId, mainId, puddingId, guestList, recipeNotes);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> pagerList = new ArrayList<>();
        private final List<String> pagerTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagerList.get(position);
        }

        @Override
        public int getCount() {
            return pagerTitleList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            pagerList.add(fragment);
            pagerTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return pagerTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DinnerActivity.class);
        intent.setData(currentDinnerUri);
        super.onBackPressed();
    }

    private void updateSavedDinner() {

        // update current dinner in database
        ContentValues contentValues = new ContentValues();
        contentValues.put(DINNER_NAME, dinnerName);
        contentValues.put(STARTER_ID, starterId);
        contentValues.put(MAIN_ID, mainId);
        contentValues.put(PUDDING_ID, puddingId);
        contentValues.put(GUEST_LIST, guestList);
        contentValues.put(RECIPE_NOTES, recipeNotes);

        if (currentDinnerUri != null) {
            // update dinner in database
            int rowsAffected = getContentResolver().update(currentDinnerUri, contentValues, null, null);
            if (rowsAffected != 0) {
                Toast.makeText(this, "Dinner updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Dinner update failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            currentDinnerUri = getContentResolver().insert(CONTENT_URI, contentValues);
        }
        loadSavedDinner();
    }

    public void warnReplaceRecipe() {
        // alert dialog when FAB selected and recipe already saved
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.replace_saved_recipe);

        builder.setPositiveButton(R.string.confirm_replace_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchRecipes();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicks cancel
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void searchRecipes() {
        searchActivity.putExtra(COURSE, courseName);
        searchActivity.putExtra(DINNER, dinner);
        searchActivity.setData(currentDinnerUri);
        startActivity(searchActivity);
    }

}
