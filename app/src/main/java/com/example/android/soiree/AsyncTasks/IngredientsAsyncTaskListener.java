package com.example.android.soiree.AsyncTasks;

import com.example.android.soiree.model.RecipeDetail;

import java.util.ArrayList;

public interface IngredientsAsyncTaskListener {
    void onTaskComplete(ArrayList<RecipeDetail> list);
}
