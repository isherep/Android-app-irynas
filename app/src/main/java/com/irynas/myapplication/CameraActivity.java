package com.irynas.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.ParseError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
//import com.google.android.gms.common.api.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.google.android.gms.location.LocationListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;


/**
 * This is for the Homework 5
 * Your activity should check the device network status and display a graceful warning if not connected.
 * You should only make a network request if your application has connectivity
 * if your application requires making network calls, the calls need to be performed on the worker
 * threads that run in the background, not on the main thread.
 * You could use a Java HTTP client library to send and receive data over the network,
 * but the network call itself should be performed by a worker thread.
 */
public class CameraActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    private static boolean WIFIconnected = false;
    private static boolean mobileConnected = false;
    List<TrafficCam> cams = new ArrayList<TrafficCam>();


    private static final String TAG = "Camera Activity";

    //this goes into addapter
    //final TextView textView = (TextView) findViewById(R.id.text);

    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        setContentView(R.layout.camera_activity);

        Log.i(TAG, "started on Create");
        //add the toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Camera Locations");
        //getSupportActionBar().setTitle("Cameras locations");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        //---------------------Creating recycler view--------------------------

        recyclerView = (RecyclerView) findViewById(R.id.cameras_recycler_view);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        // use a linear layout manager
        recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        //initializing custom adapter
        recyclerViewAdapter = new CameraActivity.CustomAdapter();
        //setting the adapter to the recyclerView
        recyclerView.setAdapter(recyclerViewAdapter);

        String camUrl = "http://brisksoft.us/ad340/traffic_cameras_merged.json";

        //----------------Requesting Json info using Valley----------------------

       //create a list of WSDOT cammeras


        // if(wsdotbutton is checked){

            //create a list of WSDOT cammeras
            //show the list

        // }else if(SDOT is checked){

            //create a list of SDOT cammeras
            //show the list


        // }


        // add the cache object to the RequestQueue
        // Your activity should check the device network status and
        // display a graceful warning if not connected.
        // You should only make a network request if your application has connectivity
        boolean connected = checkNetworkConnections();

        if (connected) {

            RequestQueue queue = Volley.newRequestQueue(this);

            //params - String url, @Nullable Listener<JSONArray>, Response.ErrorListener - interface
            //Callbacks can be easily implemented with Java interfaces.
            JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, camUrl, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    //The Log.d() method is used to log debug messages.
                    Log.d("Cameras 1", response.toString());

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            //get each individual camera at the position
                            JSONObject indivCamera = response.getJSONObject(i);

                            double[] coords = {indivCamera.getDouble("ypos"), indivCamera.getDouble("xpos")};

                            TrafficCam cam = new TrafficCam(
                                    indivCamera.getString("cameralabel"),
                                    //pull the image url out of image object
                                    indivCamera.getJSONObject("imageurl").getString("url"),
                                    indivCamera.getString("ownershipcd"),
                                    coords
                            );

                            //if indivCamera.type == SWDOT  --> cams.add(indivCamera).
                            //

                            cams.add(cam);
                        }
                        recyclerViewAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        //error handler
                        Log.d("Cameras error", e.getMessage());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("JSON", "Error: " + error.getMessage());
                        }
                    });
            queue.add(arrayRequest);

        } else {
            //display text activity
            Intent intent = new Intent(this, NoNetworkConnection.class);
            startActivity(intent);
        }
    }

    //----------------Creating custom RecyclerView Adapter--------------------------

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            // TextView textView = (TextView)findViewById(R.id.description);

            public TextView cameraLabel;
            public ImageView camImage;

            //creating ViewHolder with camera label and image
            public ViewHolder(View v) {
                super(v);

                cameraLabel = v.findViewById(R.id.description);
                camImage = v.findViewById(R.id.image);
            }
        }

        //returns ViewHolder
        //This method calls onCreateViewHolder(ViewGroup, int) to create a new RecyclerView.ViewHolder
        // and initializes some private fields to be used by RecyclerView.
        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // Inflate the view for this view holder//
            //getLayoutInflater() - Instantiates a layout XML file into its corresponding View objects.
            // individual_cameras.xml --> item
            //individual_cameras have description and image
            View item = getLayoutInflater().inflate(R.layout.individual_cameras, parent,
                    false);

            // Call the view holder's constructor, and pass the view to it;
            // return that new view holder
            //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
            //ViewHolders belong to the adapter.
            ViewHolder holder = new ViewHolder(item);
            return holder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.cameraLabel.setText(cams.get(position).getLabel());
            String imageUrl = cams.get(position).imageUrl();
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(holder.camImage);
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return cams.size();
        }
    }

    //-----------------Checking connection-------------------------

    public boolean checkNetworkConnections() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            //different types of connection

            WIFIconnected = networkInfo.getType() == connectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == connectivityManager.TYPE_MOBILE;
            if (WIFIconnected) {
                Log.i("WIFI connected", "successfully");
                return true;
            } else if (mobileConnected) {
                Log.i("Mobile connected ", "successfuly");
                return true;
            }
        } else {
            Log.i("Connection status ", "No connection");
            return false;
        }
        return false;
    }
}
