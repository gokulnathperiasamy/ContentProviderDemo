package com.kpgn.appone;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.kpgn.common.ApplicationConstant;

import java.util.HashMap;

public class CustomContentProvider extends ContentProvider {


    static final Uri CONTENT_URI = Uri.parse(ApplicationConstant.PROVIDER_URL);

    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ApplicationConstant.PROVIDER_NAME, ApplicationConstant.PROVIDER_PATH, ApplicationConstant.URI_CODE);
        uriMatcher.addURI(ApplicationConstant.PROVIDER_NAME, ApplicationConstant.PROVIDER_PATH + "/*", ApplicationConstant.URI_CODE);
    }

    private SQLiteDatabase db;

    static final String CREATE_DB_TABLE = " CREATE TABLE " + ApplicationConstant.TABLE_NAME
            + "("
            + ApplicationConstant.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ApplicationConstant.COLUMN_EMAIL + " TEXT NOT NULL"
            + ");";

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ApplicationConstant.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ApplicationConstant.URI_CODE:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder.equals("")) {
            sortOrder = ApplicationConstant.COLUMN_EMAIL;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ApplicationConstant.URI_CODE:
                return ApplicationConstant.URI_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long rowID = db.insert(ApplicationConstant.TABLE_NAME, "", values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case ApplicationConstant.URI_CODE:
                count = db.delete(ApplicationConstant.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case ApplicationConstant.URI_CODE:
                count = db.update(ApplicationConstant.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, ApplicationConstant.DATABASE_NAME, null, ApplicationConstant.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ApplicationConstant.TABLE_NAME);
            onCreate(db);
        }
    }
}