package com.penn.still.themoviedb2.fragment;


import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.penn.still.themoviedb2.R;
import com.penn.still.themoviedb2.SearchActivity;
import com.penn.still.themoviedb2.adapter.MoviesAdapter;
import com.penn.still.themoviedb2.loader.MovieAsyncTaskLoader;
import com.penn.still.themoviedb2.models.MovieItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MovieItem>>{

    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.lv_result)
    ListView lvResult;
    @BindView(R.id.progress_bar)
    ProgressBar pbLoad;
    private MoviesAdapter adapter;
    private Context context;
    private View view;

    public static final String EXTRAS_KEYWORD = "com.penn.still.themoviedb2.keyword";
    public static final String EXTRAS_SEARCH = "com.penn.still.themoviedb2.search";
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        context = getActivity().getApplicationContext();
        initialize();

        String keyword = edSearch.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_KEYWORD,"The Raid");
        //getLoaderManager().restartLoader(0,bundle, SearchFragment.this);
        //lvResult.setVisibility(View.VISIBLE);
        return view;
    }

    private void initialize() {

        adapter = new MoviesAdapter(context);
        adapter.notifyDataSetChanged();
        ButterKnife.bind(this, view);
        lvResult.setAdapter(adapter);
        lvResult.setVisibility(View.INVISIBLE);
        //pbLoad = (ProgressBar) findViewById(R.id.progress_bar);
        pbLoad.setVisibility(View.GONE);

        btSearch.setOnClickListener(myListener);
    }

    @Override
    public Loader<List<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        String keyword = "";
        if(bundle!=null){
            keyword = bundle.getString(SearchFragment.EXTRAS_KEYWORD);
        }
        return new MovieAsyncTaskLoader(context, keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieItem>> loader, List<MovieItem> data) {
        adapter.setmData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<MovieItem>> loader) {
        adapter.clearData();
    }

    View.OnClickListener myListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            String keyword = edSearch.getText().toString();

            //jika edit text kosong, do nothing
            if(TextUtils.isEmpty(keyword)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_KEYWORD,keyword);
            pbLoad.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(0,bundle, SearchFragment.this);
            pbLoad.setVisibility(View.GONE);
            lvResult.setVisibility(View.VISIBLE);
        }
    };
}
