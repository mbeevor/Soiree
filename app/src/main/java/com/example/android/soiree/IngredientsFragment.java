package com.example.android.soiree;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.soiree.Adapters.IngredientsListAdapter;
import com.example.android.soiree.AsyncTasks.GetIngredientsData;
import com.example.android.soiree.AsyncTasks.IngredientsAsyncTaskListener;
import com.example.android.soiree.Utils.NetworkUtils;
import com.example.android.soiree.model.Dinner;
import com.example.android.soiree.model.Ingredient;

import java.net.URL;
import java.util.ArrayList;

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

    public IngredientsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootview);

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
                if (currentRecipeListAdapter.getItemCount() != 0) {
                    showIngredientsList();
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
}



