package com.example.android.soiree.AsyncTasks;

import com.example.android.soiree.model.Ingredients;

import java.util.ArrayList;

public interface IngredientsAsyncTaskListener {
    void onTaskComplete(ArrayList<Ingredients> list);
}
