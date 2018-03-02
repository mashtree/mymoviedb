package uxt6.psu.com.myfavoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uxt6.psu.com.myfavoritemovie.R;
import uxt6.psu.com.myfavoritemovie.db.DatabaseContract;

/**
 * Created by aisyahumar on 2/18/2018.
 */

public class FavoriteMoviesAdapter extends CursorAdapter {

    Context context;
    public FavoriteMoviesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movies, viewGroup,false);
        return view;
    }

    @Override
    public Cursor getCursor(){
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if(cursor!=null){
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvRelease = (TextView) view.findViewById(R.id.tv_release);
            TextView tvOverview = (TextView) view.findViewById(R.id.tv_desc);
            ImageView imgPoster = (ImageView) view.findViewById(R.id.iv_poster);
            tvTitle.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavoritesColumns.TITLE));
            tvRelease.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavoritesColumns.RELEASE_DATE));
            tvOverview.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavoritesColumns.OVERVIEW));
            String url = "http://image.tmdb.org/t/p/w185/"+DatabaseContract.getColumnString(cursor,DatabaseContract.FavoritesColumns.POSTER_PATH);
            //Log.d("FavoriteMoviesAdapter", "bindView: "+url);
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_filter_b_and_w_black_24dp)
                    .error(R.drawable.ic_filter_b_and_w_black_24dp)
                    .into(imgPoster);
        }
    }
}