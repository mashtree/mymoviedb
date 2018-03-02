package com.penn.still.themoviedb2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.penn.still.themoviedb2.DetailMovieActivity;
import com.penn.still.themoviedb2.models.MovieItem;
import com.penn.still.themoviedb2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aisyahumar on 1/13/2018.
 */

public class CardMovieAdapter extends RecyclerView.Adapter<CardMovieAdapter.CardViewHolder> {

    private List<MovieItem> listMovies;
    private Context context;

    public CardMovieAdapter(Context c){
        context = c;
    }

    private List<MovieItem> getListMovies(){
        return listMovies;
    }

    public void setListMovies(List<MovieItem> listMovies){
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie,parent,false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final MovieItem m = getListMovies().get(position);
        int length = 100;

        holder.tvTitle.setText(m.getTitle());
        holder.tvDescription.setText(m.getOverview().length()>length?m.getOverview().substring(0,length)+"...":m.getOverview());
        holder.tvRelease.setText(MoviesAdapter.longFormatDate(getListMovies().get(position).getRelease()));
        String url = "http://image.tmdb.org/t/p/w185/"+m.getPosterPath();

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_filter_b_and_w_black_24dp)
                .into(holder.imgPoster);

        /**
         * button fav and share
         */

        holder.btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Favorite "+m.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Share "+m.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDetail = new Intent(context,DetailMovieActivity.class);
                intentToDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE,m);
                // additional flag, for calling from outside activity context
                intentToDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentToDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_item_poster) ImageView imgPoster;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_desc) TextView tvDescription;
        @BindView(R.id.tv_release) TextView tvRelease;
        @BindView(R.id.btn_set_favorite) Button btFavorite;
        @BindView(R.id.btn_set_share) Button btShare;
        public CardViewHolder(View v) {
            super(v);
            //imgPoster = (ImageView) v.findViewById(R.id.img_item_poster);
            //tvTitle = (TextView) v.findViewById(R.id.tv_title);
            //tvDescription = (TextView) v.findViewById(R.id.tv_desc);
            //tvRelease = (TextView) v.findViewById(R.id.tv_release);
            ButterKnife.bind(this,v);
        }
    }
}
