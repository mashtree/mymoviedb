package com.penn.still.themoviedb2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.penn.still.themoviedb2.models.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aisyahumar on 2/9/2018.
 */

public class MovieHelper {
    private static String DATABASE_TABLE = DatabaseContract.TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public List<MovieItem> query(){
        List<MovieItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,
                DatabaseContract.FavoritesColumns._ID+" DESC",null);
        cursor.moveToFirst();
        MovieItem movie;
        if(cursor.getCount()>0){
            do{
                movie = new MovieItem();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns._ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.TITLE)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.LANGUAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.OVERVIEW)));
                movie.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.POPULARITY)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.POSTER_PATH)));
                movie.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritesColumns.VOTE_AVERAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insert(MovieItem movie){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(DatabaseContract.FavoritesColumns.TITLE, movie.getTitle());
        initialValues.put(DatabaseContract.FavoritesColumns.LANGUAGE, movie.getOriginalLanguage());
        initialValues.put(DatabaseContract.FavoritesColumns.OVERVIEW, movie.getOverview());
        initialValues.put(DatabaseContract.FavoritesColumns.POPULARITY, movie.getPopularity());
        initialValues.put(DatabaseContract.FavoritesColumns.POSTER_PATH, movie.getPosterPath());
        initialValues.put(DatabaseContract.FavoritesColumns.RELEASE_DATE, movie.getRelease());
        initialValues.put(DatabaseContract.FavoritesColumns.VOTE_AVERAGE, movie.getVoteAverage());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public long update(MovieItem movie){
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.FavoritesColumns.TITLE, movie.getTitle());
        args.put(DatabaseContract.FavoritesColumns.LANGUAGE, movie.getOriginalLanguage());
        args.put(DatabaseContract.FavoritesColumns.OVERVIEW, movie.getOverview());
        args.put(DatabaseContract.FavoritesColumns.POPULARITY, movie.getPopularity());
        args.put(DatabaseContract.FavoritesColumns.POSTER_PATH, movie.getPosterPath());
        args.put(DatabaseContract.FavoritesColumns.RELEASE_DATE, movie.getRelease());
        args.put(DatabaseContract.FavoritesColumns.VOTE_AVERAGE, movie.getVoteAverage());

        return database.update(DATABASE_TABLE,args, DatabaseContract.FavoritesColumns._ID+
                "= '"+movie.getId()+"'",null
        );
    }

    public int delete(int id){
        return database.delete(DatabaseContract.TABLE_FAVORITE, DatabaseContract.FavoritesColumns._ID+"='"+id+"'",null);
    }

    /**
     * tambahan
     */

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,DatabaseContract.FavoritesColumns._ID+" = ? "
                ,new String[]{id}
                ,null, null, null, null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,null
                ,null
                ,null
                ,null, null
                ,DatabaseContract.FavoritesColumns._ID+" DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, DatabaseContract.FavoritesColumns._ID+"=?", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, DatabaseContract.FavoritesColumns._ID+"=?", new String[]{id});
    }
}
