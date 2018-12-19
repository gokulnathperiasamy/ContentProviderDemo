package com.kpgn.appone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kpgn.common.ApplicationConstant;

public class CustomDatabaseHelper extends SQLiteOpenHelper {

    static final String CREATE_DB_TABLE = " CREATE TABLE " + ApplicationConstant.TABLE_NAME
            + "("
            + ApplicationConstant.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ApplicationConstant.COLUMN_EMAIL + " TEXT NOT NULL"
            + ");";

    public CustomDatabaseHelper(Context context) {
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