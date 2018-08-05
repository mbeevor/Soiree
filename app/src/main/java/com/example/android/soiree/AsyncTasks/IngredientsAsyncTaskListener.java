package com.example.android.soiree.AsyncTasks;

import com.example.android.soiree.model.Ingredient;

import java.util.ArrayList;

public interface IngredientsAsyncTaskListener {
    void onTaskComplete(ArrayList<Ingredient> list);
}
