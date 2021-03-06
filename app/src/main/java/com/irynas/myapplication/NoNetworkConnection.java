package com.irynas.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.widget.Toolbar;

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
