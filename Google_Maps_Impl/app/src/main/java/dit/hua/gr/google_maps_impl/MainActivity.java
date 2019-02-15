package dit.hua.gr.google_maps_impl;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String user_id;

    //Function to get the user ID
    public static String getUser_id() {
        return user_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // onClick listener for the map button
        findViewById(R.id.map_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            EditText user_id_editText = findViewById(R.id.user_id_EditText);
            user_id = user_id_editText.getText().toString();

            //Check if user's input is empty
            if(!user_id.isEmpty()) {
                Cursor cursor = getContentResolver().query(
                        Uri.parse("content://dit.hua.gr.androidhuaproject/coordinates_table/"+user_id),
                        null,
                        null,
                        null,
                        null);
                //If the query found any results
                if(cursor.moveToFirst()) {

                    ArrayList<LatLng> coordinatesList = new ArrayList<>();

                    do {
                        Double longitude = cursor.getDouble(2);
                        Double latitude = cursor.getDouble(3);
                        LatLng coordinates = new LatLng(latitude,longitude);
                        //Add them in an arraylist
                        coordinatesList.add(coordinates);

                    } while(cursor.moveToNext());

                    //Create an Intent to MapsActivity class
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    Bundle bundle = new Bundle();

                    //Bundle the coordinatesList
                    bundle.putSerializable("coordinates", coordinatesList);
                    intent.putExtras(bundle);

                    //Start the activity
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "The user ID field is required.", Toast.LENGTH_SHORT).show();
            }
            }
        });

    }
}
