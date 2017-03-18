package com.example.lucas.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lucas.tasks.R;

/**
 * Created by lkm02 on 3/17/2017.
 */

public class CreateListDialogFragment extends DialogFragment {

    //TaskDialog listener that allows the entered information to be accessible to the activity
    public interface CreateTaskListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    CreateTaskListener mListener;
    EditText mEditTextView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Create view for the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_list_dialog, null);
        builder.setView(view);

        //Identify editTextView
        mEditTextView = (EditText) view.findViewById(R.id.edit_txt_dialog_task);

        builder.setTitle("Add a new Task:");
        builder.setMessage("What do you want to do next?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(CreateListDialogFragment.this);
                    }
                })
                .setNegativeButton("No", null);
        return builder.create();
    }

    //delivers click events to the activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (CreateTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
            " must implement CreateTask Listener");
        }
    }

    public String getEditTextView() {
        return String.valueOf(mEditTextView.getText());
    }

}