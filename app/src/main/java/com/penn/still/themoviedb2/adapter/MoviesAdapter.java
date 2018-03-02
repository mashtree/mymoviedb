package com.penn.still.themoviedb2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.penn.still.themoviedb2.DetailMovieActivity;
import com.penn.still.themoviedb2.R;
import com.penn.still.themoviedb2.models.MovieItem;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by triyono on 1/1/2018.
 */

public class MoviesAdapter extends BaseAdapter {

    private List<MovieItem> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MoviesAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setmData(List<MovieItem> movies){
        mData = movies;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem movie){
        mData.add(movie);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public MovieItem getItem(int index) {
        return mData.get(index);
    }

    @Override
    public long getItemId(int index) {
        return mData.get(index).getId();
    }

    @Override
    public View getView(final int index, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view==null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.movie_item,null);
            holder.imgPoster = (ImageView) view.findViewById(R.id.iv_poster);
            holder.txtTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.txtDescription = (TextView) view.findViewById(R.id.tv_desc);
            holder.txtRelease = (TextView) view.findViewById(R.id.tv_release);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        /**
         * retrieve poster
         */
        String url = "http://image.tmdb.org/t/p/w185/"+mData.get(index).getPosterPath();

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_filter_b_and_w_black_24dp)
                .into(holder.imgPoster);
        holder.txtTitle.setText(mData.get(index).getTitle());
        String desc = mData.get(index).getOverview();
        holder.txtDescription.setText(desc.length()>=36?desc.substring(0,36)+" ...":desc); //display only fewer chars
        //holder.txtRelease.setText(sdf.format(cal)); //date format

        holder.txtRelease.setText(MoviesAdapter.longFormatDate(mData.get(index).getRelease()));
        //holder.txtRelease.setText(mData.get(index).getRelease());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDetail = new Intent(context,DetailMovieActivity.class);
                intentToDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE,mData.get(index));
                // additional flag, for calling from outside activity context
                intentToDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentToDetail);

            }
        });
        return view;
    }

    public static String longFormatDate(String dateString){
        /**
         * date format
         */
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("EEEE, d MMMM, yyyy", Locale.getDefault());
        if(dateString.equals("")) dateString = "1900-01-01";
        Date date = null;
        try {
            date = inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }

    private static class ViewHolder{
        ImageView imgPoster;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtRelease;
    }
}