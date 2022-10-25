package com.example.androidproject08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {
    GridView gridview;
    int logos[] = {R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.mono1, R.drawable.ao2};
    String names[] = {"Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you",
            "Áo khoác cực chất","Waiting for you"};
    String costs[] = {"đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000"};
    String costs_sale[] = {"đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000"};
    String percent_sale[] = {"-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        gridview = (GridView) findViewById(R.id.gridview);

        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getApplicationContext(), logos, names, costs, costs_sale, percent_sale);
        gridview.setAdapter(customAdapter);

    }
}