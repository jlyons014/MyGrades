package com.example.joe.mygrades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateCourse extends AppCompatActivity {

    // Declaring intent
    Intent intent;

    // Declare editTexts - used to reference the EditTexts in the resource file
    EditText nameEditText;
    EditText semesterEditText;
    EditText yearEditText;
    EditText codeEditText;
    EditText gradeEditText;

    // Spinner for Semester and Code
    Spinner spinner1;
    Spinner spinner2;

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
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        codeEditText = (EditText) findViewById(R.id.codeEditText);
        gradeEditText = (EditText) findViewById(R.id.gradeEditText);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // Spinner for Semester
        spinner1 = (Spinner) findViewById(R.id.spinnerSemester);
        // Spinner for Grade
        spinner2 = (Spinner) findViewById(R.id.spinnerGrade);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.grade_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        //String semester = semesterEditText.getText().toString();
        String semester = spinner1.getSelectedItem().toString();
        String year = yearEditText.getText().toString();
        String code = codeEditText.getText().toString();
        //String grade = gradeEditText.getText().toString();
        String grade = spinner2.getSelectedItem().toString();

        int i = 0;
        int counter = 0;

        for (i = 0; i < 4; i++)
            if (!Character.isLetter(code.charAt(i)))
                counter++;

        for (i = 4; i < 7; i++){
            if (!Character.isDigit(code.charAt(i)))
                counter++;}




        // trim Strings and see if any are equal to an empty String
        if (counter >= 1) {
            Toast.makeText(this, "Incorrect code format! Must be in [AAAA123] format", Toast.LENGTH_LONG).show();
            counter = 0;

            for (i = 0; i < 4; i++)
                if (!Character.isLetter(code.charAt(i)))
                    counter++;

            for (i = 4; i < 7; i++)
                if (!Character.isDigit(code.charAt(i)))
                    counter++;
        }
        else if(name.trim().equals("") || semester.equalsIgnoreCase("--Select--")){
            // required data hasn't been input, so display Toast
            Toast.makeText(this, "Please enter a name and a semester!", Toast.LENGTH_LONG).show();
        } else if(code.trim().equals("") && year.trim().equals("") && grade.equalsIgnoreCase("--Select--")){
            // required data has been input, update database and display a different Toast
            dbHandler.addCourse(name, semester,"", "", " ");
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();

            // returns to Main Activity
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(!code.trim().equals("") && year.trim().equals("") && grade.equalsIgnoreCase("--Select--")){
            // required data has been input + 1 unrequired data, update database and display a different Toast
            dbHandler.addCourse(name, semester,"", code, " ");
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();

            // returns to Main Activity
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(code.trim().equals(" ") && !year.trim().equals("") && grade.equalsIgnoreCase("--Select--")) {

            // required data has been input + 1 unrequired data, update database and display a different Toast
            dbHandler.addCourse(name, semester, year, "", " ");
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();

            // returns to Main Activity
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(code.trim().equals(" ") && year.trim().equals("") && !grade.equalsIgnoreCase("--Select--")){
            // required data has been input + 1 unrequired data, update database and display a different Toast
            dbHandler.addCourse(name, semester,"", "", grade);
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();

            // returns to Main Activity
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            // all data has been input, update database and display a different Toast
            dbHandler.addCourse(name, semester, year, code, grade);
            Toast.makeText(this, "Course Added!", Toast.LENGTH_LONG).show();

            // returns to Main Activity
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);



        }
    }

}
