package zakrevsm.sql_lite_locations;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
//[1]
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView listViewItem = (TextView) findViewById(R.id.Layout1);
        listViewItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, updateLocation.class);
                startActivity(intent);
            }
        });

        TextView sqlDisplay = (TextView) findViewById(R.id.Layout2);
        sqlDisplay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplaySQL.class);
                startActivity(intent);
            }
        });




    }

}

//references:
//[1]:http://classes.engr.oregonstate.edu/eecs/winter2017/cs496/module-6/basic-activity.html
