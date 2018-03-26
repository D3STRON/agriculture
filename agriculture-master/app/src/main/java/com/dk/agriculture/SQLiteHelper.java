package com.dk.agriculture;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Agriculture";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user ( phone TEXT, location TEXT, name TEXT );";
        db.execSQL(CREATE_USER_TABLE);
        Log.d(TAG, "user table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public void addUserInfo(String location, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO user VALUES('"+phone+"','"+location+"','"+name+"');";
        db.execSQL(insert);
        Log.d(TAG, "User info inserted sucessfully");
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public boolean isUserExist(String phone) {
        String select = "SELECT * FROM user WHERE phone = '"+phone+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public HashMap<String, String> getAllValues() {
        HashMap<String, String> values = new HashMap<>();
        String select = "SELECT * FROM user;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            values.put("phone", cursor.getString(0));
            values.put("location", cursor.getString(1));
            values.put("name", cursor.getString(2));
        } else {
            Log.e(TAG,"CRITICAL ERROR!!");
            return null;
        }
        cursor.close();
        Log.d(TAG,"Fetching user: " + values.toString());
        return  values;
    }

}