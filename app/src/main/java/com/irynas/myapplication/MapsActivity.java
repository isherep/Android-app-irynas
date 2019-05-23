package com.irynas.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.lang.reflect.Type ;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocationPermission();
        showCameraMarkers();

       

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

                                }
                            }
                        }
                );
            }
        } catch (SecurityException e) {

            Log.e("Security Exception ", "Get device location");
        }
    }

    public void showCameraMarkers(){
        //You have to import java.lang.reflect.Type ;

        //in on onCreate() to retrieve ArrayList:

        String camsListAsString = getIntent().getStringExtra("cameras_as_string");

        Gson gson = new Gson();
        Type type = new TypeToken<List<TrafficCam>>(){}.getType();
        List<TrafficCam> camsList = gson.fromJson(camsListAsString, type);
        for (TrafficCam cams : camsList){
            Log.i("Data", cams.getLabel());
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
