package com.sust.project_250_001;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG = "";
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 10.5f;

    private EditText mSearchText;
    private ImageView mGps;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Marker marker;
    ArrayList<Address> addresses = new ArrayList<Address>();

    private HashMap<Marker, String> hs = new HashMap<>();


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            //getDeviceLocation();
            //getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //mMap.getUiSettings().setMyLocationButtonEnabled(false);


           googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

               @Override
               public boolean onMarkerClick(Marker marker) {

                    marker.showInfoWindow();
//
//                    Intent intent = new Intent(MapActivity.this,Profile.class);
//                    intent.putExtra("profileID",hs.get(marker));
//                    if(hs.get(marker).equals(LoginActivity.user)) {
//                        intent.putExtra("from", "");
//                    }
//                    else intent.putExtra("from", "MapActivity");
//                    startActivity(intent);
//
                    return false;
                }
            });

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(MapActivity.this,Profile.class);
                    intent.putExtra("profileID",hs.get(marker));
                    if(hs.get(marker).equals(LoginActivity.user)) {
                        intent.putExtra("from", "");
                    }
                    else intent.putExtra("from", "MapActivity");
                    startActivity(intent);
//
                }
            });



            init();
            getCurrentLocation();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSearchText = (EditText) findViewById(R.id.inputsearch);
        mGps = (ImageView) findViewById(R.id.gpsid);

        getLocationPermission();


    }

    private void init() {
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute method for searching
                    geolocate();

                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDeviceLocation();
                getCurrentLocation();
            }
        });



        hideSoftKeyboard(MapActivity.this);
        drawNearby();
    }

    private void geolocate() {
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();

        try {

            list = geocoder.getFromLocationName(searchString, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            //Toast.makeText(MapActivity.this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));

        }
    }



    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (!title.equals("my location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            marker = mMap.addMarker(options);
        }
        hideSoftKeyboard(MapActivity.this);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }

                    mLocationPermissionGranted = true;

                    //initialize the map

                    initMap();
                }
            }
        }
    }


    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    double latitude;
    double longitude;

    private MarkerOptions userMarker;
    private Marker myMarker;

    private void drawNearby() {

        ArrayList<Address> addresses = new ArrayList<Address>();
        String address = null;

        try {

            Geocoder coder = new Geocoder(this);
            for(int i=0;i<HomePageActivity.profileInfoArrayList.size();i++){
                addresses.clear();
                address = HomePageActivity.profileInfoArrayList.get(i).getAddress();
                addresses = (ArrayList<Address>) coder.getFromLocationName(address,1);
                for(Address add : addresses){

                    double longitude = add.getLongitude();
                    double latitude = add.getLatitude();
                    MarkerOptions options = new MarkerOptions().position(new LatLng(latitude,longitude)).title(HomePageActivity.profileInfoArrayList.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    Marker marker = mMap.addMarker(options);

                    hs.put(marker,HomePageActivity.profileInfoArrayList.get(i).getUsername());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getCurrentLocation(){

        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {


        }
        else {

            // enable location buttons

            // fetch last location if any from provider - GPS.
            final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //if last known location is not available
            if (loc == null) {

                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(final Location location) {

                        // getting location of user
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        //do something with Lat and Lng
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //when user enables the GPS setting, this method is triggered.
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        //when no provider is available in this case GPS provider, trigger your gpsDialog here.
                    }
                };

                //update location every 10sec in 500m radius with both provider GPS and Network.

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000, 500, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);
            }
            else {
                //do something with last known location.
                // getting location of user
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
            }
        }

        if(userMarker == null) {
            mMap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude)).radius(10000).strokeWidth(0f).fillColor(0xE6FFBEBE));

            userMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current Location");
            myMarker = mMap.addMarker(userMarker);
            myMarker.showInfoWindow();
            hs.put(myMarker,LoginActivity.user);
        }
        else {
            myMarker.remove();

            userMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current Location");
            mMap.clear();
            mMap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude)).radius(10000).strokeWidth(0f).fillColor(0xE6FFBEBE));

            myMarker = mMap.addMarker(userMarker);
            myMarker.showInfoWindow();

            hs.put(myMarker,LoginActivity.user);
        }
        //Toast.makeText(this,"lat:"+latitude+" long:"+longitude,Toast.LENGTH_SHORT).show();
        moveCamera(new LatLng(latitude, longitude),DEFAULT_ZOOM
                , "my location");

        drawNearby();
    }

}
