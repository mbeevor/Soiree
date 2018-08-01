package com.example.android.soiree.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.soiree.data.DinnerContract.RecipeEntry.MAIN_ID;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.METHOD_URL;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.PUDDING_ID;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_ID;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_IMAGE;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_NAME;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.RECIPE_RANK;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.STARTER_ID;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.TABLE_DINNER;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.TABLE_MAIN;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.TABLE_PUDDING;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry.TABLE_STARTER;
import static com.example.android.soiree.data.DinnerContract.RecipeEntry._ID;

public class DBHandler extends SQLiteOpenHelper {

    //Database Version and name
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dinnerPartyList.db";

    private static final String SQL_CREATE_STARTER_TABLE =

            "CREATE TABLE " + TABLE_STARTER + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RECIPE_ID + " TEXT, "
                    + RECIPE_IMAGE + " TEXT, "
                    + METHOD_URL + " TEXT, "
                    + RECIPE_NAME + " TEXT, "
                    + RECIPE_RANK + " TEXT);";

    private static final String SQL_CREATE_MAIN_TABLE =

            "CREATE TABLE " + TABLE_MAIN + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RECIPE_ID + " TEXT, "
                    + RECIPE_IMAGE + " TEXT, "
                    + METHOD_URL + " TEXT, "
                    + RECIPE_NAME + " TEXT, "
                    + RECIPE_RANK + " TEXT);";

    private static final String SQL_CREATE_PUDDING_TABLE =

            "CREATE TABLE " + TABLE_PUDDING + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RECIPE_ID + " TEXT, "
                    + RECIPE_IMAGE + " TEXT, "
                    + METHOD_URL + " TEXT, "
                    + RECIPE_NAME + " TEXT, "
                    + RECIPE_RANK + " TEXT);";

    private static final String SQL_CREATE_DINNER_TABLE =

            "CREATE TABLE " + TABLE_DINNER + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STARTER_ID + " TEXT, "
                    + MAIN_ID + " TEXT, "
                    + PUDDING_ID + " TEXT);";

    // default constructor
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STARTER_TABLE);
        db.execSQL(SQL_CREATE_MAIN_TABLE);
        db.execSQL(SQL_CREATE_PUDDING_TABLE);
        db.execSQL(SQL_CREATE_DINNER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STARTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUDDING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DINNER);
        // then, create new tables
        onCreate(db);
    }
}
