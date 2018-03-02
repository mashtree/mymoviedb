package com.penn.still.themoviedb2.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.penn.still.themoviedb2.R;
import com.penn.still.themoviedb2.adapter.CardMovieAdapter;
import com.penn.still.themoviedb2.adapter.ListMovieAdapter;
import com.penn.still.themoviedb2.adapter.MoviesAdapter;
import com.penn.still.themoviedb2.db.DatabaseContract;
import com.penn.still.themoviedb2.models.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    RecyclerView rvListItem;
    private Cursor listMovies;
    private Cursor list;
    private Context context;
    private ListMovieAdapter adapterList;

    public static final String EXTRAS_FAVORITE = "com.penn.still.themoviedb2.favorite";

    public FavoriteFragment(){
        //need empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvListItem = (RecyclerView) view.findViewById(R.id.rv_favorite);
        rvListItem.setHasFixedSize(true);

        context = getActivity().getApplicationContext();
        adapterList = new ListMovieAdapter(context);
        adapterList.setFlag(1);
        adapterList.notifyDataSetChanged();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FAVORITE,EXTRAS_FAVORITE);

        //adapterList.setListMovies(listMovies);
        adapterList.setListMovies(list);
        rvListItem.setAdapter(adapterList);

        getLoaderManager().restartLoader(0,bundle, FavoriteFragment.this);

        new LoadMoviesAsync().execute();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Cursor cursor = getContext().getContentResolver().query(DatabaseContract.CONTENT_URI, null, null, null, null);
            //Log.d("FavoriteFragment", "doInBackground: "+cursor.toString());
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor){
            super.onPostExecute(cursor);
            list = cursor;
            Log.d(FavoriteFragment.class.getSimpleName(), "onPostExecute: "+cursor.getCount());
            adapterList.setListMovies(list);
            adapterList.notifyDataSetChanged();
            showRecyclerList(cursor);
        }
    }

    private void showRecyclerList(Cursor data){
        listMovies = data;
        rvListItem.setLayoutManager(new LinearLayoutManager(context));
        adapterList.setListMovies(listMovies);
        rvListItem.setAdapter(adapterList);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
