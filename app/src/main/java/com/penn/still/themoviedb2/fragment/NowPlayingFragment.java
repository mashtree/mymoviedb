package com.penn.still.themoviedb2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.penn.still.themoviedb2.FragmentInterface;
import com.penn.still.themoviedb2.MainActivity;
import com.penn.still.themoviedb2.R;
import com.penn.still.themoviedb2.adapter.CardMovieAdapter;
import com.penn.still.themoviedb2.adapter.ListMovieAdapter;
import com.penn.still.themoviedb2.loader.MovieAsyncTaskLoader;
import com.penn.still.themoviedb2.models.MovieItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MovieItem>>, FragmentInterface{

    RecyclerView rvListItem;
    private List<MovieItem> listMovies;
    private Context context;
    private ListMovieAdapter adapterList;
    private CardMovieAdapter adapterCard;

    public static final String EXTRAS_PLAYING = "com.penn.still.themoviedb2.now_playing";

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        rvListItem = (RecyclerView) view.findViewById(R.id.rv_now_playing);
        rvListItem.setHasFixedSize(true);
        listMovies = new ArrayList<>();
        context = getActivity().getApplicationContext();
        adapterList = new ListMovieAdapter(context);
        adapterCard = new CardMovieAdapter(context);
        adapterList.notifyDataSetChanged();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_PLAYING,EXTRAS_PLAYING);
        //load now playing list
        //listMovies.addAll(getNowPlayingList());

        getLoaderManager().restartLoader(0,bundle, NowPlayingFragment.this);
        return view;
    }

    @Override
    public Loader<List<MovieItem>> onCreateLoader(int id, Bundle bundle) {
        String nowplaying = "";
        if(bundle!=null){
            nowplaying = bundle.getString(NowPlayingFragment.EXTRAS_PLAYING);
        }
        return new MovieAsyncTaskLoader(context, nowplaying);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieItem>> loader, List<MovieItem> data) {
        boolean isListView = MainActivity.isListView;
        if(isListView){
            showRecyclerList(data);
        }else{
            showRecyclerCard(data);
        }

        /*ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(list.get(position));
            }
        });*/
    }

    @Override
    public void refresh(){
        boolean isListView = MainActivity.isListView;
        if(isListView){
            showRecyclerList(listMovies);
        }else{
            showRecyclerCard(listMovies);
        }
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_PLAYING,EXTRAS_PLAYING);
        getLoaderManager().restartLoader(0,bundle, NowPlayingFragment.this);
    }

    private void showRecyclerList(List<MovieItem> data){
        listMovies.addAll(data);
        rvListItem.setLayoutManager(new LinearLayoutManager(context));
        adapterList.setListMovies(listMovies);
        rvListItem.setAdapter(adapterList);
    }

    private void showRecyclerCard(List<MovieItem> data){
        listMovies.addAll(data);
        rvListItem.setLayoutManager(new LinearLayoutManager(context));
        adapterCard.setListMovies(listMovies);
        rvListItem.setAdapter(adapterCard);
    }



    @Override
    public void onLoaderReset(Loader<List<MovieItem>> loader) {
        adapterList.clearData();
    }
}
