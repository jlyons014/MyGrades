package com.example.joe.mygrades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewList extends AppCompatActivity {

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
        setContentView(R.layout.activity_view_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //intitialize a bundle that contains the id passed from Main Activity
        bundle = this.getIntent().getExtras();

        //get the id in the bundle
        id = bundle.getLong("_id", id);

        //intitialize dbHandeler
        dbHandler = new DBHandler(this,null);

        //call DBHandler method that gets the name of the course list
        String courseListName = dbHandler.getCourseListName((int) id);

        //set the title of the View List activity to course list name
        this.setTitle(courseListName);




        };
    public boolean onCreateOptionsMenu(Menu menu) {
        //set the action bar of the main activity to whatever is defined in the
        //menu main resource
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
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


