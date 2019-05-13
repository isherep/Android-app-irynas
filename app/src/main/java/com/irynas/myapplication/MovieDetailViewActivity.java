package com.irynas.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.squareup.picasso.Picasso;

public class MovieDetailViewActivity extends AppCompatActivity {

    private static final String TAG = "Movie Detail Activity";

    //note that you can pass a stringArray intent message
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        Log.i(TAG, "started");
        Bundle bundle = getIntent().getExtras();
        //pulling elements values from key on Bundle
        String ImageUrlReceived = bundle.getString("ImageURL");
        String TitleReceived = bundle.getString("Title");
        String YearReceived = bundle.getString("Year");
        String DirectReceived = bundle.getString("Director");
        String DescReceived = bundle .getString("Description");

        //creating layout elements
        ImageView image = (ImageView) findViewById(R.id.LargeMovieImage);
        TextView title = (TextView)findViewById(R.id.movieTitle);
        TextView year = (TextView)findViewById(R.id.movieYear);
        TextView director = (TextView)findViewById(R.id.movieDirector);
        TextView description = (TextView)findViewById(R.id.movieDescription);
        //setting text variables to the fields
        title.setText(TitleReceived);
        year.setText(YearReceived);
        director.setText(DirectReceived);
        description.setText(DescReceived);
        //setting image URL
        Picasso.get().load(ImageUrlReceived).into(image);
    }

}
