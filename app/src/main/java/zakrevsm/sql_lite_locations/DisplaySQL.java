package zakrevsm.sql_lite_locations;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class DisplaySQL extends AppCompatActivity {
    protected SimpleCursorAdapter mSQLCursorAdapter;

    protected ArrayList results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_display_sql);

        DBContract.SQLiteExample mSQLLiteExample = new DBContract.SQLiteExample(this);
        SQLiteDatabase mSQLDB = mSQLLiteExample.getWritableDatabase();
        Cursor mSQLCursor = mSQLDB.query(DBContract.DemoTable.TABLE_NAME,
                new String[]{DBContract.DemoTable._ID, DBContract.DemoTable.COLUMN_NAME_LOCATION_STRING,
                        DBContract.DemoTable.COLUMN_NAME_LOCATION_LAT, DBContract.DemoTable.COLUMN_NAME_LOCATION_LONG},
                null, null, null, null, null);

        
        results = new ArrayList();
        //iterate over cursor, pull necessary data, concat, print to screen
        //[1]
        if (mSQLCursor != null) {
            if (mSQLCursor.moveToFirst()) {
                do {
                    String res = "id: " + mSQLCursor.getString(mSQLCursor.getColumnIndex("_id")) + "\n" +
                            "string: " + mSQLCursor.getString(mSQLCursor.getColumnIndex("location_string")) + "\n"+
                            "Latitiude: " +  mSQLCursor.getString(mSQLCursor.getColumnIndex("location_lat")) + "\n" +
                            "Longtiude: " + mSQLCursor.getString(mSQLCursor.getColumnIndex("location_long"))  + "\n";
                    results.add(res);
                } while (mSQLCursor.moveToNext());
            }
        }

        ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(this, R.layout.activity_display_sql, results);
        ListView sqlList = (ListView) findViewById(R.id.sql_list_view);
        sqlList.setAdapter(newAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

//references
//[1]:https://stackoverflow.com/questions/10723770/whats-the-best-way-to-iterate-an-android-cursor