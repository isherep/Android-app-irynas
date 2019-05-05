package com.irynas.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.squareup.picasso.Picasso;

public class MovieDetailViewActivity extends AppCompatActivity {


    //note that you can pass a stringArray intent message
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        String[] movieDetail = intent.getStringArrayExtra(RecyclerViewActivity.RESULT);

        ImageView imageView = findViewById(R.id.LargeMovieImage);

        TextView title = (TextView)findViewById(R.id.movieTitle);
        TextView year = (TextView)findViewById(R.id.movieYear);
        TextView director = (TextView)findViewById(R.id.movieDirector);
        TextView description = (TextView)findViewById(R.id.movieDescription);
        //note that you can pass a stringArray intent message.
        String url = movies[3];
        Picasso.get().load(movies[3]).into(imageView);


    }

}
