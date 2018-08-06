package com.example.android.soiree.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.soiree.data.DinnerContract.DinnerEntry.DINNER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.GUEST_LIST;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_IMAGE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.MAIN_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_IMAGE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.PUDDING_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_ID;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_IMAGE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_NAME;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_NOTES;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.STARTER_URI;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.TABLE_DINNER;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry._ID;

public class DBHandler extends SQLiteOpenHelper {

    //Database Version and name
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dinnerParty.db";

    private static final String SQL_CREATE_DINNER_TABLE =

            "CREATE TABLE " + TABLE_DINNER + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DINNER_NAME + " TEXT NOT NULL, "
                    + STARTER_ID + " TEXT, "
                    + STARTER_NAME + " TEXT, "
                    + STARTER_URI + " TEXT, "
                    + STARTER_IMAGE + " TEXT, "
                    + STARTER_NOTES + " TEXT, "
                    + MAIN_ID + " TEXT, "
                    + MAIN_NAME + " TEXT, "
                    + MAIN_URI + " TEXT, "
                    + MAIN_IMAGE + " TEXT, "
                    + MAIN_NOTES + " TEXT, "
                    + PUDDING_ID + " TEXT, "
                    + PUDDING_NAME + " TEXT, "
                    + PUDDING_URI + " TEXT, "
                    + PUDDING_IMAGE + " TEXT, "
                    + PUDDING_NOTES + " TEXT, "
                    + GUEST_LIST + " TEXT);";

    // default constructor
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(SQL_CREATE_DINNER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DINNER);
        // then, create new table
        onCreate(db);
    }
}
