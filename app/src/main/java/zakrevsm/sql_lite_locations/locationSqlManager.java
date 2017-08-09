package zakrevsm.sql_lite_locations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Matt on 8/6/2017.
 */

class DBContract {

private DBContract(){};

        //[1]
        public final class DemoTable implements BaseColumns {
            public static final String DB_NAME = "location_db";
            public static final String TABLE_NAME = "locations";
            public static final String COLUMN_NAME_LOCATION_STRING = "location_string";
            public static final String COLUMN_NAME_LOCATION_LAT = "location_lat";
            public static final String COLUMN_NAME_LOCATION_LONG = "location_long";
            public static final int DB_VERSION = 4;

            //[1]
            public static final String SQL_CREATE_DEMO_TABLE = "CREATE TABLE " +
                    DemoTable.TABLE_NAME + "(" + DemoTable._ID + " INTEGER PRIMARY KEY NOT NULL," +
                    DemoTable.COLUMN_NAME_LOCATION_STRING + " VARCHAR(255)," +
                    DemoTable.COLUMN_NAME_LOCATION_LAT + " VARCHAR(255)," +
                    DemoTable.COLUMN_NAME_LOCATION_LONG + " VARCHAR(255));";

            //[1]
            public static final String SQL_DROP_DEMO_TABLE = "DROP TABLE IF EXISTS " + DemoTable.TABLE_NAME;
        }



    static class SQLiteExample extends SQLiteOpenHelper {

        //[1]
        public SQLiteExample(Context context) {
            super(context, DBContract.DemoTable.DB_NAME, null, DBContract.DemoTable.DB_VERSION);
        }
        //[1]
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBContract.DemoTable.SQL_CREATE_DEMO_TABLE);
        }
        //[1]
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DBContract.DemoTable.SQL_DROP_DEMO_TABLE);
            onCreate(db);
        }

    }

}

//[1]:http://classes.engr.oregonstate.edu/eecs/winter2017/cs496/module-7/sqlite.html