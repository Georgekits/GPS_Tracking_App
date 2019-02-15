package dit.hua.gr.gps_track;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Airplane_Broadcast_Receiver receiver;
    private IntentFilter filter;
    private static String user_id;

    //Function to get the user ID
    public static String getUser_id() {
        return user_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new Airplane_Broadcast_Receiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        // onClick listener for the track button
        findViewById(R.id.track_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Get user ID
            EditText user_id_text = findViewById(R.id.user_id_editText);
            user_id = user_id_text.getText().toString();

            if (!user_id.isEmpty()) {
                //Register the Airplane broadcast receiver
                registerReceiver(receiver, filter);
            } else {
                Toast.makeText(getApplicationContext(), "The user ID field is required.", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}