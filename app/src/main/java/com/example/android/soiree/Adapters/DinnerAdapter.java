package com.example.android.soiree.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.soiree.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.DinnerAdapterViewHolder>{

    /** adapter for RecyclerView in com.example.android.soiree.MainActivity showing list of saved dinner parties
     */

    private Context context;
    private DinnerPartyClickListener dinnerPartyClickListener;

    public interface DinnerPartyClickListener {
        void onDinnerSelected(int position);
    }

    public DinnerAdapter(Context applicationContext, DinnerPartyClickListener listener) {
        context = applicationContext;
        dinnerPartyClickListener = listener;
    }

    @NonNull
    @Override
    public DinnerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.item_dinner_party;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutForListItem, parent, false);
        DinnerAdapterViewHolder viewHolder = new DinnerAdapterViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DinnerAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DinnerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /** corresponding ViewHolder to power DinnerAdapter RecyclerView in com.example.android.soiree.MainActivity showing list of saved dinner parties
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

}


