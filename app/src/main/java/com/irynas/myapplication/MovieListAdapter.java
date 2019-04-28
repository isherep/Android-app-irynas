package com.irynas.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MovieListAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final String[][] movies;

    public MovieListAdapter(Context context, String[][] movies) {
        super(context, -1);
        this.context = context;
        this.movies = movies;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movie_list_row_item, parent, false);

        TextView movieTitle = (TextView) rowView.findViewById(R.id.movieTitle);
        movieTitle.setText(movies[position][0]);

//        LinearLayout layout = new LinearLayout(context);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//        TextView title = new TextView(context);
//        title.setText(movies[position][0]);
//
//        TextView id = new TextView(context);
//        id.setText("ID: " + position);
//
//        layout.addView(title);
//        layout.addView(id);
        return rowView;
    }
}
