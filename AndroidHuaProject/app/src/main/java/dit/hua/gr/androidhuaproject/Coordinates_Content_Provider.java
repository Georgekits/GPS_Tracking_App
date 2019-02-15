package dit.hua.gr.androidhuaproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dit.hua.gr.androidhuaproject.DbHelper;

public class Coordinates_Content_Provider extends ContentProvider {

    private DbHelper dbHelper;
    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        //Initialize uriMatcer
        uriMatcher  = new UriMatcher(UriMatcher.NO_MATCH);
        // Create 3 different categories
        uriMatcher.addURI(
                "dit.hua.gr.androidhuaproject",
                "coordinates_table",
                1);
        uriMatcher.addURI(
                "dit.hua.gr.androidhuaproject",
                "coordinates_table/#",
                2);
        uriMatcher.addURI(
                "dit.hua.gr.androidhuaproject",
                "add",
                3);

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case 1:
                cursor  = dbHelper.getReadableDatabase().query(
                        DbHelper.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                break;
            case 2:
                String id = uri.getLastPathSegment();
                cursor = dbHelper.getReadableDatabase().query(
                        DbHelper.TABLE_NAME,
                        null,
                        DbHelper.USER_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = dbHelper.getWritableDatabase().insert(DbHelper.TABLE_NAME,null,values);
        dbHelper.getWritableDatabase().close();
        return Uri.parse("content://dit.hua.gr.androidhuaproject/coordinates_table/"+id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}