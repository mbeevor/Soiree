package com.example.android.soiree.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.soiree.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsListAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.results_image_view) public ImageView resultsImageView;
    @BindView(R.id.results_rating_text_view) public TextView resultsRatingTextView;
    @BindView(R.id.results_title_text_view) public TextView resultsTitleTextView;


    public ResultsListAdapterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
