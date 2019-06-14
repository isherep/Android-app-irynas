package com.irynas.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;



import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * detects the user's location,
 * displays a map, centered on the user's location,
 * displays a marker for each of the traffic camera's loaded in the previous homework,
 * display camera label when a marker is clicked
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location currentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private static final String[] permission = {ACCESS_COARSE_LOCATION};
    private boolean locationPermissionGranted;

    private CameraActivity camActivity;

    private static boolean WIFIconnected = false;
    private static boolean mobileConnected = false;

    //list of cameras
    List<TrafficCam> cams = new ArrayList<TrafficCam>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocationPermission();
        //loadCameras();
        //showCameraMarkers();
    }
    //------------Getting location--------------------

    public void getDeviceLocation() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (locationPermissionGranted) {
                Task<Location> location = fusedLocationClient.getLastLocation();

                location.addOnSuccessListener(
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    mMap.setMyLocationEnabled(true);

                                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.setMinZoomPreference(9);
                                    //mMap.setMinZoomPreference(10); // zoom to city level
                                    mMap.addMarker(new MarkerOptions().position(myLocation)
                                            .title("Iryna"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                                    mMap.setMinZoomPreference(9);
                                    //showCameraMarkers();
                                }
                            }
                        }
                );
            }
        } catch (SecurityException e) {

            Log.e("Security Exception ", "Get device location");
        }
    }


public void loadCameras(){

    String camUrl = "http://brisksoft.us/ad340/traffic_cameras_merged.json";

    //----------------Requesting Json info using Valley----------------------

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
                        cams.add(cam);
                        Log.i("CAMERA DATA", cam.toString());
                    }
                    //recyclerViewAdapter.notifyDataSetChanged();
                    showCameraMarkers();

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

    /**
     * Shows markers of each camera on the map
     */
    public void showCameraMarkers() {

        Log.i("CAMERA DATA", cams.toString());

        //This is not needed if redirected from the main activity

        String camsListAsString = getIntent().getStringExtra("cameras_as_string");

        Gson gson = new Gson();
        Type type = new TypeToken<List<TrafficCam>>() {
        }.getType();
        List<TrafficCam> camsList = gson.fromJson(camsListAsString, type);

        for (TrafficCam cams : camsList) {
            Log.i("Data", cams.getLabel());

            LatLng position = new LatLng(cams.getCoords()[0], cams.getCoords()[1]);

            Log.i("POSITIONS ", position.toString());
            Marker m = mMap.addMarker(new MarkerOptions().position(position).title(cams.getLabel()).snippet(cams.imageUrl()));

            m.setTag(cams);
        }


        for(TrafficCam camera: cams){
            Log.i("CAMERA DATA", camera.toString());
           LatLng position = new LatLng(camera.getCoords()[0], camera.getCoords()[1]);
           Marker m = mMap.addMarker(new MarkerOptions().position(position).title(camera.getLabel()).snippet(camera.imageUrl()));
           m.setTag(camera);
        }
    }


    private void getLocationPermission() {

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_REQUEST_CODE);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (locationPermissionGranted) {
            getDeviceLocation();
            loadCameras();
            showCameraMarkers();
            //getLocationPermission();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //public void onRequestPermissionsResult(int requestCode, permission, int[] grantResults){
                //locationPermissionGranted = true;
                ActivityCompat.requestPermissions(this, permission, LOCATION_REQUEST_CODE);
            }
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

}
