package com.example.joe.mygrades;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class UpdateCourse extends AppCompatActivity {

    NotificationManagerCompat notificationManager;
    private static final String CHANNEL_ID = "channel";

    // Declare editTexts - used to reference the EditTexts in the resource file
    EditText nameEditText;
    EditText semesterEditText;
    EditText yearEditText;
    EditText codeEditText;
    EditText gradeEditText;

    // Spinner for Semester and Code
    Spinner spinner1;
    Spinner spinner2;

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
        setContentView(R.layout.activity_update_course);
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

        // Declare and Initialize variables to hold Course details
        String courseName = dbHandler.getColumnCourseName((int)id);
        String courseSemester = dbHandler.getColumnCourseSemester((int)id);
        String courseYear = dbHandler.getColumnCourseYear((int)id);
        String courseCode = dbHandler.getColumnCourseCode((int)id);
        String courseGrade = dbHandler.getColumnCourseGrade((int)id);

        spinner1 = (Spinner) findViewById(R.id.spinnerSemester);
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

        // Set name of course in its EditText
        EditText setname = findViewById(R.id.nameEditText);
        setname.setText(courseName);

        // Set semester of course in its spinner
        spinner1.setSelection(adapter1.getPosition(courseSemester));
        //setsemester.setText(courseSemester);

        // Set year of course in its EditText
        EditText setyear = findViewById(R.id.yearEditText);
        setyear.setText(courseYear);

        // Set code of course in its EditText
        final EditText setcode = findViewById(R.id.codeEditText);
        setcode.setText(courseCode);

        // Set grade of course in its spinner
        spinner2.setSelection(adapter2.getPosition(courseGrade));

        setcode.addTextChangedListener(new TextWatcher() {
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

        // initializes notificationManager
        notificationManager = NotificationManagerCompat.from(this);
        // creates notification channel
        createNotificationChannels();

    };

    // good feedback notification
    public void sendOnChannelGood(){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_grade)
                .setContentTitle("Grade Feedback")
                .setContentText("Good Job! Keep it up!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    };

    // bad feedback notification
    public void sendOnChannelBad(){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_grade)
                .setContentTitle("Grade Feedback")
                .setContentText("Step your game up!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    };

    // notification channel to display notification
    public void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
            CHANNEL_ID,
            "Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
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
        getMenuInflater().inflate(R.menu.menu_update_course, menu);
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

    public void updateCourse(MenuItem menuItem){
        //initialize dbHandler
        dbHandler = new DBHandler(this,null);

        // Initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        //semesterEditText = (EditText) findViewById(R.id.semesterEditText);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        codeEditText = (EditText) findViewById(R.id.codeEditText);
        //gradeEditText = (EditText) findViewById(R.id.gradeEditText);

        spinner1 = (Spinner) findViewById(R.id.spinnerSemester);
        spinner2 = (Spinner) findViewById(R.id.spinnerGrade);

        String name = nameEditText.getText().toString();
        //String semester = semesterEditText.getText().toString();
        String semester = spinner1.getSelectedItem().toString();
        String year = yearEditText.getText().toString();
        String code = codeEditText.getText().toString();
        //String grade = gradeEditText.getText().toString();
        String grade = spinner2.getSelectedItem().toString();

        // checks if grade result and provide feedback based on result
        if(!dbHandler.getColumnCourseGrade((int)id).equalsIgnoreCase("--Select--") &&
                dbHandler.getColumnCourseGrade((int)id).compareTo(grade) > 0){
            sendOnChannelGood();
        }else{
            sendOnChannelBad();
        }


        //call DBHandler method to update selected course from the course list
        dbHandler.updateSelectedCourse((int) id, name, semester, year, code, grade);
        Toast.makeText(this, "Course has been updated!", Toast.LENGTH_LONG).show();


        // returns to Main Activity if Course is updated
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    }




