package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {

    public enum DisplayStyle {
        FULL_IMAGE, DETAIL
    }

    static final private Double VOTE_RATING_THRESHOLD = 7.0;

    private String posterPath;
    private String originalTitle;
    private String overview;
    private String backdropPath;

    public DisplayStyle displayStyle;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");

        Double voteAverage = jsonObject.getDouble("vote_average");
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
}
