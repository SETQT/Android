package com.example.androidproject08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class activity_view_product extends AppCompatActivity {
    private ArrayList<Type_View_Product> listType = new ArrayList<Type_View_Product>();
    private ListTypeProductAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        // getting reference of  ExpandableTextView
        ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);

// calling setText on the ExpandableTextView so that
// text content will be  displayed to the user
        expTv.setText(getString(R.string.expandable_text));
        RecyclerView recyclerView = findViewById(R.id.recyclerView_view_product);
        mAdapter = new ListTypeProductAdapter(listType);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();

    }
    private void prepareMovieData() {
        Type_View_Product type = new Type_View_Product("Đen");
        listType.add(type);
        type = new Type_View_Product("Đỏ");
        listType.add(type);
        type = new Type_View_Product("Trắng");
        listType.add(type);
        mAdapter.notifyDataSetChanged();
    }
}