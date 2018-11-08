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
import android.widget.EditText;
import android.widget.Toast;

public class CreateCourse extends AppCompatActivity {

    // Declaring intent
    Intent intent;

    // Declare editTexts - used to reference the EditTexts in the resource file
    EditText nameEditText;
    EditText semesterEditText;
    EditText codeEditText;
    EditText gradeEditText;

    // declare DBHandler
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        semesterEditText = (EditText) findViewById(R.id.semesterEditText);
        codeEditText = (EditText) findViewById(R.id.codeEditText);
        gradeEditText = (EditText) findViewById(R.id.gradeEditText);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);
    }
    /**
     * This method sets the Action Bar of the Create Course Activity to whatever
     * is defined in the menu create course menu resource
     * @param menu Menu object
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // set the Action Bar of the Create Course Activity to whatever is defined
        // in the menu create course menu resource
        getMenuInflater().inflate(R.menu.menu_create_course, menu);
        return true;
    }

    /**
     *  This method gets called when an item in the overflow menu is selected
     * @param item MenuItem object that contains information about the item
     *             selected in the overflow; for example, its id
     * @return true if an item is selected, else false
     */
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
                // initialize an Intent for the Create List Activity, start intent,
                // return true if the id in the item selected is for the Create List Activity
                intent = new Intent(this, CreateCourse.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add_course menu item gets pushed
     * @param menuItem because the add_course item that calls this method is
     *                 a menu item, we must pass the method a MenuItem
     */
    public void createCourse(MenuItem menuItem){

        // get data input in EditTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String semester = semesterEditText.getText().toString();
        String code = codeEditText.getText().toString();
        String grade = gradeEditText.getText().toString();

        // trim Strings and see if any are equal to an empty String
        if(name.trim().equals("") || semester.trim().equals("")){
            // required data hasn't been input, so display Toast
            Toast.makeText(this, "Please enter a name and a semester!", Toast.LENGTH_LONG).show();
        } else if(code.trim().equals("") && grade.trim().equals("")){
            // required data has been input, update database and display a different Toast
            dbHandler.addCourse(name, semester, " ", " ");
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();
        } else if(!code.trim().equals("") && grade.trim().equals("")){
            // required data has been input + 1 unrequired data, update database and display a different Toast
            dbHandler.addCourse(name, semester, code, " ");
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();
        } else if(code.trim().equals(" ") && !grade.trim().equals("")){
            // required data has been input + 1 unrequired data, update database and display a different Toast
            dbHandler.addCourse(name, semester, "", grade);
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();
        } else{
            // all data has been input, update database and display a different Toast
            dbHandler.addCourse(name, semester, code, grade);
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();
        }

        // returns to Main Activity if Course is deleted
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
