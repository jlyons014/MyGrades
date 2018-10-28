package com.example.joe.mygrades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewCourse extends AppCompatActivity {

    //declare an intent
    Intent intent;

    //declare a bundle
    Bundle bundle;

    //declare a long to store the ID passed from Main Activity
    long id;

    //declare dbHandler
    DBHandler dbHandler;



    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize a bundle that contains the id passed from Main Activity
        bundle = this.getIntent().getExtras();

        //get the id in the bundle
        id = bundle.getLong("_id", id);

        //initialize dbHandler
        dbHandler = new DBHandler(this,null);

        //call DBHandler method that gets the name of the course list
        String courseListName = dbHandler.getCourseListName((int) id);

        //set the title of the View List activity to course list name
        this.setTitle(courseListName);

        String courseName = dbHandler.getColumnCourseName((int)id);


        };

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

    public boolean onCreateOptionsMenu(Menu menu) {
        //set the action bar of the main activity to whatever is defined in the
        //menu main resource
        getMenuInflater().inflate(R.menu.menu_view_course, menu);
        return true;


    }

    public boolean onCreateOptionsMenu(MenuItem item) {
        //get the id of the item selected
        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);

                startActivity(intent);
                return true;
            case R.id.action_create_course:
                intent = new Intent(this,CreateCourse.class);
                startActivity(intent);
                return true;



            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void openCreateCourse(View view){

        intent = new Intent(this, CreateCourse.class);
        intent.putExtra("_id", id);
    }


    }


