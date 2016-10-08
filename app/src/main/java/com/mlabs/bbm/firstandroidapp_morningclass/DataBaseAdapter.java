package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Adrianne on 9/23/2016.
 */
public class DataBaseAdapter extends SQLiteOpenHelper {

    private static final String TAG = DataBaseAdapter.class.getSimpleName();

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USER = "users";

    private static final String ID = "userId";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String UserName = "uname";
    private static final String FirstName = "fname";
    private static final String LastName = "lname";



    public DataBaseAdapter(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + ID + "INTEGER PRIMARY KEY, "
                + EMAIL + " TEXT UNIQUE," + PASSWORD + " TEXT," + UserName + " TEXT UNIQUE," + FirstName + " TEXT,"
                + LastName + " TEXT"+")";

        sqlDB.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        //Recreate Table
        onCreate(db);

    }

    public void registerUser(String email, String password, String username, String firstname, String lastname) {
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        values.put(UserName, username);
        values.put(FirstName, firstname);
        values.put(LastName, lastname);


        //long id = db.insert(TABLE_USER, null, values);

        db.insert(TABLE_USER, null, values);
        db.close();
    }


    public boolean validateUserFromEmail(String emailAdd, String password) {
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQueryFromEmail = "SELECT * FROM " + TABLE_USER + " WHERE " + EMAIL + " = '" + emailAdd +"' AND " + PASSWORD + " = '" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorEmail = db.rawQuery(selectQueryFromEmail, null);

        cursorEmail.moveToFirst();
        if (cursorEmail.getCount() > 0) {
            cursorEmail.close();
            db.close();
            return true;
        }

        cursorEmail.close();
        db.close();
        return false;


    }

    public boolean validateUserFromUName(String userName, String password) {
        String selectQueryFromUsername = "SELECT * FROM " + TABLE_USER + " WHERE " + UserName + " = '" + userName +"' AND " + PASSWORD + " = '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorUserName = db.rawQuery(selectQueryFromUsername, null);

        cursorUserName.moveToFirst();
        if (cursorUserName.getCount() > 0) {
            cursorUserName.close();
            db.close();
            return true;
        }
        cursorUserName.close();
        db.close();
        return false;
    }


    public boolean validateEmail(String emailAdd) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + EMAIL + " = '" + emailAdd +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean validateUserName(String userName) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + UserName + " = '" + userName +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
