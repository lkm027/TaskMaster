package com.example.lucas.tasks;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucas.adapters.ToDoListAdapter;
import com.example.lucas.fragments.CreateListDialogFragment;
import com.example.lucas.fragments.CreateTaskDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity implements CreateTaskDialogFragment.CreateTaskListener {

    private DbHelper db;
    private ToDoListAdapter mAdapter;
    private List<TodoItem> todoList;
    private String parent = new String();

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
                FragmentManager fm = getFragmentManager();
                DialogFragment dialogFragment = new CreateTaskDialogFragment();
                dialogFragment.show(fm, "task");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Grab the name of the list from our last activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        parent = (String) bundle.get("LIST_NAME");

        // Set the actionbar title to the list name
        getSupportActionBar().setTitle(parent);

        // Initialize arraylist and db
        todoList = new ArrayList<TodoItem>();
        db = new DbHelper(this.getApplicationContext());

        if (db.getAllTodos(parent).size() != 0) {
            todoList = db.getAllTodos(parent);
        }

        // Create adapter
        ListView listView = (ListView) findViewById(R.id.todo_list_listview);
        mAdapter = new ToDoListAdapter(todoList, TodoActivity.this);
        listView.setAdapter(mAdapter);

        // Set onLongClick listener for each task item in a list
        final String parentName = parent;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
                builder.setTitle(R.string.delete_task_dialog_title);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TodoItem item = mAdapter.getItem(position);
                        db.deleteTodo(item.getText().toString(), parentName);
                        mAdapter.remove(position);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                return true;
            }
        });

    }

    // On positive click when adding new task, add it to the adapter and db
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        CreateTaskDialogFragment dialogFragment = (CreateTaskDialogFragment) dialog;
        String newList = dialogFragment.getEditTextView();
        if (!newList.equals("")) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            TodoItem item = new TodoItem(newList);
            mAdapter.add(item);
            db.addToDo(item.getText(),(String) bundle.get("LIST_NAME"));
        }
    }

    // Called when th back button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
}

    // Overrides the back button transition so that it follows convention
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    public void isCheckedListener(Boolean isChecked, String task) {
        db.updateCheckValue(task, parent, isChecked);
    }

}
