package com.example.android.soiree.model;

import android.net.Uri;

import com.example.android.soiree.BuildConfig;

public class Keys {

    public static final String RECIPES = "recipes";

    // app bar keys
    public static final String COURSE = "course";

    // content authority constants
    public static final String CONTENT_AUTHORITY = "com.example.android.soiree";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // API constants
    public static final String BASE_URL = "http://food2fork.com/api/search?key=";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String QUERY = "query";
    public static final String SEARCH_QUERY = "&q=";

    public static final String RECIPE_URL = "http://food2fork.com/api/get?key=";
    public static final String SEARCH_RECIPE_ID = "&rId=";


}
