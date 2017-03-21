package com.example.lucas.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucas.adapters.ToDoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private DbHelper db;
    private ToDoListAdapter mAdapter;
    private List<TodoItem> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                mAdapter.add(new TodoItem("Hello"));
                mAdapter.add(new TodoItem("Hey"));
                db.addToDo("Hello", (String) bundle.get("LIST_NAME"));
                db.addToDo("Hey", (String) bundle.get("LIST_NAME"));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        TextView textView = (TextView) findViewById(R.id.text_view);
//        textView.setText((String) bundle.get("LIST_NAME"));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        todoList = new ArrayList<TodoItem>();
        db = new DbHelper(this.getApplicationContext());

        //TODO: change arraylist to type TodoItem
        if (db.getAllTodos((String) bundle.get("LIST_NAME")).size() != 0) {
            todoList = db.getAllTodos((String) bundle.get("LIST_NAME"));
        }

        ListView listView = (ListView) findViewById(R.id.todo_list_listview);
        mAdapter = new ToDoListAdapter(todoList, TodoActivity.this);

        listView.setAdapter(mAdapter);

    }

}
