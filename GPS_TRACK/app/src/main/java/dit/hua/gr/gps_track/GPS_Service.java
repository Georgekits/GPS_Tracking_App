package dit.hua.gr.gps_track;

import android.Manifest;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GPS_Service extends Service {

    //Create variables for LocationManager and LocationListener
    private LocationManager lm;
    private LocationListener ls;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;

        }

        ls = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                //Create content values object
                ContentValues values = new ContentValues();
                String user_id = MainActivity.getUser_id();
                values.put("USER_ID", user_id);
                values.put("LATITUDE", location.getLatitude());
                values.put("LONGITUDE", location.getLongitude());
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date(location.getTime());
                String formatted = format.format(date);
                values.put("DT", formatted);

                //Insert these values through the content provider
                getContentResolver().insert( Uri.parse("content://dit.hua.gr.androidhuaproject/add"),  values);

                //Inform user that the location has changed and added to the database
                Toast.makeText(getApplicationContext(), "Location added!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        // Location manager to get user's location
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 20, ls );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //When the service is stopped, close the location listener
        lm.removeUpdates(ls);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
