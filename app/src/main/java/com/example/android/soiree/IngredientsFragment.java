package com.example.android.soiree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soiree.Adapters.IngredientsListAdapter;
import com.example.android.soiree.AsyncTasks.GetIngredientsData;
import com.example.android.soiree.AsyncTasks.IngredientsAsyncTaskListener;
import com.example.android.soiree.Utils.NetworkUtils;
import com.example.android.soiree.model.Dinner;
import com.example.android.soiree.model.Ingredient;

import java.net.URL;
import java.util.ArrayList;

import static com.example.android.soiree.model.Keys.COURSE;
import static com.example.android.soiree.model.Keys.COURSE_MAIN;
import static com.example.android.soiree.model.Keys.COURSE_PUDDING;
import static com.example.android.soiree.model.Keys.COURSE_STARTER;
import static com.example.android.soiree.model.Keys.COURSE_UNKNOWN;
import static com.example.android.soiree.model.Keys.DEFAULT_VALUE;
import static com.example.android.soiree.model.Keys.DINNER;

public class IngredientsFragment extends Fragment {

    private RecyclerView ingredientsRecyclerView;
    public IngredientsListAdapter currentRecipeListAdapter;
    private ArrayList<Ingredient> ingredientsList;
    private Dinner dinner;
    private String dinnerName;
    private String starterId;
    private String mainId;
    private String puddingId;
    private String guestList;
    private String recipeNotes;
    private String courseName;
    private int currentCourse;

    public IngredientsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ingredients, container, false);

        CourseActivity activity = (CourseActivity) getActivity();
        Bundle getData = activity.dataForFragment();
        if (getData != null) {

            courseName = getData.getString(COURSE);
            dinner = getData.getParcelable(DINNER);

            if (courseName != null) {
                if (courseName.equals(getString(R.string.starter))) {
                    currentCourse = COURSE_STARTER;
                } else if (courseName.equals(getString(R.string.main))) {
                    currentCourse = COURSE_MAIN;
                } else if (courseName.equals(getString(R.string.pudding))) {
                    currentCourse = COURSE_PUDDING;
                } else {
                    currentCourse = COURSE_UNKNOWN;
                }
            }

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
        }

        ingredientsRecyclerView = rootview.findViewById(R.id.ingredients_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setHasFixedSize(true);

        currentRecipeListAdapter = new IngredientsListAdapter(getContext());
        ingredientsRecyclerView.setAdapter(currentRecipeListAdapter);

        String recipeId;

        switch (currentCourse) {
            case COURSE_STARTER:
                recipeId = dinner.getStarterId();
                break;

            case COURSE_MAIN:
                recipeId = dinner.getMainId();
                break;

            case COURSE_PUDDING:
                recipeId = dinner.getPuddingId();
                break;

            default:
                recipeId = DEFAULT_VALUE;
        }

        loadIngredientsList(recipeId);
        return rootview;

    }

    public void loadIngredientsList(String id) {

        if (id != null) {
            URL resultUrl = NetworkUtils.resultUrl(id);
            new GetIngredientsData(new GetIngredientsDataListener()).execute(resultUrl);
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

            }
        }
    }
}



