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

public class MovieListAdapter extends ArrayAdapter<String[]> {

    public MovieListAdapter(Context context, String[][] movies) {
        super(context, -1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_row_item, parent, false);
        }


        String[] movie = getItem(position);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
        movieTitle.setText(movie[0]);

        TextView movieYear = (TextView) convertView.findViewById(R.id.movieYear);
        movieYear.setText(movie[1]);

        ImageView movieImage = (ImageView) convertView.findViewById(R.id.movieImage);
        movieImage.setImageURI(Uri.parse(movie[3]));

        return convertView;
    }
}
