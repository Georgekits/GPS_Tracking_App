package dit.hua.gr.androidhuaproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button storeButton = findViewById(R.id.store_button);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the values of each EditText field
                String user_id = ((EditText) findViewById(R.id.user_id)).getText().toString();
                String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();
                String latitude = ((EditText) findViewById(R.id.latitude)).getText().toString();
                String dt = ((EditText) findViewById(R.id.dt)).getText().toString();

                //Checks all input fields before creating the object
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateobj = null;
                try {
                    dateobj = formatter.parse(dt);
                } catch (ParseException e) {
                    Toast.makeText(FirstActivity.this, "Give valid timestamp\nFormat:yyyy-MM-dd HH:mm:ss", Toast.LENGTH_LONG).show();
                    return;
                }
                Context context = getApplicationContext();
                if(user_id.isEmpty() || longitude.isEmpty() || latitude.isEmpty() || dt.isEmpty()){
                    Toast.makeText(context,"Enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //DbHelper Object
                DbHelper dbHelper = new DbHelper(FirstActivity.this);

                //Create a CoordinatesContract Object to store to DB
                CoordinatesContract cc = new CoordinatesContract(user_id,longitude,latitude,String.valueOf(dateobj));

                //Insert to DB
                long status = dbHelper.insert(cc);

                //Check status of the insertion to DB
                if (status <= 0) {
                    Toast.makeText(context, "Insertion Unsuccessful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Insertion Successful", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Button to proceed to the next activity
        Button proceedButton = findViewById(R.id.next_activity);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("ViewSecondActivity");
                startActivity(i);
            }
        });

        //Button to proceed to the next activity
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("ViewForthActivity");
                startActivity(i);
            }
        });
    }
}
