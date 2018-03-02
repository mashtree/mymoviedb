package uxt6.psu.com.myfavoritemovie.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import uxt6.psu.com.myfavoritemovie.db.DatabaseContract;

/**
 * Created by aisyahumar on 2/18/2018.
 */

public class MovieItem implements Parcelable {

    private int voteCount;
    private int id;
    private boolean video;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private int[] genre;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String release;
    private final String TAG = "Movie Item";

    public MovieItem(){}

    public MovieItem(JSONObject object){
        try{
            voteCount = object.getInt("vote_count");
            id = object.getInt("id");
            video = object.getBoolean("video");
            voteAverage = object.getDouble("vote_average");
            title = object.getString("title");
            popularity = object.getDouble("popularity");
            posterPath = object.getString("poster_path");
            originalLanguage = object.getString("original_language");
            backdropPath = object.getString("backdrop_path");
            adult = object.getBoolean("adult");
            setOverview(object.getString("overview"));
            setRelease(object.getString("release_date"));
        }catch(Exception e){
            Log.e(TAG, "Error on constructor");
            e.printStackTrace();
        }

    }

    public MovieItem(Cursor cursor){
        id = DatabaseContract.getColumnInt(cursor, DatabaseContract.FavoritesColumns._ID);
        voteAverage = DatabaseContract.getColumnInt(cursor, DatabaseContract.FavoritesColumns.VOTE_AVERAGE);
        title = DatabaseContract.getColumnString(cursor, DatabaseContract.FavoritesColumns.TITLE);
        popularity = DatabaseContract.getColumnDouble(cursor, DatabaseContract.FavoritesColumns.POPULARITY);
        posterPath = DatabaseContract.getColumnString(cursor, DatabaseContract.FavoritesColumns.POSTER_PATH);
        originalLanguage = DatabaseContract.getColumnString(cursor, DatabaseContract.FavoritesColumns.LANGUAGE);
        overview = DatabaseContract.getColumnString(cursor, DatabaseContract.FavoritesColumns.OVERVIEW);
        release = DatabaseContract.getColumnString(cursor, DatabaseContract.FavoritesColumns.RELEASE_DATE);
    }

    public int getVote_count() {
        return voteCount;
    }

    public void setVote_count(int vote_count) {
        this.voteCount = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int[] getGenre() {
        return genre;
    }

    public void setGenre(int[] genre) {
        this.genre = genre;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.voteCount);
        parcel.writeInt(this.id);
        parcel.writeInt(this.video?1:0);
        parcel.writeDouble(this.voteAverage);
        parcel.writeString(this.title);
        parcel.writeDouble(this.popularity);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.originalLanguage);
        parcel.writeString(this.backdropPath);
        parcel.writeInt(this.adult?1:0);
        parcel.writeString(this.overview);
        parcel.writeString(this.release);
    }

    protected MovieItem(Parcel in){
        this.voteCount = in.readInt();
        this.id = in.readInt();
        this.video = in.readInt()==1?true:false;
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.backdropPath = in.readString();
        this.adult = in.readInt()==1?true:false;
        this.overview = in.readString();
        this.release = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>(){
        @Override
        public MovieItem createFromParcel(Parcel source){
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size){
            return new MovieItem[size];
        }
    };
}