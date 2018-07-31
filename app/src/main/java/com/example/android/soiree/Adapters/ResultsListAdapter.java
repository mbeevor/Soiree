package com.example.android.soiree.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soiree.R;
import com.example.android.soiree.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapterViewHolder>  {

    /** adapter for RecyclerView in SearchFragment showing list of results
     */

    private ArrayList<Recipe> recipesList;
    private Context context;

    public ResultsListAdapter(Context activity) {
        context = activity;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ResultsListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.item_search_result;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutForListItem, parent, false);
        ResultsListAdapterViewHolder viewHolder = new ResultsListAdapterViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ResultsListAdapterViewHolder holder, int position) {

        if (recipesList != null) {

            Recipe recipe = recipesList.get(position);
            String recipeTitle = recipe.getRecipeTitle();
            String methodUrl = recipe.getMethodUrl();
            String recipeId = recipe.getRecipeId();
            String recipeImage = recipe.getRecipeImage();
            String recipeRank = recipe.getRecipeRank();

            // reduce rank to two digits
            String recipeRankReduced = recipeRank.substring(0,2);

            ResultsListAdapterViewHolder viewHolder = holder;
            viewHolder.resultsTitleTextView.setText(recipeTitle);
            viewHolder.resultsRatingTextView.setText(recipeRankReduced);

            Picasso.with(viewHolder.resultsImageView.getContext())
                    .load(recipeImage)
                    .into(viewHolder.resultsImageView);

        }

    }

    @Override
    public int getItemCount() {
        if (recipesList == null) {
            return  0;
        } return recipesList.size();
    }

    public void updateData(ArrayList<Recipe> recipes) {

        if (recipes != null)
        recipesList = new ArrayList<>(recipes);
        notifyDataSetChanged();
    }

}
