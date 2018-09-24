package com.codepath.flicks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flicks.R;
import com.codepath.flicks.adapters.MovieArrayAdapter;
import com.codepath.flicks.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = findViewById(R.id.lvMovie);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        fetchMovies();
        setupListViewListener();
    }

    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    final JSONArray movieJSONResults = json.getJSONArray("results");
                    Log.d("DEBUG", movieJSONResults.toString());
                    movies.addAll(Movie.fromJSONArray(movieJSONResults));

                    MovieActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchDetailView(position);
                    }
                }
        );
    }

    public void launchDetailView(int index) {
        Intent i = new Intent(MovieActivity.this, DetailActivity.class);
        Movie movie = movies.get(index);
        i.putExtra("movie", movie);
        startActivity(i);
    }
}
