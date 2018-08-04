package com.example.android.soiree.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.soiree.data.DinnerContract.CONTENT_AUTHORITY;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.CONTENT_ITEM_TYPE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.CONTENT_LIST_TYPE;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry.TABLE_DINNER;
import static com.example.android.soiree.data.DinnerContract.DinnerEntry._ID;

public class DinnerProvider extends ContentProvider {

    private static final int DINNERS = 100;
    private static final int DINNER_ID = 101;
    private static final UriMatcher URI_MATCHER;

    // static initialiser run each time anything is called from database

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, DinnerContract.PATH_DINNER, DINNERS);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, DinnerContract.PATH_DINNER + "#", DINNER_ID);

    }

    private DBHandler dbHandler;

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = dbHandler.getReadableDatabase();

        // create cursor to read database
        Cursor cursor;

        int match = URI_MATCHER.match(uri);
        switch (match) {

            case DINNERS:
                cursor = database.query(TABLE_DINNER, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case DINNER_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TABLE_DINNER, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Error loading database " + uri.toString());
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case DINNERS:
                return CONTENT_LIST_TYPE;
            case DINNER_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri.toString() + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case DINNERS:
                return insertDinner(uri, values);
            default:
                throw new IllegalArgumentException("insertion not supported for " + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // number of rows to be deleted
        int rowsDeleted;

        SQLiteDatabase database = dbHandler.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri, null);


        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case DINNERS:
                rowsDeleted = database.delete(TABLE_DINNER, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case DINNER_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TABLE_DINNER, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;


            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri.toString());
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private Uri insertDinner(Uri uri, ContentValues values) {

        SQLiteDatabase database = dbHandler.getWritableDatabase();
        long id = database.insert(TABLE_DINNER, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }


}
