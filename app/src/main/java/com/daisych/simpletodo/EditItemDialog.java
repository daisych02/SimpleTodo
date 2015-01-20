package com.daisych.simpletodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;
import android.widget.Toast;
// ...

public class EditItemDialog extends DialogFragment {

    public interface EditItemDialogListener {
        void onFinishEditDialog(Intent i);
    }

    private EditText etItemEdit;
    int pos;
    String item;
    EditItemDialogListener editItemDialogListener;

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editItemDialogListener = (EditItemDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onFinishEditDialog");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        etItemEdit = (EditText) view.findViewById(R.id.etItemEdit);
        pos = getArguments().getInt("index");
        String item = getArguments().getString("item");
        etItemEdit.setText(item);

        String title = getArguments().getString("title", "Edit Item");
        getDialog().setTitle(title);

        Button button = (Button) view.findViewById(R.id.btnSave);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View parent = (View) view.getParent();
                Intent i = new Intent();
                EditText etItemEdit = (EditText) parent.findViewById(R.id.etItemEdit);

                i.putExtra("item", etItemEdit.getText().toString());
                i.putExtra("index", pos);
                editItemDialogListener.onFinishEditDialog(i);
                dismiss();
            }
        });

        return view;
    }

}