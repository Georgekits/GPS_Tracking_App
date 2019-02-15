package dit.hua.gr.androidhuaproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {

    //List View (View group that displays a list of scrollable items)
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        //DBHelper Object
        final DbHelper dbHelper = new DbHelper(FourthActivity.this);
        ArrayList<CoordinatesContract> list = dbHelper.getData();

        //Get the Id of the list_view element
        list_view = findViewById(R.id.delete_result_list);

        //Create an Arraylist
        ArrayList<String> Final = new ArrayList<>();
        //Load Arraylist
        for (int i = 0; i < list.size(); i++) {
            Final.add(list.get(i).getUser_Id());
            Final.add(list.get(i).getLongitude());
            Final.add(list.get(i).getLatitude());
            Final.add(list.get(i).getDt());
        }

        //Get Application context
        final Context context = getApplicationContext();

        //Attach an adapter to the Arraylist
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Final);
        //Check if adapter is empty
        if(adapter.isEmpty()){
            Toast.makeText(context,"No data entry found with this input", Toast.LENGTH_LONG).show();
        }else{
            list_view.setAdapter(adapter);
        }

        Button deletion = findViewById(R.id.deletion);
        deletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idtext = findViewById(R.id.delete_id);
                String deleted_id = idtext.getText().toString();
                long status = dbHelper.deletebyId(deleted_id);
                if(status > 0){
                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Delete Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
