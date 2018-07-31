package com.example.android.soiree.AsyncTasks;

import com.example.android.soiree.model.Recipe;

import java.util.ArrayList;

public interface AsyncTaskListener {

    void onTaskComplete(ArrayList<Recipe> list);

}
