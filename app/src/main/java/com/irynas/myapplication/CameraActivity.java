package com.irynas.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


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


/*
   This is for the Homework 5
 */
public class CameraActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    List<TrafficCam> cams = new ArrayList<TrafficCam>();


    //final TextView textView = (TextView) findViewById(R.id.text);

    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        setContentView(R.layout.camera_activity);
        //add the toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Camera Locations");
        //getSupportActionBar().setTitle("Cameras locations");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        String url = "http://brisksoft.us/ad340/traffic_cameras_merged.json";
/*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error is ", "Error responce");

                    }
                });*/

// Access the RequestQueue through your singleton class.
        // MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mName;
            public ImageView mImage;
            public ViewHolder(View v) {
                super(v);

                mName = v.findViewById(R.id.description);
                mImage = v.findViewById(R.id.image);
            }
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            // Inflate the view for this view holder
            View item = getLayoutInflater().inflate(R.layout.individual_cameras, parent,
                    false);

            // Call the view holder's constructor, and pass the view to it;
            // return that new view holder
            ViewHolder vh = new ViewHolder(item);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mName.setText(cams.get(position).getLabel());
            //String url = baseUrls.get(cameraList.get(position).getType()) + cameraList.get(position).getImageUrl();
            Picasso.get().load(cams.get(position).imageUrl()).into(holder.mImage);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return cams.size();
        }
    }
}
