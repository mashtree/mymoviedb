package com.penn.still.themoviedb2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.penn.still.themoviedb2.adapter.MoviesAdapter;
import com.penn.still.themoviedb2.db.DatabaseContract;
import com.penn.still.themoviedb2.db.MovieHelper;
import com.penn.still.themoviedb2.models.MovieItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by triyono on 1/1/2018.
 */

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    @BindView(R.id.iv_dposter) ImageView ivPoster;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_user_score) TextView tvUserScore;
    @BindView(R.id.tv_lang) TextView tvLang;
    @BindView(R.id.tv_release) TextView tvRelease;
    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.imgbtn_favorite) ImageButton imgBtnFavorite;
    @BindView(R.id.activity_detail) LinearLayout layout;
    private final String url = "http://image.tmdb.org/t/p/w500/";
    private String imgName;
    private boolean isFavorite = false;
    private MovieItem movie;
    private MovieHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        setTitle("Movie's Information");
        MovieItem movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        helper = new MovieHelper(this);
        helper.open();
        this.movie = movie;
        initialize();
        view(movie);
        Cursor cursor = helper.queryByIdProvider(Integer.toString(movie.getId()));
        Log.d("DetailMovieActivity", "onCreate: "+cursor.getCount());
        if(cursor.getCount()>0){
            isFavorite = true;
            imgBtnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    private void initialize(){
        ButterKnife.bind(this);
        tvUserScore.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stars_black_24dp,0,0,0);
        tvLang.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_language_black_24dp,0,0,0);
        tvRelease.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_date_range_black_24dp,0,0,0);
        ivPoster.setOnClickListener(thisListener);
        imgBtnFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        imgBtnFavorite.setOnClickListener(thisListener);
        imgName = "";
    }

    View.OnClickListener thisListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.iv_dposter){
                final String url = "http://image.tmdb.org/t/p/original/"+imgName;
                Intent posterIntent = new Intent(DetailMovieActivity.this, PosterActivity.class);
                posterIntent.putExtra(PosterActivity.EXTRA_POSTER,url);
                startActivity(posterIntent);
            }else if(view.getId()==R.id.imgbtn_favorite){
                //Toast.makeText(DetailMovieActivity.this,"test",Toast.LENGTH_LONG).show();
                if(!isFavorite) {
                    imgBtnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    isFavorite = true;
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.FavoritesColumns._ID, movie.getId());
                    values.put(DatabaseContract.FavoritesColumns.TITLE, movie.getTitle());
                    values.put(DatabaseContract.FavoritesColumns.VOTE_AVERAGE, movie.getVoteAverage());
                    values.put(DatabaseContract.FavoritesColumns.RELEASE_DATE, movie.getRelease());
                    values.put(DatabaseContract.FavoritesColumns.POSTER_PATH, movie.getPosterPath());
                    values.put(DatabaseContract.FavoritesColumns.POPULARITY, movie.getPopularity());
                    values.put(DatabaseContract.FavoritesColumns.OVERVIEW, movie.getOverview());
                    values.put(DatabaseContract.FavoritesColumns.LANGUAGE, movie.getOriginalLanguage());
                    getContentResolver().insert(DatabaseContract.CONTENT_URI, values);
                    //finish();
                    Toast.makeText(DetailMovieActivity.this,"add "+movie.getTitle()+" to favorite",Toast.LENGTH_LONG).show();
                }else{
                    helper.delete(movie.getId());
                    imgBtnFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    isFavorite = false;
                    Toast.makeText(DetailMovieActivity.this,"delete "+movie.getTitle()+" from favorite",Toast.LENGTH_LONG).show();
                }
            }

        }
    };

    private void view(MovieItem movie){
        Picasso.with(this)
                .load(url+movie.getPosterPath())
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_filter_b_and_w_black_24dp)
                .into(ivPoster);
        tvTitle.setText(movie.getTitle().toUpperCase());
        tvUserScore.setText(" "+String.valueOf(movie.getVoteAverage()));
        tvLang.setText(" "+movie.getOriginalLanguage());
        tvRelease.setText(" "+ MoviesAdapter.longFormatDate(movie.getRelease()));
        //tvRelease.setText(" "+movie.getRelease());
        tvOverview.setText(movie.getOverview());
        imgName = movie.getPosterPath();
        //Toast.makeText(this, "id="+movie.getId(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        finish();
    }
}
