package com.example.joe.mygrades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Attributes;

/**
 * The DBHandler class is used to set up the mygrades database
 * and interact with it.
 */
public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for DB version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mygrades.db";

    // initialize constants for course list table
    private static final String TABLE_COURSE_LIST = "courseList";
    private static final String COLUMN_COURSE_ID = "_id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_SEMESTER = "semester";
    private static final String COLUMN_COURSE_CODE = "code";
    private static final String COLUMN_COURSE_GRADE = "grade";

    /**
     * Initializes a DBHandler. Create a version of our database
     *
     * @param context reference to the activity that initializes the DBHandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        // call superclass constructor
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates database tables
     *
     * @param sqLiteDatabase reference to the mygrades database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Define String for create statement for course list table
        String query = "CREATE TABLE " + TABLE_COURSE_LIST + "(" +
                COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_NAME + " TEXT, " +
                COLUMN_COURSE_SEMESTER + " TEXT, " +
                COLUMN_COURSE_CODE + " TEXT, " +
                COLUMN_COURSE_GRADE + " TEXT " +
                ");";

        // execute the create statement
        sqLiteDatabase.execSQL(query);
    }

    /**
     * This method gets called when a new version of the database is initialized
     *
     * @param sqLiteDatabase reference to the mygrades database
     * @param oldVersion     old version of mygrades database
     * @param newVersion     new version of mygrades database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // execute a drop statement that drops course list table from the mygrades database
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_LIST);

        // call method that creates the tables
        onCreate(sqLiteDatabase);
    }

    /**
     * This method gets called when the add course in the AddCourse action bar gets
     * clicked. It inserts a new row into the course list table.
     *
     * @param name     course name typed in by user
     * @param semester course semester typed in by the user
     * @param code     course code typed in by the user
     * @param grade    course grade typed in by the user
     */
    public void addCourse(String name, String semester, String code, String grade) {

        //get writeable reference to mygrades database
        SQLiteDatabase db = getWritableDatabase();

        // initialize an empty ContentValue object
        ContentValues values = new ContentValues();

        // put key-value pairs in the ContentValues object. The key must be
        // the name of a column and the value is the value to be inserted in
        // the column
        values.put(COLUMN_COURSE_NAME, name);
        values.put(COLUMN_COURSE_SEMESTER, semester);
        values.put(COLUMN_COURSE_CODE, code);
        values.put(COLUMN_COURSE_GRADE, grade);

        // insert values into the course list table
        db.insert(TABLE_COURSE_LIST, null, values);

        // close reference to mygrades database
        db.close();
    }

    /**
     * This method gets called when the Main Activity is created.
     *
     * @return Cursor that contains all of the rows in the course list table
     */
    public Cursor getCourseLists() {

        //get writeable reference to mygrades database
        SQLiteDatabase db = getWritableDatabase();

        // execute select statement that selects all rows from the
        // course list table and returns them as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_COURSE_LIST, null);
    }

    public String getCourseListName(int id) {
        //get writeable reference to mygrades database
        SQLiteDatabase db = getWritableDatabase();

        // initialize String that will be returned by the method
        String dbString = "";

        // define select statement that will select everything from the
        // course list table for the specified id
        String query = "SELECT * FROM " + TABLE_COURSE_LIST +
                " WHERE " + COLUMN_COURSE_ID + " = " + id;

        // execute select statement
        Cursor cursor = db.rawQuery(query, null);

        // move to the first row in the Cursor
        cursor.moveToFirst();

        // if the course list name in the Cursor isn't null
         if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            // get the course list name and store it in our String
            dbString = cursor.getString(cursor.getColumnIndex("name"));
        }

        // close our reference to the mygrades database
        db.close();

        // return course list name
        return dbString;
    }

    public String  getColumnCourseName(int id){

        SQLiteDatabase db = getWritableDatabase();

        String course = "";

        String query = "SELECT * FROM " + TABLE_COURSE_LIST + " WHERE " +
                COLUMN_COURSE_ID + " = " + id;

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("name")) != null){
            course = cursor.getString(cursor.getColumnIndex("name"));
        }


        db.close();

        return course;
    }

    public String  getColumnCourseSemester(int id){

        SQLiteDatabase db = getWritableDatabase();

        String course = "";

        String query = "SELECT * FROM " + TABLE_COURSE_LIST + " WHERE " +
                COLUMN_COURSE_ID + " = " + id;

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("semester")) != null){
            course = cursor.getString(cursor.getColumnIndex("semester"));
        }

        db.close();

        return course;
    }

    public String  getColumnCourseCode(int id){

        SQLiteDatabase db = getWritableDatabase();

        String course = "";

        String query = "SELECT * FROM " + TABLE_COURSE_LIST + " WHERE " +
                COLUMN_COURSE_ID + " = " + id;

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("code")) != null){
            course = cursor.getString(cursor.getColumnIndex("code"));
        }

        db.close();

        return course;
    }

    public String  getColumnCourseGrade(int id){

        SQLiteDatabase db = getWritableDatabase();

        String course = "";

        String query = "SELECT * FROM " + TABLE_COURSE_LIST + " WHERE " +
                COLUMN_COURSE_ID + " = " + id;

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        if (cursor.getString(cursor.getColumnIndex("grade")) != null){
            course = cursor.getString(cursor.getColumnIndex("grade"));
        }

        db.close();

        return course;
    }

    public void deleteSelectedCourse(int id) {
        //get writeable reference to mygrades database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement that will select everything from the
        // course list table for the specified id
        String query = "DELETE FROM " + TABLE_COURSE_LIST +
                " WHERE " + COLUMN_COURSE_ID + " = " + id;

        // execute select statement
        Cursor cursor = db.rawQuery(query, null);

        // move to the first row in the Cursor
        cursor.moveToFirst();

        // close our reference to the mygrades database
        db.close();
    }

    public void updateSelectedCourse(int id, String name, String semester, String code, String grade) {
        //get writeable reference to mygrades database
        SQLiteDatabase db = getWritableDatabase();

        //replacing values in column with users new values
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("semester", semester);
        cv.put("code", code);
        cv.put("grade", grade);

        //executing update statement
        db.update(TABLE_COURSE_LIST, cv, "_id="+id, null);

        //closing reference to database
        db.close();

    }
}