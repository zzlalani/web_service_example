package com.zeeshanlalani.customlistexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/28/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentApplication";

    private static final String TABLE_STUDENTS = "students";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_table = "CREATE TABLE " + TABLE_STUDENTS + "("+
                " id TEXT PRIMARY KEY,"+
                " firstName TEXT," +
                " lastName TEXT" + ")";
        sqLiteDatabase.execSQL(create_table);

    }

    public void addStudent(String id, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("firstName", firstName);
        values.put("lastName", lastName);

        // Inserting Row

        db.insert(TABLE_STUDENTS, null, values);
        db.close(); // Closing database connection
    }

    public List<Person> getAllStudents() {
        List<Person> students = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(cursor.getString(0), cursor.getString(1));
                students.add(person);
            } while (cursor.moveToNext());
        }

        return students;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
