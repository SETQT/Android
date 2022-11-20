package com.example.androidproject08;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import java.util.ArrayList;

public class DashboardFragmentFirst extends Fragment {
    public class DashboardFragmentFirst extends Fragment{

        activity_dashboard main;

        // khai báo biến UI
        Button dashboard_option_tatca, dashboard_option_aokhoac, dashboard_option_mu, dashboard_option_quan, dashboard_option_ao;

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

            dashboard_option_tatca  = (Button) layout_first.findViewById(R.id.dashboard_option_tatca);
            dashboard_option_aokhoac  = (Button) layout_first.findViewById(R.id.dashboard_option_aokhoac);
            dashboard_option_mu  = (Button) layout_first.findViewById(R.id.dashboard_option_mu);
            dashboard_option_quan  = (Button) layout_first.findViewById(R.id.dashboard_option_quan);
            dashboard_option_ao  = (Button) layout_first.findViewById(R.id.dashboard_option_ao);

            dashboard_option_tatca.setTextAppearance(getActivity(), R.style.setTextAfterClickDashBoard);
            dashboard_option_tatca.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));

            dashboard_option_tatca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboard_option_tatca.setTextAppearance(getActivity(), R.style.setTextAfterClickDashBoard);
                    dashboard_option_tatca.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                    dashboard_option_aokhoac.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_aokhoac.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_mu.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_mu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_quan.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_quan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_ao.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_ao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                    String dataSend = "Tat ca";
                    main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
                }});

            dashboard_option_aokhoac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboard_option_tatca.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_tatca.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_aokhoac.setTextAppearance(getActivity(), R.style.setTextAfterClickDashBoard);
                    dashboard_option_aokhoac.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                    dashboard_option_mu.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_mu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_quan.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_quan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_ao.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_ao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                    String dataSend = "Ao khoac";
                    main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
                }});

            dashboard_option_mu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboard_option_tatca.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_tatca.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_aokhoac.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_aokhoac.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_mu.setTextAppearance(getActivity(), R.style.setTextAfterClickDashBoard);
                    dashboard_option_mu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                    dashboard_option_quan.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_quan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_ao.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_ao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                    String dataSend = "Mu";
                    main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
                }});

            dashboard_option_quan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboard_option_tatca.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_tatca.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_aokhoac.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_aokhoac.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_mu.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_mu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    dashboard_option_quan.setTextAppearance(getActivity(), R.style.setTextAfterClickDashBoard);
                    dashboard_option_quan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                    dashboard_option_ao.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                    dashboard_option_ao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    String dataSend = "Quan";
                    main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
                }});

            dashboard_option_ao.setOnClickListener(new View.OnClickListener() {
                RecyclerView recyclerView = layout_first.findViewById(R.id.dashboard_listview);
                ListTypeOptionDashboard mAdapter = new ListTypeOptionDashboard(list, new IClickItemListener() {
                    @Override
                    public void onClick(View view) {
                        dashboard_option_tatca.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                        dashboard_option_tatca.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        dashboard_option_aokhoac.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                        dashboard_option_aokhoac.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        dashboard_option_mu.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                        dashboard_option_mu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        dashboard_option_quan.setTextAppearance(getActivity(), R.style.setTextNotClickDashBoard);
                        dashboard_option_quan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        dashboard_option_ao.setTextAppearance(getActivity(), R.style.setTextAfterClickDashBoard);
                        dashboard_option_ao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));

                        String dataSend = "Ao";
                        public void onClickItem(ListViewOptionDashboard l) {
                            String dataSend = l.getText();
                            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
                        }});
                }
            });
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(main.getApplicationContext());
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            text.add("Tất cả");text.add("Áo khoác");text.add("Quần");text.add("Áo"); text.add("Mũ");

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

