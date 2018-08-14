package uk.co.beevorwhite.soiree.model;

import uk.co.beevorwhite.soiree.BuildConfig;

public class Keys {

    public static final String RECIPES = "recipes";
    public static final String RECIPE = "recipe";


    // app bar keys
    public static final String COURSE = "course";

    // API constants
    public static final String BASE_URL = "http://food2fork.com/api/search?key=";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String QUERY = "query";
    public static final String SEARCH_QUERY = "&q=";

    public static final String RECIPE_URL = "http://food2fork.com/api/get?key=";
    public static final String SEARCH_RECIPE_ID = "&rId=";

    // API results
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_IMAGE = "image_url";
    public static final String METHOD_URL = "source_url";
    public static final String RECIPE_NAME = "title";
    public static final String RECIPE_RANK = "social_rank";
    public static final String RECIPE_INGREDIENTS = "ingredients";

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
    public static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

}
