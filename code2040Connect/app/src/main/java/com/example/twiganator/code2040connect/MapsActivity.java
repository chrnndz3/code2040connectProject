package com.example.twiganator.code2040connect;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitide, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gpsTracker = new GPSTracker(getApplicationContext());
        mLocation = gpsTracker.getLocation();

        latitide = mLocation.getLatitude();
        longitude = mLocation.getLongitude();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    Marker marker;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMarker("Cindy Hernandez",41.7143528, -64.0059731);

        if(mMap != null){
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                @Override
                public View getInfoWindow(Marker marker){
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker){
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView name = (TextView) v.findViewById(R.id.name);
                    TextView workTitle = (TextView) v.findViewById(R.id.workTitle);
                    TextView company = (TextView) v.findViewById(R.id.company);
                    TextView email = (TextView) v.findViewById(R.id.email);
                    TextView facebookId = (TextView) v.findViewById(R.id.facebookID);
                    TextView vsnippet = (TextView) v.findViewById(R.id.snippet);

                    LatLng ll_marker = marker.getPosition();
                    name.setText(marker.getTitle());
                    workTitle.setText("Software Engineer");
                    company.setText("Intuit");
                    email.setText("chrnndz3@gmail.com");
                    facebookId.setText("cindy.hernandez.77715");
                    vsnippet.setText(marker.getSnippet());

                    return v;
                }

            });

        }

//        String str = "Occupation title:" + "Software Engineer" + '\n'
//                + "Works:" + "Intuit" + '\n'
//                + "Facebook id:" + "cindy.hernandez.77715";

        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(latitide, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title("Cindy Hernandez")
                .snippet("I'm here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                fromResource(R.mipmap.ic_launcher)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        );

        //ADDED ANOTHER PIN
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.7143528, -74.0059731))
                .title("pin")
                .snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    private void setMarker(String name, double lat, double lng) {
        if(marker != null){
            marker.remove();
        }

        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(name)
                .snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        marker = mMap.addMarker(options);
    }
}
