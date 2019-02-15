package dit.hua.gr.androidhuaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String ID = "_ID";
    public static final String USER_ID = "USER_ID";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String DT = "DT";
    public static final String DB_NAME = "COORDINATES";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "COORDINATES_TABLE";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME +" ("+
                ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                USER_ID+" TEXT NOT NULL, "+
                LONGITUDE+" TEXT NOT NULL, "+
                LATITUDE+" TEXT NOT NULL , "+
                DT+" DATETIME NOT NULL" +
                ");"
        );
    }

    public long insert(CoordinatesContract cc){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Initialize the values of the database fields
        ContentValues values = new ContentValues();
        values.put(USER_ID,cc.getUser_Id());
        values.put(LONGITUDE,cc.getLongitude());
        values.put(LATITUDE,cc.getLatitude());
        values.put(DT,String.valueOf(cc.getDt()));
        //nullColumnHack default value is null
        long status = sqLiteDatabase.insert(DbHelper.TABLE_NAME,null,values);
        return status;
    }

    //a method to take all the data from the db
    public ArrayList<CoordinatesContract> getData(){

        ArrayList<CoordinatesContract> list = new ArrayList<>();
        //a cursor to control the database
        Cursor cursor = this.getReadableDatabase().query(DbHelper.TABLE_NAME, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                //get the data from every column
                String uid = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String longitude = cursor.getString(cursor.getColumnIndex("LONGITUDE"));
                String latitude = cursor.getString(cursor.getColumnIndex("LATITUDE"));
                String dt = cursor.getString(cursor.getColumnIndex("DT"));
                //put them into a custom instance
                CoordinatesContract data = new CoordinatesContract(uid,longitude,latitude,dt);
                //add this instance to the arraylist
                list.add(data);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long deletebyId(String deleted_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long status = db.delete(DbHelper.TABLE_NAME, "USER_ID=?", new String[]{deleted_id});
        return status;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
