package com.example.lucas.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkm02 on 3/16/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    //Database
    public static final String DATABASE_NAME = "ToDoItems";
    public static final int DATABASE_VERSION = 2;

    //Tables
    public static final String LIST_TABLE = "ListsTable";
    public static final String TODO_TABLE = "TasksTable";

    //columns for lists table
    public static final String KEY_LIST_NAME = "List";

    //columns for tasks table
    public static final String KEY_TASK = "Task";
    public static final String KEY_PARENT = "Parent";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase table) {
        String CREATE_LIST_TABLE= "CREATE TABLE " + LIST_TABLE + "(" +
                KEY_LIST_NAME + " TEXT)";

        table.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase table, int oldVersion, int newVersion) {
        table.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        onCreate(table);
    }

    //Add new list to our table
    public void addList(String listName) {
        SQLiteDatabase table = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_NAME, listName);
        table.insert(LIST_TABLE, null, values);
        table.close();
    }

    //Retrieve all lists from table
    public List<String> getAllLists() {
        List<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + LIST_TABLE;

        SQLiteDatabase table = this. getWritableDatabase();
        Cursor cursor = table.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return list;
    }





}
