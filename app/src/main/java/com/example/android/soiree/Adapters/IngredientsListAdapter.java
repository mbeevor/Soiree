package com.example.android.soiree.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.soiree.R;
import com.example.android.soiree.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsListAdapterViewHolder> {

    private ArrayList<Ingredient> ingredientsList;
    private Context context;

    public IngredientsListAdapter(Context activity) {
        context = activity;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public IngredientsListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutForRecipeDetails = R.layout.item_ingredient;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(layoutForRecipeDetails, parent, false);
        IngredientsListAdapterViewHolder viewHolder = new IngredientsListAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListAdapterViewHolder holder, int position) {

        if (ingredientsList != null) {


                Ingredient ingredient = ingredientsList.get(position);
                String ingredientItem = ingredient.getIngredientItem();

                IngredientsListAdapterViewHolder viewHolder = holder;
                viewHolder.ingredientTextView.setText(ingredientItem);

        }
    }

    // notify the app that data has changed to refresh the view
    public void updateData(ArrayList<Ingredient> ingredients) {

        if (ingredients != null)
            ingredientsList = new ArrayList<>(ingredients);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (ingredientsList == null) {
            return 0;
        } else {
            return ingredientsList.size();
        }
    }

    public class IngredientsListAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_item_tv)
        public TextView ingredientTextView;

        public IngredientsListAdapterViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
