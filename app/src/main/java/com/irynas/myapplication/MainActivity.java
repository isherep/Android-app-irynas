package com.irynas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.content.Intent.EXTRA_TEXT;

public class MainActivity extends AppCompatActivity {

    private static boolean WIFIconnected = false;
    private static boolean mobileConnected = false;

    private static final String TAG = "Main Activity";

    // rewrite to Kotlin later
    //Creating the lists of movie names
    //String[] NAMES = {"Night of the Comet", "Dead Snow","Cemetery Man", "28 Weeks Later","Night of the Creeps", "ParaNorman","Zombieland", "Planet Terror", "Train to Busan"};
    //method to your main activity that starts a new activity when the upper-left button is clicked,

    // if Google Play services is required for your app at all times, you might want to do it when your app first launches
/*

On the other hand, if Google Play services is an optional part of your app,
you can check the version only once the user navigates to that portion of your app.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Log.i(TAG, "started onCreate");

        //toolbar.setLogo(R.drawable.ic_info_black_24dp);

       Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {

                @Override
                        public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ListViewItemsActivity.class);
                    startActivity(intent);
                }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Button mapButton = (Button)findViewById(R.id.button3);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

            }
        });


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
            //return true;
       // }
        switch(item.getItemId()){
            case R.id.list_settings:
                Intent list = new Intent(this, ListViewItemsActivity.class);
                startActivity(list);
                break;
            case R.id.about_settings:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.info_icon:
                Intent aboutIcon = new Intent(this, AboutActivity.class);
                startActivity(aboutIcon);
                break;
            case R.id.location_icon:

                    Intent locationCameras = new Intent(this, CameraActivity.class);
                    startActivity(locationCameras);

                break;

            default:

        }

        return super.onOptionsItemSelected(item);
    }


}
