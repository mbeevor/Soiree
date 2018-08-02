package com.example.android.soiree.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DinnerContract {

    // content authority constants
    public static final String CONTENT_AUTHORITY = "com.example.android.soiree";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DINNER = "dinner";


    // empty constructor
    private DinnerContract() {
    }

    public static final class DinnerEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DINNER);

        // MIME type for directory of items
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DINNER;
        // MIME type for single item
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DINNER;

        // table names
        public static final String TABLE_STARTER = "starter";
        public static final String TABLE_MAIN = "main";
        public static final String TABLE_PUDDING = "pudding";
        public static final String TABLE_DINNER = "dinner";

        // common column name
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
