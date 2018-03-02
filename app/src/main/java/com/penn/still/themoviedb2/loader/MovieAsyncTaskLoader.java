package com.penn.still.themoviedb2.loader;

import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.penn.still.themoviedb2.BuildConfig;
import com.penn.still.themoviedb2.db.DatabaseContract;
import com.penn.still.themoviedb2.fragment.FavoriteFragment;
import com.penn.still.themoviedb2.fragment.NowPlayingFragment;
import com.penn.still.themoviedb2.fragment.SearchFragment;
import com.penn.still.themoviedb2.fragment.UpcomingFragment;
import com.penn.still.themoviedb2.models.MovieItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by triyono on 1/2/2018.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<List<MovieItem>> {
    private List<MovieItem> mData;
    private boolean mHasResult = false;
    private String keyword;
    private String action;
    private static final Map<String, String> urls = new HashMap<>();

    public MovieAsyncTaskLoader(Context context, String keyword) {
        super(context);
        onContentChanged();
        if(keyword.equals(NowPlayingFragment.EXTRAS_PLAYING)){
            action = NowPlayingFragment.EXTRAS_PLAYING;
        }else if(keyword.equals(UpcomingFragment.EXTRAS_UPCOMING)){
            action = UpcomingFragment.EXTRAS_UPCOMING;
        }else{
            action = SearchFragment.EXTRAS_KEYWORD;
        }

        this.keyword = keyword;

        urls.put("SEARCH","https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.OPEN_WEATHER_MAP_API_KEY +"&language=en-US&query=");
        urls.put("UPCOMING","https://api.themoviedb.org/3/movie/upcoming?api_key="+ BuildConfig.OPEN_WEATHER_MAP_API_KEY+"&language=en-US");
        urls.put("NOW_PLAYING","https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.OPEN_WEATHER_MAP_API_KEY+"&language=en-US");
    }

    @Override
    protected void onStartLoading(){
        if(takeContentChanged()){
            forceLoad();
        }else if(mHasResult){
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(final List<MovieItem> data){
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if(mHasResult){
            //onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(List<MovieItem> mData) {
    }

    @Override
    public List<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final List<MovieItem> movies = new ArrayList<>();
        //String url = "https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.OPEN_WEATHER_MAP_API_KEY +"&language=en-US&query="+keyword;
        String url = "";
        if(action.equals(SearchFragment.EXTRAS_KEYWORD)){
            url = urls.get("SEARCH")+keyword;
        }else if(action.equals(NowPlayingFragment.EXTRAS_PLAYING)){
            url = urls.get("NOW_PLAYING");
        }else if(action.equals(UpcomingFragment.EXTRAS_UPCOMING)){
            url = urls.get("UPCOMING");
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();

                setUseSynchronousMode(true);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    System.out.println(result);
                    //Log.d("Movie Async", result);
                    JSONObject response = new JSONObject(new String(responseBody));
                    JSONArray list = response.getJSONArray("results");
                    for(int i = 0;i<list.length();i++){
                        JSONObject movie = list.getJSONObject(i);

                        MovieItem movieItem = new MovieItem(movie);
                        movies.add(movieItem);
                    }
                }catch(Exception e){
                    Log.e("Movie Model", "onSuccess: ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movies;
    }
}