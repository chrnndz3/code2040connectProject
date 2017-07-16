package com.example.twiganator.code2040connect;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitide, longitude;
    private JSONObject jsonResponse;
    String jamal;
    Nerd [] nerdses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getData();
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



//        String str = "Occupation title:" + "Software Engineer" + '\n'
//                + "Works:" + "Intuit" + '\n'
//                + "Facebook id:" + "cindy.hernandez.77715";

        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(latitide, longitude);
        double zoomLevel = 11; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, (float)zoomLevel));

//        mMap.addMarker(
//                 new MarkerOptions()
//                .position(currentLocation)
//                .title("Cindy Hernandez")
//                .snippet("I'm here")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//        );

        //lat, lon
        String [][] arr = {{"Billy", "Paypal", "37.4088733", "-121.9302447"}, {"Bob", "Google", "37.4199213","-122.1138465" }};

        for(int i =0; i < arr.length; i++ ){
            String name = arr[i][0];
            String company = arr[i][1];
            System.out.println(name);
            System.out.println(company);
            Double lat1 = Double.parseDouble(arr[i][2]);
            Double lon1 = Double.parseDouble(arr[i][3]);
            Location loc1 = new Location("");

            loc1.setLatitude(lat1);
            loc1.setLongitude(lon1);

            Location loc2 = new Location("");
            loc2.setLatitude(latitide);
            loc2.setLongitude(longitude);

            float distanceInMeters = loc1.distanceTo(loc2);
            setMarker(name, lat1, lon1);

            if(marker != null){
                marker.remove();
            }

            setPinWindow(company, "email");
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat1, lon1))
                    .title(name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        );

//            if(distanceInMeters < 50)
//            {
//
//            }
        }

//        //ADDED ANOTmR PIN
//        mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(40.7143528, -74.0059731))
//                .title("pin")
//                .snippet("and snippet")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    private void setPinWindow(final String str_company, final String str_email) {
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
                    TextView company = (TextView) v.findViewById(R.id.company);
                    TextView email = (TextView) v.findViewById(R.id.email);
                    TextView vsnippet = (TextView) v.findViewById(R.id.snippet);

//                    LatLng ll_marker = marker.getPosition();
                    name.setText(marker.getTitle());
                    company.setText(str_company);
                    email.setText(str_email);
                    vsnippet.setText(marker.getSnippet());

                    return v;
                }

            });

        }
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

    private void getData(){
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                System.out.println("Haha sebastian" + response);
                String [] people = response.split("\\,\"|\\,\\[");
                nerdses = new Nerd[135];
                int j = 1;
                int index = 0;
                for (int i=1;i< people.length;i++){
                    String single_info = people[i-1];
                    String entry;
                    try {
//                        entry = single_info.substring(single_info.indexOf('"') + 1, single_info.indexOf('"', 3));
                        String[] splt = single_info.split("\"");
                        entry = splt[1];
                    } catch (Exception e){
                        String[] splt = single_info.split("\"");
                        entry = splt[0];
                    }
                    System.out.println(entry);

                    if (j==1){
                        Nerd new_guy = new Nerd();
                        new_guy.setFirstName(entry);
                        nerdses[index] = new_guy;
                    }
                    else if (j==2){
                        nerdses[index].setLastName(entry);
                    }
                    else if (j==3){
                        nerdses [index].setEmail(entry);
                    }
                    else if (j==4){
                        nerdses [index].setCompany(entry);
                    }
                    else if (j==5){
                        nerdses [index].setLongitude(Double.parseDouble(entry));
                    }
                    else if (j==6){
                        nerdses[index].setLatitude(Double.parseDouble(entry));
                    }
                    else if (j==7){
                        nerdses[index].setSchool(entry);
                        j = 0;
                        index++;
                    }
                    j++;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    boolean sucess = json.getBoolean("success");

                    if (sucess) {
                        System.out.println("success");
                        jsonResponse = json;
                    } else {
                        System.out.println("FAILED :(");
                        AlertDialog.Builder bldr = new AlertDialog.Builder(MapsActivity.this);
                        bldr.setMessage("data getting failed :(")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RetrievalRequest retrievalRequest = new RetrievalRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        queue.add(retrievalRequest);

        //        Iterator<?> keys = jsonResponse.keys();
//
//        while( keys.hasNext() ) {
//            String key = (String)keys.next();
//            if ( jsonResponse.get(key) instanceof JSONObject ) {
//                jsonResponse.toString();
//            }
//        }

    }

    public void parsePerson(String p){
        String[] ppl = p.split("\\,");
        for (int i = 0; i < ppl.length; i++){
            String fixed = ppl[i].substring(1, ppl[i].length() - 1);
            System.out.println(fixed);
        }
    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
//    private void makeJsonObjectRequest() {
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
//                "https://code2040.000webhostapp.com/get_many_users.php", null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                System.out.println(response.toString());
//
//                try {
//                    // Parsing json object response
//                    // response will be a json object
//                    String name = response.getString("name");
//                    String email = response.getString("email");
//                    JSONObject phone = response.getJSONObject("phone");
//                    String home = phone.getString("home");
//                    String mobile = phone.getString("mobile");
//
//                    jsonResponse = "";
//                    jsonResponse += "Name: " + name + "\n\n";
//                    jsonResponse += "Email: " + email + "\n\n";
//                    jsonResponse += "Home: " + home + "\n\n";
//                    jsonResponse += "Mobile: " + mobile + "\n\n";
//
//                    txtResponse.setText(jsonResponse);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//                hidepDialog();
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                hidepDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//    }

}
