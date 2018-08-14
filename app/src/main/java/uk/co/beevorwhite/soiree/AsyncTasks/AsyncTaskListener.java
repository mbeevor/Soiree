package uk.co.beevorwhite.soiree.AsyncTasks;

import uk.co.beevorwhite.soiree.model.Recipe;

import java.util.ArrayList;

public interface AsyncTaskListener {

    void onTaskComplete(ArrayList<Recipe> list);

}
