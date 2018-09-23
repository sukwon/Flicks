package com.codepath.flicks.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie implements Parcelable {

    public enum DisplayStyle {
        FULL_IMAGE, DETAIL
    }

    static final private Double VOTE_RATING_THRESHOLD = 7.0;

    private String posterPath;
    private String originalTitle;
    private String overview;
    private String backdropPath;
    private Double voteAverage;
    private String releaseDate;

    public DisplayStyle displayStyle;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.releaseDate = jsonObject.getString("release_date");
        this.voteAverage = jsonObject.getDouble("vote_average");

        this.displayStyle = voteAverage >= VOTE_RATING_THRESHOLD ? DisplayStyle.FULL_IMAGE : DisplayStyle.DETAIL;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    // Parcelable

    protected Movie(Parcel in) {
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        backdropPath = in.readString();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(backdropPath);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
    }
}
