package uk.co.beevorwhite.soiree.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.beevorwhite.soiree.R;
import uk.co.beevorwhite.soiree.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ResultsListAdapterViewHolder>  {

    /** adapter for RecyclerView in SearchFragment showing list of results
     */

    private ArrayList<Recipe> recipesList;
    private OnItemClickHandler onItemClickHandler;
    private Context context;


    public ResultsListAdapter(Context activity, OnItemClickHandler handler) {
        context = activity;
        onItemClickHandler = handler;
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

    // notify the app that data has changed to refresh the view
    public void updateData(ArrayList<Recipe> recipes) {

        if (recipes != null)
        recipesList = new ArrayList<>(recipes);
        notifyDataSetChanged();
    }


    // view holder class that extends recyclerview holder
    public class ResultsListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.results_image_view) public ImageView resultsImageView;
        @BindView(R.id.results_rating_text_view) public TextView resultsRatingTextView;
        @BindView(R.id.results_title_text_view) public TextView resultsTitleTextView;


        public ResultsListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onItemClickHandler.onItemClick(v, position);
            }

        }

    }

  }
