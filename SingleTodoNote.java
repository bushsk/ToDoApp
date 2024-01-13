package com.example.todo;

import android.provider.BaseColumns;

public class SingleTodoNote implements BaseColumns {
    public static final String TABLE_NAME = "Todo_TABLE";
    public static final String COLUMN_TEXT = "COLUMN_TEXT";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( " +
             _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TEXT + " TEXT " + " ) ";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME ;



}
