package dit.hua.gr.androidhuaproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    //List View (View group that displays a list of scrollable items)
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //DBHelper Object
        DbHelper dbHelper = new DbHelper(ThirdActivity.this);
        ArrayList<CoordinatesContract> list = dbHelper.getData();

        //Get the Id of the list_view element
        list_view = findViewById(R.id.result_list);
        //Extract the Intent
        String id = getIntent().getStringExtra("selected");
        String dt = getIntent().getStringExtra("list");

        //Get Application context
        Context context = getApplicationContext();
        //Create an Arraylist
        ArrayList<String> Final = new ArrayList<>();
        //Load Arraylist
        for (int i = 0; i < list.size(); i++) {
            if (id.equals(list.get(i).getUser_Id()) && dt.equals(list.get(i).getDt())) {
                Final.add(list.get(i).getUser_Id());
                Final.add(list.get(i).getLongitude());
                Final.add(list.get(i).getLatitude());
                Final.add(list.get(i).getDt());
            }
        }
        //Attach an adapter to the Arraylist
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Final);
        //Check if adapter is empty
        if(adapter.isEmpty()){
            Toast.makeText(context,"No data entry found with this input", Toast.LENGTH_LONG).show();
        }else{
            list_view.setAdapter(adapter);
        }

        //Home Button
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("ViewFirstActivity");
                startActivity(i);
            }
        });
    }
}
