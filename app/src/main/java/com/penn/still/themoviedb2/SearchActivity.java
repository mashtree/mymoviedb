package com.penn.still.themoviedb2;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.penn.still.themoviedb2.adapter.MoviesAdapter;
import com.penn.still.themoviedb2.loader.MovieAsyncTaskLoader;
import com.penn.still.themoviedb2.models.MovieItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieItem>> {

    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.lv_result)
    ListView lvResult;
    @BindView(R.id.progress_bar)
    ProgressBar pbLoad;
    private MoviesAdapter adapter;
    public static final String EXTRAS_KEYWORD = "keyword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Movie's Catalogue");

        initialize();

        String keyword = edSearch.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_KEYWORD,keyword);

        //getLoaderManager().initLoader(0,bundle,this);
    }

    private void initialize(){
        adapter = new MoviesAdapter(this);
        adapter.notifyDataSetChanged();
        ButterKnife.bind(this);
        //edSearch = (EditText) findViewById(R.id.ed_search);
        //btSearch = (Button) findViewById(R.id.bt_search);
        //lvResult = (ListView) findViewById(R.id.lv_result);
        lvResult.setAdapter(adapter);
        lvResult.setVisibility(View.INVISIBLE);
        //pbLoad = (ProgressBar) findViewById(R.id.progress_bar);
        pbLoad.setVisibility(View.GONE);

        btSearch.setOnClickListener(myListener);
    }

    @Override
    public Loader<List<MovieItem>> onCreateLoader(int id, Bundle bundle) {
        String keyword = "";
        if(bundle!=null){
            keyword = bundle.getString(SearchActivity.EXTRAS_KEYWORD);
        }
        return new MovieAsyncTaskLoader(this, keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieItem>> loader, List<MovieItem> movieItems) {
        adapter.setmData(movieItems);
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
            //getLoaderManager().restartLoader(0,bundle, SearchActivity.this);
            pbLoad.setVisibility(View.GONE);
            lvResult.setVisibility(View.VISIBLE);
        }
    };
}
