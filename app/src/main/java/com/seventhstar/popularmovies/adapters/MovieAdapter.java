package com.seventhstar.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seventhstar.popularmovies.MainActivity;
import com.seventhstar.popularmovies.R;
import com.seventhstar.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> moviesList;
    private final Context context;
    private final Callbacks callbacks;

    public interface Callbacks {
        void openMovie(Movie movie);
    }

    public MovieAdapter(MainActivity mainActivity) {
        super(mainActivity, 0);
        this.callbacks = mainActivity;
        this.moviesList = new ArrayList<>();
        this.context = mainActivity;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return moviesList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final View cell;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        final Movie movie = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.movie_poster);

        TextView versionNameView = convertView.findViewById(R.id.flavor_text);
        versionNameView.setText(movie != null ? movie.getTitle() : "");

        String url = movie != null ? movie.getPreviewURL() : null;
        if (url != null) {
            Picasso.with(context)
                    .load(url)
                    .into(imageView);
        }

        cell = convertView;
        cell.setId(position);

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = getItem(cell.getId());
                callbacks.openMovie(movie);
            }
        });

        return convertView;
    }

    public void refresh(List<Movie> moviesList) {
        this.moviesList = moviesList;
        this.notifyDataSetChanged();
    }
}