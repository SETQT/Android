package com.example.doan;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class activity_view_product extends Activity {
    private ArrayList<Type_View_Product> listType_color = new ArrayList<Type_View_Product>();
    private ArrayList<Type_View_Product> listType_size = new ArrayList<Type_View_Product>();


    private ListTypeProductAdapter mAdapter_color;
    private ListTypeProductAdapter mAdapter_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        // getting reference of  ExpandableTextView
        ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);

// calling setText on the ExpandableTextView so that
// text content will be  displayed to the user
        expTv.setText(getString(R.string.expandable_text));


        RecyclerView recyclerView_color = findViewById(R.id.recyclerView_color_view_product);
        mAdapter_color = new ListTypeProductAdapter(listType_color);
        LinearLayoutManager mLayoutManager_color = new LinearLayoutManager(getApplicationContext());
        mLayoutManager_color.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_color.setLayoutManager(mLayoutManager_color);
        recyclerView_color.setItemAnimator(new DefaultItemAnimator());
        recyclerView_color.setAdapter(mAdapter_color);
        prepareTypeColor();

        mAdapter_size = new ListTypeProductAdapter(listType_size);
        RecyclerView recyclerView_size = findViewById(R.id.recyclerView_size_view_product);
        LinearLayoutManager mLayoutManager_size = new LinearLayoutManager(getApplicationContext());
        mLayoutManager_size.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_size.setLayoutManager(mLayoutManager_size);
        recyclerView_size.setItemAnimator(new DefaultItemAnimator());
        recyclerView_size.setAdapter(mAdapter_size);
        prepareTypeSize();


    }

    private void prepareTypeColor() {
        Type_View_Product type = new Type_View_Product("Đen");
        listType_color.add(type);
        type = new Type_View_Product("Đỏ");
        listType_color.add(type);
        type = new Type_View_Product("Trắng");
        listType_color.add(type);
        mAdapter_color.notifyDataSetChanged();

    }

    private void prepareTypeSize() {
        Type_View_Product type = new Type_View_Product("Đen");
        type = new Type_View_Product("M");
        listType_size.add(type);
        type = new Type_View_Product("L");
        listType_size.add(type);
        type = new Type_View_Product("XL");
        listType_size.add(type);
        mAdapter_size.notifyDataSetChanged();
    }
}