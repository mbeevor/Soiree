package uk.co.beevorwhite.soiree;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.beevorwhite.soiree.Adapters.IngredientsListAdapter;
import uk.co.beevorwhite.soiree.AsyncTasks.IngredientsAsyncTaskListener;
import uk.co.beevorwhite.soiree.model.Dinner;
import uk.co.beevorwhite.soiree.model.Ingredient;
import uk.co.beevorwhite.soiree.widget.widget;

import static uk.co.beevorwhite.soiree.model.Keys.ACTION_UPDATE_WIDGET;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_STARTER;
import static uk.co.beevorwhite.soiree.model.Keys.COURSE_UNKNOWN;
import static uk.co.beevorwhite.soiree.model.Keys.DEFAULT_VALUE;
import static uk.co.beevorwhite.soiree.model.Keys.DINNER;
import static uk.co.beevorwhite.soiree.model.Keys.MAIN;
import static uk.co.beevorwhite.soiree.model.Keys.PUDDING;
import static uk.co.beevorwhite.soiree.model.Keys.RECIPE_INGREDIENTS;
import static uk.co.beevorwhite.soiree.model.Keys.STARTER;

public class IngredientsFragment extends Fragment {

    public IngredientsListAdapter currentRecipeListAdapter;
    private ArrayList<Ingredient> ingredientsList;
    private Dinner dinner;
    private String starterId;
    private String mainId;
    private String puddingId;
    private String courseName;
    private int currentCourse;
    @BindView(R.id.ingredients_recyclerview)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.empty_ingredients_view)
    TextView emptyRecyclerView;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootview);

        // set up shared preferences to save ingredients list for widget
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        CourseActivity activity = (CourseActivity) getActivity();
        Bundle getData = activity.dataForFragment();
        if (getData != null) {

            courseName = getData.getString(COURSE);
            dinner = getData.getParcelable(DINNER);

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
                starterId = dinner.getStarterId();
                mainId = dinner.getMainId();
                puddingId = dinner.getPuddingId();

            } else {
                starterId = DEFAULT_VALUE;
                mainId = DEFAULT_VALUE;
                puddingId = DEFAULT_VALUE;

            }
        }

        String recipeId;

        switch (currentCourse) {
            case COURSE_STARTER:
                recipeId = starterId;
                break;

            case COURSE_MAIN:
                recipeId = mainId;
                break;

            case COURSE_PUDDING:
                recipeId = puddingId;
                break;

            case COURSE_UNKNOWN:
                recipeId = starterId;
                break;

            default:
                recipeId = DEFAULT_VALUE;
        }

        loadIngredientsList(recipeId);

        ingredientsRecyclerView = rootview.findViewById(R.id.ingredients_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setHasFixedSize(true);
        currentRecipeListAdapter = new IngredientsListAdapter(getContext());
        ingredientsRecyclerView.setAdapter(currentRecipeListAdapter);

        return rootview;

    }

    public void loadIngredientsList(String id) {

        if (id != null) {

//            new GetIngredientsData(new GetIngredientsDataListener()).execute(resultUrl);
        }

    }

    // initiate AsyncTask to return list of ingredients and attach to adapter
    public class GetIngredientsDataListener implements IngredientsAsyncTaskListener {


        @Override
        public void onTaskComplete(ArrayList<Ingredient> list) {

            ingredientsList = list;

            if (ingredientsList != null) {
                ingredientsRecyclerView.setAdapter(currentRecipeListAdapter);
                currentRecipeListAdapter.updateData(ingredientsList);
                if (currentRecipeListAdapter.getItemCount() != 0) {
                    showIngredientsList();
                    saveArrayList(RECIPE_INGREDIENTS, ingredientsList);
                } else {
                    showEmptyView();
                }

            }
        }
    }

    public void showEmptyView() {
        ingredientsRecyclerView.setVisibility(View.GONE);
        emptyRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showIngredientsList() {
        ingredientsRecyclerView.setVisibility(View.VISIBLE);
        emptyRecyclerView.setVisibility(View.GONE);
    }

    public void saveArrayList(String key, ArrayList<Ingredient> ingredients) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ingredients);
        editor.putString(key, json);
        editor.apply();

        // send intent to also update widget
        Intent widgetIntent = new Intent(getContext(), widget.class);
        widgetIntent.setAction(ACTION_UPDATE_WIDGET);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int [] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), widget.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getContext().sendBroadcast(widgetIntent);

    }

}



