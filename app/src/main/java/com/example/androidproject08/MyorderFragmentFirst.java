package com.example.androidproject08;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MyorderFragmentFirst extends Fragment{
    // this fragment shows a ListView
    activity_myorder main; View icon_back;
    TextView myorder_option_cho_xac_nhan, myorder_option_cho_lay_hang, myorder_option_dang_giao_hang, myorder_option_da_giao;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static MyorderFragmentFirst newInstance(String strArg) {
        MyorderFragmentFirst fragment = new MyorderFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_myorder) getActivity();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_myorder_layout_fragment_first, null);

        icon_back = (View) layout_first.findViewById(R.id.icon_back);

        myorder_option_cho_xac_nhan  = (TextView) layout_first.findViewById(R.id.myorder_option_cho_xac_nhan);
        myorder_option_cho_lay_hang  = (TextView) layout_first.findViewById(R.id.myorder_option_cho_lay_hang);
        myorder_option_dang_giao_hang  = (TextView) layout_first.findViewById(R.id.myorder_option_dang_giao_hang);
        myorder_option_da_giao = (TextView) layout_first.findViewById(R.id.myorder_option_da_giao);

        myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        myorder_option_cho_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                myorder_option_cho_lay_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "Cho xac nhan";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        myorder_option_cho_lay_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_cho_lay_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "Cho lay hang";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        myorder_option_dang_giao_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_cho_lay_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "Dang giao hang";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        myorder_option_da_giao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_cho_lay_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "Da giao";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(main, "Quay lai", Toast.LENGTH_SHORT).show();
            }});

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);

    }
}// class
