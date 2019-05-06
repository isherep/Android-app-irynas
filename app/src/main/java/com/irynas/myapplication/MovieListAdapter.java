package com.irynas.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieListAdapter extends ArrayAdapter<String[]> {

    public MovieListAdapter(Context context, String[][] movies) {
        super(context, -1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_row_item, parent, false);
        }
        //ImageView imageView = findViewById(R.id.movieImage);
        //String url = "movies"

        String[] movie = getItem(position);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
        movieTitle.setText(movie[0]);

        TextView movieYear = (TextView) convertView.findViewById(R.id.movieYear);
        movieYear.setText(movie[1]);

        ImageView movieImage = convertView.findViewById(R.id.movieImage);
        String url = (movie[3]);

        Picasso.get().load(url).into(movieImage);

        return convertView;
    }
}
