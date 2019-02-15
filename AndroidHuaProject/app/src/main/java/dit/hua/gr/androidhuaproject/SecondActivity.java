package dit.hua.gr.androidhuaproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Spinner Object
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Get Spinner Id
        spinner = findViewById(R.id.dt_spinner);
        //Listens when an item from the spinner is clicked
        spinner.setOnItemSelectedListener(this);

        //Load the Spinner with data
        loadData();
        //Button to go back to the First Activity
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("ViewFirstActivity");
                startActivity(i);
            }
        });

        //Submit button
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("ViewThirdActivity");
                intent.putExtra("selected",((EditText) findViewById(R.id.user_id2)).getText().toString());
                intent.putExtra("list", (String) spinner.getSelectedItem());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        //DBHelper Object
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        //Spinner list
        ArrayList<CoordinatesContract> list = dbHelper.getData();
        //Create adapter for Spinner
        String[] dtList=new String[list.size()];

        for(int i=0;i<list.size();i++){
            //Create Array of dt
            dtList[i]=list.get(i).getDt();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dtList);
        //Dropdown List View
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Attach data from Adapter to Spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
