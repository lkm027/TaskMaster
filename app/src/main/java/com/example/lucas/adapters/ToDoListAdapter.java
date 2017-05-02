package com.example.lucas.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lucas.tasks.GridActivity;
import com.example.lucas.tasks.R;
import com.example.lucas.tasks.TodoActivity;
import com.example.lucas.tasks.TodoItem;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lkm02 on 3/21/2017.
 */

public class ToDoListAdapter extends ArrayAdapter<TodoItem> {

    private Context mContext;
    private List<TodoItem> mList;
    private CheckListener listener;

    public interface CheckListener {
        public void checkboxClicked(View view);
        public void listItemLongClick(View view, int position);
    }


    public ToDoListAdapter(List<TodoItem> mList, Context context, CheckListener listener) {
        super(context, 0 , mList);
        this.mList = mList;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        // Get the data item for this position
        TodoItem item = getItem(position);

        final TextView mTextview = (TextView) convertView.findViewById(R.id.list_view_textview);
        mTextview.setText(item.getText());

        final CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.list_view_checkbox);


        mCheckBox.setChecked(item.getChecked());

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.checkboxClicked((View) v.getParent());
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.listItemLongClick(v, position);
                return true;
            }
        });

        return convertView;
    }

    @Override
    //add items to our list
    public void add(TodoItem item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    private View.OnClickListener pressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView view = (TextView) v.findViewById(R.id.list_view_textview);
            Log.d("text", view.getText().toString());
        }
    };

    //Remove all TodoItems
    public void deleteAllTodos() {
        mList.clear();
        notifyDataSetChanged();
    }
}
