package com.codepath.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class DetailViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    private static class FullImageViewHolder {
        ImageView ivImage;
    }

    public  MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        int type = getItemViewType(position);

        if (type == Movie.DisplayStyle.FULL_IMAGE.ordinal()) {
            FullImageViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie_image, parent, false);

                viewHolder = new FullImageViewHolder();
                viewHolder.ivImage = convertView.findViewById(R.id.ivMovie);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (FullImageViewHolder) convertView.getTag();
            }

            viewHolder.ivImage.setImageResource(0);

            String imagePath;
            int orientation = parent.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imagePath = movie.getPosterPath();
            } else {
                imagePath = movie.getBackdropPath();
            }

            Picasso.with(getContext()).load(imagePath).placeholder(R.drawable.placeholder).into(viewHolder.ivImage);
        } else {
            DetailViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie, parent, false);

                viewHolder = new DetailViewHolder();
                viewHolder.ivImage = convertView.findViewById(R.id.ivMovie);
                viewHolder.tvOverview = convertView.findViewById(R.id.tvOverView);
                viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (DetailViewHolder) convertView.getTag();
            }

            viewHolder.ivImage.setImageResource(0);
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(movie.getOverview());

            String imagePath;
            int orientation = parent.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imagePath = movie.getPosterPath();
            } else {
                imagePath = movie.getBackdropPath();
            }

            Picasso.with(getContext()).load(imagePath).placeholder(R.drawable.placeholder).into(viewHolder.ivImage);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItem(position).displayStyle.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.DisplayStyle.values().length;
    }
}
