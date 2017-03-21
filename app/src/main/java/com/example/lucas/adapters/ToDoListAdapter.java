package com.example.lucas.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lucas.tasks.R;
import com.example.lucas.tasks.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkm02 on 3/21/2017.
 */

public class ToDoListAdapter extends ArrayAdapter<TodoItem> {

    private Context mContex;
    private List<TodoItem> mList;

    public ToDoListAdapter(List<TodoItem> mList, Context context) {
        super(context, 0 , mList);
        this.mList = mList;
        this.mContex = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);
        //Check if an existing view is being reused, else inflate the view
        LayoutInflater inflater = (LayoutInflater) mContex.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null);

        TextView mTextview = (TextView) view.findViewById(R.id.list_view_textview);
        mTextview.setText(item.getText());

        CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.list_view_checkbox);

        if (item.getChecked()) {
            mCheckBox.setChecked(true);
        }

        return view;
    }

    @Override
    //add items to our list
    public void add(TodoItem item) {
        mList.add(item);
        notifyDataSetChanged();
    }
}
