package uk.co.beevorwhite.soiree.Adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import static uk.co.beevorwhite.soiree.data.DinnerContract.DinnerEntry._ID;

public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Cursor cursor;
    private boolean dataValid;
    private int rowId;

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    public CursorRecyclerViewAdapter(Cursor data) {
        setHasStableIds(true);
        swapCursor(data);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        if (!dataValid) {
            throw new IllegalArgumentException("Error - cursor not valid");
        }

        if (!cursor.moveToPosition(position)) {
            throw new IllegalArgumentException("Error - couldn't move cursor to position " + position);
        }

        onBindViewHolder(holder, cursor);
    }


    @Override
    public int getItemCount() {

        if (dataValid) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {

        if (!dataValid) {
            throw new IllegalArgumentException("Error - cursor not valid");
        }

        if (!cursor.moveToPosition(position)) {
            throw new IllegalArgumentException("Error - couldn't move cursor to position " + position);
        }
        return cursor.getLong(rowId);
    }


    public void swapCursor(Cursor newCursor) {

        if (cursor == newCursor) {
            return;
        }
        if (newCursor != null) {
            cursor = newCursor;
            rowId = cursor.getColumnIndexOrThrow(_ID);
            dataValid = true;
            notifyDataSetChanged();
        } else {

            notifyItemRangeRemoved(0, getItemCount());
            cursor = null;
            rowId = -1;
            dataValid = false;
        }
    }
}
