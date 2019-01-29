package uk.co.beevorwhite.soiree.model;

import uk.co.beevorwhite.soiree.BuildConfig;

public class Keys {

    public static final String RECIPES = "recipes";
    public static final String RECIPE = "recipe";


    // app bar keys
    public static final String COURSE = "course";

    // API constants
    public static final String BASE_URL = "https://api.edamam.com/search";
    public static final String SEARCH_QUERY = "?q=";
    public static final String QUERY = "query";
    public static final String APP_ID = "&app_id=";
    public static final String API_ID = BuildConfig.API_ID;
    public static final String APP_KEY = "&app_key=";
    public static final String API_KEY = BuildConfig.API_KEY;

    // API results
    public static final String HITS = "hits";
    public static final String RECIPE_IMAGE = "image";
    public static final String METHOD_URL = "url";
    public static final String RECIPE_NAME = "label";
    public static final String RECIPE_PORTIONS = "yield";
    public static final String RECIPE_INGREDIENTS = "ingredientLines";

    // default values when creating new dinner
    public static final String URI = "uri";
    public static final String DINNER = "dinner";
    public static final String STARTER = "starter";
    public static final String MAIN = "main";
    public static final String PUDDING = "pudding";
    public static final String DEFAULT_VALUE = "default_value";

    // static integers for course selected
    public static final int COURSE_UNKNOWN = 0;
    public static final int COURSE_STARTER = 1;
    public static final int COURSE_MAIN = 2;
    public static final int COURSE_PUDDING = 3;

    // widget
    public static final String ACTION_UPDATE_WIDGET = "uk.co.beevorwhite.soiree.action.update.widget";

    //AdMob
    public static final String ADMOB_APP_ID = "ca-app-pub-7636876377712969~9704600793";

}
