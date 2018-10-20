package com.example.joe.mygrades;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CourseLists extends CursorAdapter {

    /**
     * Initializes CourseLists CursorAdapter
     * @param context reference to activity that initialized the cursorAdapter
     * @param cursor reference to the Cursor that contains the data from the database
     * @param flags determines special behavior of the CursorAdapter. Will always be
     *              0 which means the CursorAdapter shouldn't have any special behavior
     */
    public CourseLists(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    /**
     * Makes a new View to hold the data in the Cursor.
     * @param context reference to activity that initialized the cursorAdapter
     * @param cursor reference to the Cursor that contains the data from the database
     * @param viewGroup reference to the courseListView in the Main Activity
     * @return reference to the view that will be displayed in the Main Activity,
     * e.g the one that contains the data
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_course_list, viewGroup,false);
    }

    /**
     * Binds the new View to the data in the Cursor.
     * @param view new View just created
     * @param context reference to activity that initialized the cursorAdapter
     * @param cursor reference to the Cursor that contains the data from the database
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.semesterTextView)).
                setText(cursor.getString(cursor.getColumnIndex("semester")));
        ((TextView) view.findViewById(R.id.codeTextView)).
                setText(cursor.getString(cursor.getColumnIndex("code")));
        ((TextView) view.findViewById(R.id.gradeTextView)).
                setText(cursor.getString(cursor.getColumnIndex("grade")));
    }
}