package com.example.lucas.tasks;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.adapters.DividerItemDecoration;
import com.example.lucas.adapters.GridAdapter;
import com.example.lucas.fragments.CreateListDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity implements CreateListDialogFragment.CreateTaskListener,
            GridAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    private GridAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<String> myLists = new ArrayList<>();
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        //Toolbar shenanigans
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DialogFragment dialogFragment = new CreateListDialogFragment();
                dialogFragment.show(fm, "task");
            }
        });

        //Obtain our database
        db = new DbHelper(this.getApplicationContext());

        //Check if there is anything in our table. If so, obtain our items
        if (db.getAllLists().size() != 0) {
            myLists = db.getAllLists();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.grid_recycler);

        //Use a GridLayout Manager
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //Specify adapter to use
        mAdapter = new GridAdapter(myLists, GridActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    // Opens list user choose to view
    public void listItemClick(View view) {
        TextView textView = (TextView) view.findViewById(R.id.card_txtView);
        String listName = textView.getText().toString();
        Intent intent = new Intent(this, TodoActivity.class);
        intent.putExtra("LIST_NAME", listName);
        startActivity(intent);
    }

    // Asks the user if they would like to delete their selected list and does so if prompted
    public void listItemLongClick(View view, final int position) {
        TextView textView = (TextView) view.findViewById(R.id.card_txtView);
        final String listName = textView.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(GridActivity.this);
        String title = GridActivity.this.getString(R.string.delete_list_dialog_title);
        builder.setTitle(title + " " +  listName);
        builder.setMessage(R.string.delete_list_dialog_message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.deleteList(position);
                db.deleteList(listName);
            }
        })
                .setNegativeButton("No", null);
        builder.create().show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        CreateListDialogFragment dialogFragment = (CreateListDialogFragment) dialog;
        String newList = dialogFragment.getEditTextView();
//        newlist = newList.substring(0,1).toUpperCase() + newList.substring()
        if (!newList.equals("")) {
            mAdapter.addList(newList);
            db.addList(newList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(GridActivity.this);
            dialog.setTitle("Delete All?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.deleteAllLists();
                    db.deleteAllLists();
                }
            });
            dialog.setNegativeButton("No", null);
            dialog.create().show();
        }
        return super.onOptionsItemSelected(item);
    }


}
