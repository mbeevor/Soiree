package com.example.android.soiree.Adapters;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.soiree.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;

public class DinnerCursorAdapter extends CursorRecyclerViewAdapter<DinnerCursorAdapter.DinnerCursorViewHolder> {

    private OnItemClickHandler clickHandler;

    @Override
    public void onBindViewHolder(DinnerCursorViewHolder holder, Cursor cursor) {

        DinnerCursorViewHolder viewHolder = holder;
        String dinnerPartyName = cursor.getString(cursor.getColumnIndex(DINNER_NAME));
        viewHolder.dinnerPartyTextView.setText(dinnerPartyName);

    }

    public DinnerCursorAdapter(Cursor data, OnItemClickHandler onItemClickHandler) {
        super(data);
        clickHandler = onItemClickHandler;
    }

    @NonNull
    @Override
    public DinnerCursorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.item_dinner_party;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutForListItem, parent, false);
        DinnerCursorViewHolder viewHolder = new DinnerCursorViewHolder(itemView);
        return viewHolder;

    }


    public class DinnerCursorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.dinner_party_label) public TextView dinnerPartyTextView;

        public DinnerCursorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                clickHandler.onItemClick(v, position);
            }
        }
    }

}
