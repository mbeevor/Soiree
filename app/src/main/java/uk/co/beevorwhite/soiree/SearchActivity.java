package uk.co.beevorwhite.soiree;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.beevorwhite.soiree.model.Dinner;
import uk.co.beevorwhite.soiree.model.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

import static uk.co.beevorwhite.soiree.model.Keys.COURSE;
import static uk.co.beevorwhite.soiree.model.Keys.DINNER;
import static uk.co.beevorwhite.soiree.model.Keys.MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.STARTER;

public class SearchActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;

    public String searchQuery;
    private Dinner dinner;
    @BindView(R.id.search_bar)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Uri currentDinnerUri;
    private String courseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_search);
        ButterKnife.bind(this);
        Intent getIntent = getIntent();
        dinner = getIntent.getParcelableExtra(DINNER);
        courseName = getIntent.getStringExtra(COURSE);
        currentDinnerUri = getIntent.getData();

        if (courseName != null) {
            if (courseName.equals(STARTER)) {
                setTitle(dinner.getStarterName());
            } else if (courseName.equals(MAIN)) {
                setTitle(dinner.getMainName());
            } else if (courseName.equals(PUDDING)) {
                setTitle(dinner.getPuddingName());
            } else {
                setTitle(R.string.app_name);
            }
        }

        // assign toolbar to activity, and enable back button in action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);

    }

    // use this method to ensure 'singletop' is replicated when using search
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void doMySearch(String query) {

        if (courseName != null) {
            Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
            searchResultsIntent.putExtra(Keys.QUERY, query);
            searchResultsIntent.putExtra(COURSE, courseName);
            searchResultsIntent.putExtra(DINNER, dinner);
            searchResultsIntent.setData(currentDinnerUri);
            startActivity(searchResultsIntent);

        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(searchQuery);
            sendAnalyticsData(searchQuery);

        }
    }

    // method to record terms users search for
    private void sendAnalyticsData(String query) {

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, query);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


    }

}
