package com.example.lucas.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.tasks.GridActivity;
import com.example.lucas.tasks.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.id;

/**
 * Created by lkm02 on 3/16/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private List<String> mDataset = new ArrayList<>();
    private Context mContext;

    //Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            mTextView = (TextView) v.findViewById(R.id.card_txtView);

            //set onLongClick listeners for each view
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //call
                    ((GridActivity) mContext).
                            deleteList(mTextView.getText().toString(), getPosition());
                    return true;
                }

            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridActivity) mContext).
                            continueToList(mTextView.getText().toString());
                }
            });
        }

    }

    //Constructor
    public GridAdapter(List<String> currentItems, Context mContext){
        mDataset = currentItems;
        this.mContext = mContext;
    }

    //Create new views (invoked by the layout Manager)
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_txt_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element from your dataset at this position
        //replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));
    }

    //Return the size of your dataset (invoked by manager)
    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }

    //Add another List to the List page
    public void addList(String data) {
        mDataset.add(data);
        notifyDataSetChanged();
    }

    //Delete a list from our list page
    public void deleteList(int position) {
        mDataset.remove(position);
        notifyDataSetChanged();
    }

}
