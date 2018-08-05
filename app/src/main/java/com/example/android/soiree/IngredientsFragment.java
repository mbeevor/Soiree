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
import com.example.android.soiree.model.Ingredients;

import java.net.URL;
import java.util.ArrayList;

import static com.example.android.soiree.model.Keys.RECIPE_ID;

public class IngredientsFragment extends Fragment {

    private RecyclerView ingredientsRecyclerView;
    public IngredientsListAdapter currentRecipeListAdapter;
    private ArrayList<Ingredients> ingredientsList;
    private String recipeId;

    public IngredientsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ingredients, container, false);

        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            recipeId = savedInstanceState.getString(RECIPE_ID);
        }

        ingredientsRecyclerView = rootview.findViewById(R.id.ingredients_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false );
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setHasFixedSize(true);

        currentRecipeListAdapter = new IngredientsListAdapter(getContext());
        ingredientsRecyclerView.setAdapter(currentRecipeListAdapter);

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
        public void onTaskComplete(ArrayList<Ingredients> list) {

            ingredientsList = list;

            if (ingredientsList != null) {
                ingredientsRecyclerView.setAdapter(currentRecipeListAdapter);
                currentRecipeListAdapter.updateData(ingredientsList);

            }
        }
    }

}


