package uxt6.psu.com.myfavoritemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by aisyahumar on 2/18/2018.
 */

public class DatabaseContract {

    public static String TABLE_FAVORITE = "favorites";

    public static final class FavoritesColumns implements BaseColumns {
        //Movie title
        public static String TITLE = "title";
        //Movie vote average
        public static String VOTE_AVERAGE= "vote_avg";
        //Movie original language
        public static String LANGUAGE = "lang";
        //movie release date
        public static String RELEASE_DATE = "rel_date";
        //movie overview
        public static String OVERVIEW = "overview";
        //movie popularity
        public static String POPULARITY = "popularity";
        //movie posterpath
        public static String POSTER_PATH = "poster_path";
    }

    public static final String AUTHORITY = "com.penn.still.themoviedb2";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName){
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}