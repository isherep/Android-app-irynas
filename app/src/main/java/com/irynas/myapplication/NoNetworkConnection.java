package com.irynas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class NoNetworkConnection extends AppCompatActivity {

    private static final String TAG = "No Network Connection";

    //note that you can pass a stringArray intent message
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_network_connection);

        Log.i(TAG, "started onCreate");
        //adding toolbar


        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("No Connection");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);







    }

}
