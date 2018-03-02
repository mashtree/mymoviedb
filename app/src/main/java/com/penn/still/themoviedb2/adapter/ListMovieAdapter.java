package com.penn.still.themoviedb2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Movie;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.penn.still.themoviedb2.DetailMovieActivity;
import com.penn.still.themoviedb2.FragmentInterface;
import com.penn.still.themoviedb2.R;
import com.penn.still.themoviedb2.models.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aisyahumar on 1/13/2018.
 */

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.CategoryViewHolder>
implements FragmentInterface{

    private List<MovieItem> listMovies;
    private Cursor listMoviesCursor;
    private Context context;
    private int flag = 0;

    public ListMovieAdapter(Context context){
        this.context = context;
    }

    List<MovieItem> getListMovies(){
        return listMovies;
    }
    Cursor getListMoviesCursor(){
        return listMoviesCursor;
    }

    public void setFlag(int flag){this.flag=flag;}

    public void setListMovies(List<MovieItem> listMovies){
        this.listMovies = listMovies;
    }
    public void setListMovies(Cursor listMovies){
        this.listMoviesCursor = listMovies;
        notifyDataSetChanged();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie,parent,false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        Log.d("ListMovieAdapter", "onBindViewHolder: flag "+flag);
        String url = "";
        if(flag==0){
            //Log.d("ListMovieAdapter", "onBindViewHolder: ");
            holder.tvTitle.setText(getListMovies().get(position).getTitle());
            holder.tvDescription.setText(getListMovies().get(position).getOverview().substring(0,40)+"...");
            holder.tvRelease.setText(MoviesAdapter.longFormatDate(getListMovies().get(position).getRelease()));
            url = "http://image.tmdb.org/t/p/w185/"+getListMovies().get(position).getPosterPath();

        }else if(flag==1){
            final MovieItem item = getItem(position);
            Log.d("ListMovieAdapter", "onBindViewHolder: "+item.getTitle());
            holder.tvTitle.setText(item.getTitle());
            holder.tvDescription.setText(item.getOverview().substring(0,40)+"...");
            holder.tvRelease.setText(MoviesAdapter.longFormatDate(item.getRelease()));
            url = "http://image.tmdb.org/t/p/w185/"+item.getPosterPath();

        }
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_filter_b_and_w_black_24dp)
                .into(holder.imgPoster);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDetail = new Intent(context,DetailMovieActivity.class);
                if(flag==0){
                    intentToDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE,listMovies.get(position));
                }else{
                    intentToDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE,getItem(position));
                }

                // additional flag, for calling from outside activity context
                intentToDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentToDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(flag==0){
            return listMovies.size();
        }else{
            if(listMoviesCursor==null) return 0;
            return listMoviesCursor.getCount();
        }
    }

    @Override
    public void refresh() {

    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout relativeLayout;
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvRelease;
        public CategoryViewHolder(View v) {
            super(v);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.list_movies);
            imgPoster = (ImageView) v.findViewById(R.id.img_item_poster);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvDescription = (TextView) v.findViewById(R.id.tv_desc);
            tvRelease = (TextView) v.findViewById(R.id.tv_release);
        }
    }

    private MovieItem getItem(int position){
        if(!listMoviesCursor.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(listMoviesCursor);
    }

    public void clearData(){
        //listMovies.clear();
    }
}
