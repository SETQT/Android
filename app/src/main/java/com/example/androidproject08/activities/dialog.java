package com.example.androidproject08.activities;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.androidproject08.R;

public class dialog {
    // 2 objects activity and dialog
    private Activity activity;
    private AlertDialog dialog;

    // constructor of dialog class
    // with parameter activity
    public dialog(Activity myActivity) {
        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingdialog() {

        // adding ALERT Dialog builder object and passing activity as parameter
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    // dismiss method
    public void dismissdialog() {
        dialog.dismiss();
    }
}
