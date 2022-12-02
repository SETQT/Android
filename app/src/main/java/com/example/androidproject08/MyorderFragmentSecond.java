package com.example.androidproject08;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MyorderFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_myorder main;
    TextView textIdUser;
    ListView listMyOrder;
    ArrayList<Myorder> MyOrderArray = new ArrayList<Myorder>();

    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> size = new ArrayList<>();
    ArrayList<String> oldCost = new ArrayList<>();
    ArrayList<String> newCost = new ArrayList<>();
    ArrayList<String> count = new ArrayList<>();
    ArrayList<String> total = new ArrayList<>();

    public static MyorderFragmentSecond newInstance(String strArg1) {
        MyorderFragmentSecond fragment = new MyorderFragmentSecond();
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
        main = (activity_myorder) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_myorder_layout_fragment_second, null);

        listMyOrder = (ListView) layout_second.findViewById(R.id.myorder_listview);

        image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);
        name.add("Áo khoác cực chất");name.add("Áo thun");name.add("Áo khoác");name.add("Áo thun");
        size.add("Size M, Đen");size.add("Size M, Đen");size.add("Size M, Đen");size.add("Size M, Đen");
        oldCost.add("đ500.000");oldCost.add("đ200.000");oldCost.add("đ300.000");oldCost.add("đ200.000");
        newCost.add("đ300.000");newCost.add("đ200.000");newCost.add("đ100.000");newCost.add("đ100.000");
        count.add("Số lượng: 1");count.add("Số lượng: 1");count.add("Số lượng: 2");count.add("Số lượng: 1");
        total.add("đ300.000");total.add("đ200.000");total.add("đ200.000");total.add("đ100.000");

        for (int i = 0; i < image.size(); i++) {
//            MyOrderArray.add(new Myorder(i, image.get(i), name.get(i), size.get(i), oldCost.get(i), newCost.get(i), count.get(i), total.get(i)));
        }

        CustomMyorderListViewAdapter myAdapter = new CustomMyorderListViewAdapter(getActivity(), R.layout.custom_voucher_listview, MyOrderArray);
        listMyOrder.setAdapter(myAdapter);

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

        if (strValue == "Cho xac nhan") {
            name.clear(); size.clear(); count.clear(); oldCost.clear();newCost.clear(); image.clear(); total.clear();

            image.add(R.drawable.ao1);image.add(R.drawable.mono1);image.add(R.drawable.ao1);
            name.add("Áo khoác cực chất");name.add("Áo thun");name.add("Áo khoác");
            size.add("Size M, Trắng");size.add("Size M, Đen");size.add("Size M, Trắng");
            oldCost.add("đ400.000");oldCost.add("đ200.000");oldCost.add("đ300.000");
            newCost.add("đ200.000");newCost.add("đ100.000");newCost.add("đ100.000");
            count.add("Số lượng: 1");count.add("Số lượng: 1");count.add("Số lượng: 2");
            total.add("đ200.000");total.add("đ100.000");total.add("đ200.000");

            MyOrderArray.clear();

            for (int i = 0; i < image.size(); i++) {
//                MyOrderArray.add(new Myorder(i, image.get(i), name.get(i), size.get(i), oldCost.get(i), newCost.get(i), count.get(i), total.get(i)));
            }
        }
        if (strValue == "Cho lay hang") {
            name.clear(); size.clear(); count.clear(); oldCost.clear();newCost.clear(); image.clear(); total.clear();

            image.add(R.drawable.mono1);image.add(R.drawable.ao1);image.add(R.drawable.ao2);
            name.add("Áo khoác cực chất");name.add("Áo thun");name.add("Áo khoác");
            size.add("Size L, Trắng");size.add("Size M, Trắng");size.add("Size M, Đen");
            oldCost.add("đ400.000");oldCost.add("đ200.000");oldCost.add("đ300.000");
            newCost.add("đ200.000");newCost.add("đ100.000");newCost.add("đ100.000");
            count.add("Số lượng: 1");count.add("Số lượng: 1");count.add("Số lượng: 2");
            total.add("đ200.000");total.add("đ100.000");total.add("đ200.000");

            MyOrderArray.clear();

            for (int i = 0; i < image.size(); i++) {
//                MyOrderArray.add(new Myorder(i, image.get(i), name.get(i), size.get(i), oldCost.get(i), newCost.get(i), count.get(i), total.get(i)));
            }
        }

        if (strValue == "Dang giao hang") {
            name.clear(); size.clear(); count.clear(); oldCost.clear();newCost.clear(); image.clear(); total.clear();

            image.add(R.drawable.ao1);image.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            size.add("Size M, Đen");size.add("Size L, Trắng");
            oldCost.add("đ400.000");oldCost.add("đ200.000");
            newCost.add("đ200.000");newCost.add("đ100.000");
            count.add("Số lượng: 1");count.add("Số lượng: 3");
            total.add("đ200.000");total.add("đ300.000");

            MyOrderArray.clear();

            for (int i = 0; i < image.size(); i++) {
//                MyOrderArray.add(new Myorder(i, image.get(i), name.get(i), size.get(i), oldCost.get(i), newCost.get(i), count.get(i), total.get(i)));
            }

        }

        if (strValue == "Da giao") {
            name.clear(); size.clear(); count.clear(); oldCost.clear();newCost.clear(); image.clear(); total.clear();

            image.add(R.drawable.ao2);image.add(R.drawable.mono1);image.add(R.drawable.ao1);
            name.add("Áo khoác cực chất");name.add("Áo thun");name.add("Áo khoác");
            size.add("Size M, Trắng");size.add("Size L, Đen");size.add("Size M, Trắng");
            oldCost.add("đ400.000");oldCost.add("đ200.000");oldCost.add("đ300.000");
            newCost.add("đ200.000");newCost.add("đ100.000");newCost.add("đ200.000");
            count.add("Số lượng: 2");count.add("Số lượng: 5");count.add("Số lượng: 2");
            total.add("đ400.000");total.add("đ500.000");total.add("đ400.000");

            MyOrderArray.clear();

            for (int i = 0; i < image.size(); i++) {
//                MyOrderArray.add(new Myorder(i, image.get(i), name.get(i), size.get(i), oldCost.get(i), newCost.get(i), count.get(i), total.get(i)));
            }

        }

        CustomMyorderListViewAdapter myAdapter = new CustomMyorderListViewAdapter(getActivity(), R.layout.custom_voucher_listview, MyOrderArray);
        listMyOrder.setAdapter(myAdapter);

    }


}
