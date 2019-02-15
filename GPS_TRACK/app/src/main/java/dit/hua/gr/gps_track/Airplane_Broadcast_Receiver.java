package dit.hua.gr.gps_track;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Airplane_Broadcast_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Check if GPS_Service Class is running
        boolean serviceRunningStatus = isServiceRunning(GPS_Service.class,context);
        if( serviceRunningStatus) {
            Toast.makeText(context, "The service has stopped!", Toast.LENGTH_SHORT).show();
            context.stopService(new Intent(context, GPS_Service.class));
        } else {
            Toast.makeText(context, "The service just started!", Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context, GPS_Service.class));
        }

    }

    //Function to check if the service is running
    private boolean isServiceRunning(Class<?> serviceClass,Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }

        }
        return false;
    }
}