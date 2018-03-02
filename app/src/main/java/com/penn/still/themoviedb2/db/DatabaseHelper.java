package com.penn.still.themoviedb2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aisyahumar on 2/9/2018.
 */

public class  DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbmovieapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format(
            "CREATE TABLE %s " + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE,
            DatabaseContract.FavoritesColumns._ID,
            DatabaseContract.FavoritesColumns.TITLE,
            DatabaseContract.FavoritesColumns.LANGUAGE,
            DatabaseContract.FavoritesColumns.OVERVIEW,
            DatabaseContract.FavoritesColumns.POPULARITY,
            DatabaseContract.FavoritesColumns.RELEASE_DATE,
            DatabaseContract.FavoritesColumns.VOTE_AVERAGE,
            DatabaseContract.FavoritesColumns.POSTER_PATH
    );

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_FAVORITE);
        onCreate(db);
    }
}
