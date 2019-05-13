package com.irynas.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/*
   This is for the Homework 5
 */
public class CameraActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);

        setContentView(R.layout.camera_activity);
        //add the toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Camera Locations");
        //getSupportActionBar().setTitle("Cameras locations");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }

}
