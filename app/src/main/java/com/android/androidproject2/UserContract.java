package com.android.androidproject2;

import android.provider.BaseColumns;

public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String KEY_TITLE = "title";
        public static final String KEY_STIME = "stime";
        public static final String KEY_ETIME = "etime";
        public static final String KEY_LOCATION = "location";
        public static final String KEY_MEMO = "memo";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_STIME + TEXT_TYPE + COMMA_SEP +
                KEY_ETIME + TEXT_TYPE + COMMA_SEP +
                KEY_LOCATION + TEXT_TYPE + COMMA_SEP +
                KEY_MEMO + TEXT_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
