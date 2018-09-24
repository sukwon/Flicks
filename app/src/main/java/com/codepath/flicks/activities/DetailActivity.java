package com.codepath.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = getIntent().getParcelableExtra("movie");

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getOriginalTitle());
        TextView tvOverview = findViewById(R.id.tvOverView);
        tvOverview.setText(movie.getOverview());
        TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText("Released on " + movie.getReleaseDate());

        RatingBar rBar = findViewById(R.id.ratingBar);
        Float rating = movie.getVoteAverage().floatValue() / 2.0f;
        rBar.setRating(rating);

        ImageView ivMovie = findViewById(R.id.ivMovie);
        Picasso.with(DetailActivity.this).load(movie.getBackdropPath()).placeholder(R.mipmap.ic_launcher).into(ivMovie);
    }
}
