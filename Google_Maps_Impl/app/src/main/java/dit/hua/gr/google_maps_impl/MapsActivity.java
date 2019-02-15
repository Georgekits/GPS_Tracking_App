package dit.hua.gr.google_maps_impl;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Extract the intent
        Bundle bundle = getIntent().getExtras();

        //Get the content to the arraylist
        final ArrayList<LatLng> coordinates = bundle.getParcelableArrayList("coordinates");

        //For every element of the arraylist, display the marker to the google maps
        for(LatLng coordinate: coordinates) {
            mMap.addMarker( new MarkerOptions().position(coordinate)  );
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                //Create content values object
                ContentValues values = new ContentValues();
                values.put("USER_ID", MainActivity.getUser_id());
                values.put("LATITUDE", latLng.latitude);
                values.put("LONGITUDE", latLng.longitude);
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String formatted = format.format(date);
                values.put("DT", formatted);

                //Insert these values through the content provider
                getContentResolver().insert( Uri.parse("content://dit.hua.gr.androidhuaproject/add"),  values);

                Toast.makeText(getApplicationContext(),"Coordinates addded",Toast.LENGTH_SHORT).show();
            }
        });
    }
}