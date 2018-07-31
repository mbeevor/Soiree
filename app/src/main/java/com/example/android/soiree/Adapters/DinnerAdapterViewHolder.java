package com.example.android.soiree.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.soiree.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DinnerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    /** corresponding ViewHolder to power DinnerAdapter RecyclerView in MainActivity showing list of saved dinner parties
     */

    @BindView(R.id.dinner_party_label) public TextView dinnerPartyLabel;

    public DinnerAdapterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {

        }

    }
}
