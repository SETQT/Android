package com.example.androidproject08;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DashboardFragmentFirst extends Fragment {

    activity_dashboard main;
    Button dashboard_option_tatca, dashboard_option_aokhoac, dashboard_option_mu, dashboard_option_quan, dashboard_option_ao;


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
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);

    }
}// class

