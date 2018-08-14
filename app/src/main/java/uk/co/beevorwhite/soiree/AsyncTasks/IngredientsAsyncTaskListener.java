package uk.co.beevorwhite.soiree.AsyncTasks;

import uk.co.beevorwhite.soiree.model.Ingredient;

import java.util.ArrayList;

public interface IngredientsAsyncTaskListener {
    void onTaskComplete(ArrayList<Ingredient> list);
}
