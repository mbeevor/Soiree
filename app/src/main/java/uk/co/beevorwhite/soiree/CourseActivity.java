package uk.co.beevorwhite.soiree;

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
import android.widget.Button;
import android.widget.Toast;

import uk.co.beevorwhite.soiree.model.Dinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_STARTER;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_UNKNOWN;
import static uk.co.beevorwhite.soiree.model.Keys.DEFAULT_VALUE;
import static uk.co.beevorwhite.soiree.model.Keys.DINNER;
import static uk.co.beevorwhite.soiree.model.Keys.MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.STARTER;
import static uk.co.beevorwhite.soiree.model.Keys.URI;

public class CourseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DINNER_LOADER = 5;

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
    private String recipeNotes;
    private String courseName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.recipe_button)
    Button recipeButton;
    @BindView(R.id.search_fab)
    FloatingActionButton floatingActionButton;
    private Uri currentDinnerUri;
    private Intent searchActivity;
    private int currentCourse;
    private String recipeId;
    private String recipeUri;
    Context context = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);

        // get intent from launching activity
        Intent getIntent = getIntent();
        dinner = getIntent.getParcelableExtra(DINNER);
        currentDinnerUri = getIntent.getData();
        courseName = getIntent.getStringExtra(COURSE);

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

        /* check the course name to determining if updating a starter, main or pudding.
        Update recipeId accordingly to determine behaviour, including app title.
         */
        if (courseName != null) {
            if (courseName.equals(STARTER)) {
                setTitle(dinner.getStarterName());
                currentCourse = COURSE_STARTER;
                recipeId = dinner.getStarterId();
                recipeUri = dinner.getStarterUri();

            } else if (courseName.equals(MAIN)) {
                setTitle(dinner.getMainName());
                currentCourse = COURSE_MAIN;
                recipeId = dinner.getMainId();
                recipeUri = dinner.getMainUri();

            } else if (courseName.equals(PUDDING)) {
                setTitle(dinner.getPuddingName());
                currentCourse = COURSE_PUDDING;
                recipeId = dinner.getPuddingId();
                recipeUri = dinner.getPuddingUri();

            } else {
                setTitle(R.string.app_name);
                currentCourse = COURSE_UNKNOWN;
                recipeId = null;
                recipeUri = null;
            }
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
                if (recipeId == null) {
                    searchRecipes();
                } else if (recipeId.equals(DEFAULT_VALUE)) {
                    searchRecipes();
                } else {
                    // recipe already saved
                    warnReplaceRecipe();
                }
            }
        });

        if (recipeUri == null || recipeUri.equals(DEFAULT_VALUE)) {
            // hide recipe button if no recipe has been saved
            recipeButton.setVisibility(View.GONE);
        } else {
            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(recipeUri));
                    startActivity(intent);
                }
            });
        }

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

    public Bundle dataForFragment() {

        Bundle recipeBundleForFragment = new Bundle();
        recipeBundleForFragment.putParcelable(DINNER, dinner);
        recipeBundleForFragment.putString(COURSE, courseName);
        recipeBundleForFragment.putParcelable(URI, currentDinnerUri);
        return recipeBundleForFragment;

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
