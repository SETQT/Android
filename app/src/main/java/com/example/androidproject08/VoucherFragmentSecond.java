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

public class VoucherFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_voucher main;
    TextView textIdUser;
    ListView listVoucher;
    ArrayList<Voucher> VoucherArray = new ArrayList<Voucher>();

    ArrayList<String> title_free_ship = new ArrayList<>();
    ArrayList<String> free_cost_free_ship = new ArrayList<>();
    ArrayList<String> date_free_ship = new ArrayList<>();
    ArrayList<Integer> image_free_ship = new ArrayList<>();

    ArrayList<String> title_shop = new ArrayList<>();
    ArrayList<String> free_cost_shop = new ArrayList<>();
    ArrayList<String> date_shop = new ArrayList<>();
    ArrayList<Integer> image_shop = new ArrayList<>();

    public static VoucherFragmentSecond newInstance(String strArg1) {
        VoucherFragmentSecond fragment = new VoucherFragmentSecond();
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
        main = (activity_voucher) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_voucher_layout_fragment_second, null);

        listVoucher = (ListView) layout_second.findViewById(R.id.voucher_listview);

        title_free_ship.add("Tất cả hình thức thanh toán");
        title_free_ship.add("Tất cả hình thức thanh toán");
        title_free_ship.add("Tất cả hình thức thanh toán");
        title_free_ship.add("Tất cả hình thức thanh toán");
        title_free_ship.add("Tất cả hình thức thanh toán");
        title_free_ship.add("Tất cả hình thức thanh toán");
        free_cost_free_ship.add("Tối đa 40k");
        free_cost_free_ship.add("Tối đa 30k");
        free_cost_free_ship.add("Tối đa 50k");
        free_cost_free_ship.add("Tối đa 70k");
        free_cost_free_ship.add("Tối đa 60k");
        free_cost_free_ship.add("Tối đa 60k");
        date_free_ship.add("HSD: 20/11/2022");
        date_free_ship.add("HSD: 30/11/2022");
        date_free_ship.add("HSD: 28/11/2022");
        date_free_ship.add("HSD: 27/11/2022");
        date_free_ship.add("HSD: 28/11/2022");
        date_free_ship.add("HSD: 30/11/2022");
        image_free_ship.add(R.drawable.free_ship);
        image_free_ship.add(R.drawable.free_ship);
        image_free_ship.add(R.drawable.free_ship);
        image_free_ship.add(R.drawable.free_ship);
        image_free_ship.add(R.drawable.free_ship);
        image_free_ship.add(R.drawable.free_ship);


        title_shop.add("Mã giảm giá đơn hàng hơn 100k");
        title_shop.add("Mã giảm giá đơn hàng hơn 200k");
        title_shop.add("Mã giảm giá đơn hàng hơn 400k");
        title_shop.add("Mã giảm giá đơn hàng hơn 500k");
        title_shop.add("Mã giảm giá đơn hàng hơn 600k");
        title_shop.add("Mã giảm giá đơn hàng hơn 800k");
        title_shop.add("Mã giảm giá đơn hàng hơn 1000k");
        free_cost_shop.add("Tối đa 40k");
        free_cost_shop.add("Tối đa 30k");
        free_cost_shop.add("Tối đa 50k");
        free_cost_shop.add("Tối đa 70k");
        free_cost_shop.add("Tối đa 60k");
        free_cost_shop.add("Tối đa 70k");
        free_cost_shop.add("Tối đa 60k");
        date_shop.add("HSD: 20/11/2022");
        date_shop.add("HSD: 30/11/2022");
        date_shop.add("HSD: 28/11/2022");
        date_shop.add("HSD: 27/11/2022");
        date_shop.add("HSD: 28/11/2022");
        date_shop.add("HSD: 30/11/2022");
        date_shop.add("HSD: 28/11/2022");
        image_shop.add(R.drawable.img_voucher);
        image_shop.add(R.drawable.img_voucher);
        image_shop.add(R.drawable.img_voucher);
        image_shop.add(R.drawable.img_voucher);
        image_shop.add(R.drawable.img_voucher);
        image_shop.add(R.drawable.img_voucher);
        image_shop.add(R.drawable.img_voucher);

        for (int i = 0; i < image_shop.size(); i++) {
            VoucherArray.add(new Voucher(i, image_shop.get(i), title_shop.get(i), free_cost_shop.get(i), date_shop.get(i)));
        }
        for (int i = 0; i < image_free_ship.size(); i++) {
            VoucherArray.add(new Voucher(i, image_free_ship.get(i), title_free_ship.get(i), free_cost_free_ship.get(i), date_free_ship.get(i)));
        }

        CustomVoucherListViewAdapter myAdapter = new CustomVoucherListViewAdapter(getActivity(), R.layout.custom_voucher_listview, VoucherArray);
        listVoucher.setAdapter(myAdapter);

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

        if (strValue == "FreeShip") {
            VoucherArray.clear();

            for (int i = 0; i < title_free_ship.size(); i++) {
                VoucherArray.add(new Voucher(i, image_free_ship.get(i), title_free_ship.get(i), free_cost_free_ship.get(i), date_free_ship.get(i)));
            }
        }
        if (strValue == "Shop") {
            VoucherArray.clear();

            for (int i = 0; i < image_shop.size(); i++) {
                VoucherArray.add(new Voucher(i, image_shop.get(i), title_shop.get(i), free_cost_shop.get(i), date_shop.get(i)));
            }
        }

        if (strValue == "All") {
            VoucherArray.clear();
            for (int i = 0; i < image_shop.size(); i++) {
                VoucherArray.add(new Voucher(i, image_shop.get(i), title_shop.get(i), free_cost_shop.get(i), date_shop.get(i)));
            }
            for (int i = 0; i < image_free_ship.size(); i++) {
                VoucherArray.add(new Voucher(i, image_free_ship.get(i), title_free_ship.get(i), free_cost_free_ship.get(i), date_free_ship.get(i)));
            }

        }
        CustomVoucherListViewAdapter myAdapter = new CustomVoucherListViewAdapter(getActivity(), R.layout.custom_voucher_listview, VoucherArray);
        listVoucher.setAdapter(myAdapter);

    }


}
