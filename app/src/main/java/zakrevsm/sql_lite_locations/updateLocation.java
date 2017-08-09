package zakrevsm.sql_lite_locations;

import android.*;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.tasks.OnSuccessListener;

public class updateLocation extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected FusedLocationProviderApi mFusedLocationProviderApi;
    protected GoogleApiClient mGoogleClient;
    protected int LocationPermsRes;
    protected LocationRequest mLocationRequest;
    protected LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update_location);

        //[1]:
        Button mButton = (Button)findViewById(R.id.button2);
        final EditText mEdit = (EditText) findViewById(R.id.message);
        final TextView mLat = (TextView) findViewById(R.id.mLatText);
        final TextView mLong = (TextView) findViewById(R.id.mLongText);
        mButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view) {
                        Log.d("EditString", mEdit.getText().toString());

                        addLocationToDatabase(mEdit.getText().toString(),
                                mLat.getText().toString(),
                                mLong.getText().toString());
                    }
                });

        //check for the permissions
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LocationPermsRes);
            Log.d("sql test", "requesting perms");
            return;
        }else {
            Log.d("sql test lat", "permissions have been granted");
        }
            //[2]:see references
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            //[2]
            mGoogleClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleClient.connect();

            //[3]
            mLocationListener = new LocationListener(){
                public void onLocationChanged(Location location) {
                    TextView LatText = (TextView) findViewById(R.id.mLatText);
                    TextView LongText = (TextView) findViewById(R.id.mLongText);
                    if (location != null) {
                        Log.d("sql test lat", "in on create new location found, updating");
                            updateLocation(location);
                    } else {
                        LongText.setText("No Location Available");
                    }
                }
            };


        }


    @Override
    protected void onResume() {
        Log.d("sql test lat", "resuming application");
        super.onResume();
        if (mGoogleClient == null){
            runWithoutPermissions();
        }else {
            mGoogleClient.connect();

        }
    }


    //Display this if permissions are not set in the application
    public void runWithoutPermissions(){
        TextView LatText = (TextView) findViewById(R.id.mLatText);
        TextView LongText = (TextView) findViewById(R.id.mLongText);
        LongText.setText("-123.2");
        LatText.setText(" 44.5");

    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d("sql test lat", "pausing app");

        if (mGoogleClient == null) {
            Log.d("sql test lat", "no client to disconnect");
            return;
        }

        if (mGoogleClient.isConnected() && mGoogleClient != null){
            mGoogleClient.disconnect();
        }
    }


    @Override
    //[3]
    public void onConnected(@Nullable Bundle bundle) {
        Log.d ("SLL-onconnedted" ,
                "Connected to location services");
        //[1]
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LocationPermsRes);
            Log.d("sql test", "requesting perms");
            return;
        }else {
            Log.d("sql test", "continuing without permissions");
        }
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleClient);
                if (location == null){
                    Log.d ("SLL-onconnedted" ,
                            "failed to find location");
                }else {
                    updateLocation(location);
                }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleClient,mLocationRequest,mLocationListener);

    }

    public void updateLocation(Location location) {
        TextView LatText = (TextView) findViewById(R.id.mLatText);
        TextView LongText = (TextView) findViewById(R.id.mLongText);
        if (location != null) {
            LongText.setText(String.valueOf(location.getLongitude()));
            LatText.setText(String.valueOf(location.getLatitude()));
        } else {
            LongText.setText("No Location provided");
            LatText.setText("No Location provided");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d ("SLL-onconnedted" ,
                "suspened connection to location services");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d ("SLL-onconnedted" ,
                "connection to location services failed");
    }
    //[4]
    public void addLocationToDatabase(String text, String lat, String lon) {

        DBContract.SQLiteExample mSQLLiteExample = new DBContract.SQLiteExample(this);
        SQLiteDatabase mSQLDB = mSQLLiteExample.getWritableDatabase();

        ContentValues submitValues = new ContentValues();
        submitValues.put(DBContract.DemoTable.COLUMN_NAME_LOCATION_LAT, lat);
        submitValues.put(DBContract.DemoTable.COLUMN_NAME_LOCATION_LONG, lon);
        submitValues.put(DBContract.DemoTable.COLUMN_NAME_LOCATION_STRING, text);
        mSQLDB.insert(DBContract.DemoTable.TABLE_NAME, null, submitValues);
    }

}


/*
[1]https://developer.android.com/reference/android/widget/Button.html
[2]http://classes.engr.oregonstate.edu/eecs/winter2017/cs496/module-7/permissions.html
[3]http://classes.engr.oregonstate.edu/eecs/winter2017/cs496/module-7/geolocation.html
[4]http://classes.engr.oregonstate.edu/eecs/winter2017/cs496/module-7/sqlite.html
 */