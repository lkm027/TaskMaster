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
    public static final String KEY_CHECKED = "Checked";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase table) {
        String CREATE_LIST_TABLE= "CREATE TABLE " + LIST_TABLE + "(" +
                KEY_LIST_NAME + " TEXT)";

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TODO_TABLE + "(" +
                KEY_TASK + " TEXT," + KEY_PARENT + " TEXT," + KEY_CHECKED + " TEXT)";

        table.execSQL(CREATE_TASKS_TABLE);
        table.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase table, int oldVersion, int newVersion) {
        table.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        table.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
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

    // Add a new task to a list
    public void addToDo(String todo, String listName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PARENT, listName);
        values.put(KEY_TASK, todo);
        values.put(KEY_CHECKED, "no");
        db.insert(TODO_TABLE, null, values);
        db.close();
    }

    public void updateCheckValue(String todo, String listName, boolean isChecked) {
        String checked = "no";
        if (isChecked) {
            checked = "checked";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PARENT, listName);
        values.put(KEY_TASK, todo);
        values.put(KEY_CHECKED, checked);
        db.update(TODO_TABLE, values, "Task = ? AND Parent = ?", new String[] {todo, listName});
    }

    //Retrieve values for a certain list
    public List<TodoItem> getAllTodos(String listName) {
        List<TodoItem> list = new ArrayList<>();

        String query = "SELECT * FROM " + TODO_TABLE + " WHERE " + KEY_PARENT + "='" + listName +"'";

        SQLiteDatabase table = this.getWritableDatabase();
        Cursor cursor = table.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem(cursor.getString(0));
                if (cursor.getString(2).equals("checked")) {
                    item.setChecked(true);
                }
                list.add(item);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    //Retrieve all lists from table
    public List<String> getAllLists() {
        List<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + LIST_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // Delete task item from a list
    public boolean deleteTodo(String item, String listName ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TODO_TABLE, "Task = ? AND Parent = ?", new String[] {item, listName}) > 0;
    }

    // Delete list from our main screen
    public boolean deleteList(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(LIST_TABLE, "List = ?", new String[] {text}) > 0;
    }





}
