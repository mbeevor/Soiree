package com.example.android.soiree.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soiree.R;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapterViewHolder>{

    /** adapter for RecyclerView in MainActivity showing list of saved dinner parties
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
}
