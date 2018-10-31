package com.example.joe.mygrades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    // Declaring intent - used to start Activities
    Intent intent;

    // declare a DBHandler - used to communicate with the database
    DBHandler dbHandler;

    // declare a CourseLists CursorAdapter - used to link the data in the Cursor
    // to the ListView
    CourseLists courseListsAdapter;

    // declare a ListView - used to reference the ListView in the resource file
    ListView courseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // Initializes CourseLists CursorAdapter with the shopping list data in the database
        courseListsAdapter = new CourseLists(this, dbHandler.getCourseLists(), 0);

        // initialize ListView
        courseListView = (ListView) findViewById(R.id.courseListView);

        // set CourseLists CursorAdapter on ListView
        courseListView.setAdapter(courseListsAdapter);

        // register OnItemClickLister on ListView
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // return true if the id in the item selected is for the Create Course Activity
                intent = new Intent(MainActivity.this, ViewCourse.class);

                // put the course list id of the clicked row in the intent
                intent.putExtra("_id", id);

                // start the intent
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the item selected
        switch(item.getItemId()){
            case R.id.action_home :
                // initialize an Intent for the Main Activity, start intent,
                // return true if the id in the item selected is for the Main Activity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_course:
                // initialize an Intent for the Create Course Activity, start intent,
                // return true if the id in the item selected is for the Create Course Activity
                intent = new Intent(this, CreateCourse.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAddCourse(View view){
        // return true if the id in the item selected is for the Create Course Activity
        intent = new Intent(this, CreateCourse.class);
        startActivity(intent);
    }
}
