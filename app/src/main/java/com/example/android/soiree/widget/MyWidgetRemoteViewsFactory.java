package com.example.android.soiree.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.soiree.R;
import com.example.android.soiree.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.android.soiree.model.Keys.RECIPE_INGREDIENTS;

public class MyWidgetRemoteViewsFactory extends RemoteViewsService {

    private Context context;
    private ArrayList<Ingredient> ingredientsList;
    private ArrayList<String> ingredients;
    SharedPreferences sharedPreferences;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListViewRemoteViewsFactory(this.getApplicationContext());
    }

    private class IngredientsListViewRemoteViewsFactory implements RemoteViewsFactory {

        IngredientsListViewRemoteViewsFactory(Context applicationContext) {
            context = applicationContext;
            ingredients = new ArrayList<>();
        }

        @Override
        public void onCreate() {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            loadIngredientsList();
        }


        @Override
        public void onDataSetChanged() {
            if (ingredients != null) {
                ingredients.clear();
            }loadIngredientsList();
        }

        @Override
        public void onDestroy() {
            if (ingredients != null) {
                ingredients.clear();
            }
        }

        @Override
        public int getCount() {
            if (ingredients != null) {
                return ingredients.size();
            } else {
                return 0;
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
            remoteViews.setTextViewText(R.id.ingredient_item_tv, ingredients.get(position));
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }


    private void loadIngredientsList() {

        ingredientsList = getArrayList(RECIPE_INGREDIENTS);
        for (Ingredient i : ingredientsList) {

            String ingredientItem = i.getIngredientItem();
            ingredients.add(ingredientItem);
        }
    }


        public ArrayList<Ingredient> getArrayList(String key){

            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, null);
            Type type = new TypeToken<ArrayList<Ingredient>>() {
            }.getType();
            return gson.fromJson(json, type);
        }

    }





