package com.irynas.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
        Log.i(TAG, "started onCreate");
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

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
