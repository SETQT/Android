package com.example.androidproject08;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DashboardFragmentFirst extends Fragment{

    activity_dashboard main;
    ArrayList<ListViewOptionDashboard> list = new ArrayList<ListViewOptionDashboard>();
    ArrayList<String> text = new ArrayList<>();

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static DashboardFragmentFirst newInstance(String strArg) {
        DashboardFragmentFirst fragment = new DashboardFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_dashboard) getActivity();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_dashboard_layout_fragment_first, null);

        RecyclerView recyclerView = layout_first.findViewById(R.id.dashboard_listview);
        ListTypeOptionDashboard mAdapter = new ListTypeOptionDashboard(list, new IClickItemListener() {
            @Override
            public void onClickItem(ListViewOptionDashboard l) {
                String dataSend = l.getText();
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(main.getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        dashboard_fragment_first_asynctask db_frag_1_at = new dashboard_fragment_first_asynctask(main, recyclerView, list);
//        db_frag_1_at.execute();

        text.add("Tất cả");text.add("Áo khoác");text.add("Quần");text.add("Áo"); text.add("Mũ"); text.add("Giày");

        for (int i = 0; i < text.size(); i++) {
            list.add(new ListViewOptionDashboard(text.get(i)));
        }

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }
}// class

