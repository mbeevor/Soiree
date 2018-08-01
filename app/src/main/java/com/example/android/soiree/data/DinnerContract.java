package com.example.android.soiree.data;

import android.provider.BaseColumns;

public class DinnerContract {

    // empty constructor
    private DinnerContract() {
    }

    public static final class RecipeEntry implements BaseColumns {

        // table names
        public static final String TABLE_STARTER = "starter";
        public static final String TABLE_MAIN = "main";
        public static final String TABLE_PUDDING = "pudding";
        public static final String TABLE_DINNER = "dinner";

        // unique ID for each item in any table
        public static final String _ID = BaseColumns._ID;

        // dinner database column
        public static final String STARTER_ID = "starter_id";
        public static final String MAIN_ID = "main_id";
        public static final String PUDDING_ID = "pudding_id";

        // database columns - same as API
        public static final String RECIPE_ID = "recipe_id";
        public static final String RECIPE_IMAGE = "image_url";
        public static final String METHOD_URL = "source_url";
        public static final String RECIPE_NAME = "title";
        public static final String RECIPE_RANK = "social_rank";

    }
}
