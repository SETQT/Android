package com.example.androidproject08;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_dashboard main;
    TextView textIdUser;
    GridView GridProduct;

    ArrayList<Integer> logos = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> costs = new ArrayList<>();
    ArrayList<String> costs_sale = new ArrayList<>();
    ArrayList<String> percents_sale = new ArrayList<>();


    public static DashboardFragmentSecond newInstance(String strArg1) {
        DashboardFragmentSecond fragment = new DashboardFragmentSecond();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (activity_dashboard) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_dashboard_layout_fragment_second, null);

        GridProduct = (GridView) layout_second.findViewById(R.id.dashboard_gridview);

        logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
        logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
        logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
        logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
        names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
        names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
        names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
        names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
        costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
        costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
        costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
        costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
        costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
        costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
        costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
        costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
        percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
        percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
        percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
        percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");


        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getActivity(), logos, names, costs, costs_sale, percents_sale);
        GridProduct.setAdapter(customAdapter);

        try {
            Bundle arguments = getArguments();
            textIdUser.setText(arguments.getString("arg1", ""));
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);

        if (strValue == "Tất cả") {
            names.clear(); costs.clear(); costs_sale.clear(); percents_sale.clear(); logos.clear();

            logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");

        }

        if (strValue == "Áo khoác") {
            names.clear(); costs.clear(); costs_sale.clear(); percents_sale.clear(); logos.clear();

            logos.add(R.drawable.ao2);logos.add(R.drawable.ao1);logos.add(R.drawable.ao1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-30%");
        }

        if (strValue == "Quần") {
            names.clear(); costs.clear(); costs_sale.clear(); percents_sale.clear(); logos.clear();

            logos.add(R.drawable.ao2);logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.ao2);
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");

        }

        if (strValue == "Áo") {
            names.clear(); costs.clear(); costs_sale.clear(); percents_sale.clear(); logos.clear();

            logos.add(R.drawable.ao2);logos.add(R.drawable.ao1);logos.add(R.drawable.ao1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-40%");
        }

        if (strValue == "Mũ") {
            names.clear(); costs.clear(); costs_sale.clear(); percents_sale.clear(); logos.clear();

            logos.add(R.drawable.mono1);logos.add(R.drawable.ao1);logos.add(R.drawable.ao1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.ao2);logos.add(R.drawable.ao1);logos.add(R.drawable.ao1);logos.add(R.drawable.ao2);
            logos.add(R.drawable.mono1);logos.add(R.drawable.ao2);
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");names.add("Áo khoác");names.add("Áo thun");
            names.add("Áo khoác cực chất");names.add("Áo thun");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");costs.add("đ300.000");costs.add("đ200.000");
            costs.add("đ500.000");costs.add("đ200.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");costs_sale.add("đ100.000");costs_sale.add("đ100.000");
            costs_sale.add("đ300.000");costs_sale.add("đ200.000");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-40%");
            percents_sale.add("-30%");percents_sale.add("-30%");percents_sale.add("-50%");percents_sale.add("-20%");
            percents_sale.add("-30%");percents_sale.add("-40%");
        }

        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getActivity(), logos, names, costs, costs_sale, percents_sale);
        GridProduct.setAdapter(customAdapter);

    }


}
