package com.android.androidproject2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG = "SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, getClass().getName() + ".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, getClass().getName() + ".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public void insertUserBySQL(String title, String Stime, String etime, String loaction, String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (NULL, '%s', '%s', '%s', '%s', '%s')",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_TITLE,
                    UserContract.Users.KEY_STIME,
                    UserContract.Users.KEY_ETIME,
                    UserContract.Users.KEY_LOCATION,
                    UserContract.Users.KEY_MEMO,
                    title,
                    Stime,
                    etime,
                    loaction,
                    memo);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public void deleteUserBySQL(String _id) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    _id);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }
    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }
}