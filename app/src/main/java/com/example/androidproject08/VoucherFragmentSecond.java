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

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> free_cost = new ArrayList<>();
    ArrayList<String> count = new ArrayList<>();
    ArrayList<String> start_date = new ArrayList<>();
    ArrayList<String> expiry_date = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();

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

        title.add("Tất cả hình thức thanh toán");
        title.add("Tất cả hình thức thanh toán");
        title.add("Tất cả hình thức thanh toán");
        free_cost.add("Tối đa 40k");
        free_cost.add("Tối đa 30k");
        free_cost.add("Tối đa 50k");
        count.add("Số lượng: 10");
        count.add("Số lượng: 2");
        count.add("Số lượng: 1");
        start_date.add("Bắt đầu: 20/11/2022");
        start_date.add("Bắt đầu: 30/11/2022");
        start_date.add("Bắt đầu: 10/11/2022");
        expiry_date.add("HSD: 12/12/2022");
        expiry_date.add("HSD: 11/12/2022");
        expiry_date.add("HSD: 10/12/2022");
        image.add(R.drawable.free_ship);
        image.add(R.drawable.free_ship);
        image.add(R.drawable.free_ship);


        title.add("Mã giảm giá đơn hàng hơn 100k");
        title.add("Mã giảm giá đơn hàng hơn 200k");
        title.add("Mã giảm giá đơn hàng hơn 400k");
        title.add("Mã giảm giá đơn hàng hơn 500k");

        free_cost.add("Tối đa 40k");
        free_cost.add("Tối đa 30k");
        free_cost.add("Tối đa 50k");
        free_cost.add("Tối đa 70k");

        count.add("Số lượng: 10");
        count.add("Số lượng: 2");
        count.add("Số lượng: 1");
        count.add("Số lượng: 1");

        start_date.add("Bắt đầu: 20/11/2022");
        start_date.add("Bắt đầu: 30/11/2022");
        start_date.add("Bắt đầu: 10/11/2022");
        start_date.add("Bắt đầu: 10/11/2022");

        expiry_date.add("HSD: 8/12/2022");
        expiry_date.add("HSD: 7/12/2022");
        expiry_date.add("HSD: 6/12/2022");
        expiry_date.add("HSD: 5/12/2022");

        image.add(R.drawable.img_voucher);
        image.add(R.drawable.img_voucher);
        image.add(R.drawable.img_voucher);
        image.add(R.drawable.img_voucher);


        for (int i = 0; i < image.size(); i++) {
            VoucherArray.add(new Voucher(i, image.get(i), title.get(i), free_cost.get(i), count.get(i), start_date.get(i), expiry_date.get(i)));
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

        if (strValue == "All") {
            title.clear(); image.clear(); free_cost.clear(); count.clear(); start_date.clear(); expiry_date.clear();

            title.add("Tất cả hình thức thanh toán");
            title.add("Tất cả hình thức thanh toán");
            title.add("Tất cả hình thức thanh toán");
            free_cost.add("Tối đa 40k");
            free_cost.add("Tối đa 30k");
            free_cost.add("Tối đa 50k");
            count.add("Số lượng: 10");
            count.add("Số lượng: 2");
            count.add("Số lượng: 1");
            start_date.add("Bắt đầu: 20/11/2022");
            start_date.add("Bắt đầu: 30/11/2022");
            start_date.add("Bắt đầu: 10/11/2022");
            expiry_date.add("HSD: 12/12/2022");
            expiry_date.add("HSD: 11/12/2022");
            expiry_date.add("HSD: 10/12/2022");
            image.add(R.drawable.free_ship);
            image.add(R.drawable.free_ship);
            image.add(R.drawable.free_ship);


            title.add("Mã giảm giá đơn hàng hơn 100k");
            title.add("Mã giảm giá đơn hàng hơn 200k");
            title.add("Mã giảm giá đơn hàng hơn 400k");
            title.add("Mã giảm giá đơn hàng hơn 500k");
            free_cost.add("Tối đa 40k");
            free_cost.add("Tối đa 30k");
            free_cost.add("Tối đa 50k");
            free_cost.add("Tối đa 70k");
            count.add("Số lượng: 10");
            count.add("Số lượng: 2");
            count.add("Số lượng: 1");
            count.add("Số lượng: 1");
            start_date.add("Bắt đầu: 20/11/2022");
            start_date.add("Bắt đầu: 30/11/2022");
            start_date.add("Bắt đầu: 10/11/2022");
            start_date.add("Bắt đầu: 10/11/2022");
            expiry_date.add("HSD: 8/12/2022");
            expiry_date.add("HSD: 7/12/2022");
            expiry_date.add("HSD: 6/12/2022");
            expiry_date.add("HSD: 5/12/2022");
            image.add(R.drawable.img_voucher);
            image.add(R.drawable.img_voucher);
            image.add(R.drawable.img_voucher);
            image.add(R.drawable.img_voucher);

            VoucherArray.clear();
            for (int i = 0; i < image.size(); i++) {
                VoucherArray.add(new Voucher(i, image.get(i), title.get(i), free_cost.get(i), count.get(i), start_date.get(i), expiry_date.get(i)));
            }

        }

        if (strValue == "FreeShip") {

            title.clear(); image.clear(); free_cost.clear(); count.clear(); start_date.clear(); expiry_date.clear();

            title.add("Tất cả hình thức thanh toán");
            title.add("Tất cả hình thức thanh toán");
            title.add("Tất cả hình thức thanh toán");
            free_cost.add("Tối đa 40k");
            free_cost.add("Tối đa 30k");
            free_cost.add("Tối đa 50k");
            count.add("Số lượng: 10");
            count.add("Số lượng: 2");
            count.add("Số lượng: 1");
            start_date.add("Bắt đầu: 20/11/2022");
            start_date.add("Bắt đầu: 30/11/2022");
            start_date.add("Bắt đầu: 10/11/2022");
            expiry_date.add("HSD: 12/12/2022");
            expiry_date.add("HSD: 11/12/2022");
            expiry_date.add("HSD: 10/12/2022");
            image.add(R.drawable.free_ship);
            image.add(R.drawable.free_ship);
            image.add(R.drawable.free_ship);

            VoucherArray.clear();

            for (int i = 0; i < image.size(); i++) {
                VoucherArray.add(new Voucher(i, image.get(i), title.get(i), free_cost.get(i), count.get(i), start_date.get(i), expiry_date.get(i)));
            }
        }
        if (strValue == "Shop") {
            title.clear(); image.clear(); free_cost.clear(); count.clear(); start_date.clear(); expiry_date.clear();
            title.add("Mã giảm giá đơn hàng hơn 100k");
            title.add("Mã giảm giá đơn hàng hơn 200k");
            title.add("Mã giảm giá đơn hàng hơn 400k");
            title.add("Mã giảm giá đơn hàng hơn 500k");
            free_cost.add("Tối đa 40k");
            free_cost.add("Tối đa 30k");
            free_cost.add("Tối đa 50k");
            free_cost.add("Tối đa 70k");
            count.add("Số lượng: 10");
            count.add("Số lượng: 2");
            count.add("Số lượng: 1");
            count.add("Số lượng: 1");
            start_date.add("Bắt đầu: 20/11/2022");
            start_date.add("Bắt đầu: 30/11/2022");
            start_date.add("Bắt đầu: 10/11/2022");
            start_date.add("Bắt đầu: 10/11/2022");
            expiry_date.add("HSD: 8/12/2022");
            expiry_date.add("HSD: 7/12/2022");
            expiry_date.add("HSD: 6/12/2022");
            expiry_date.add("HSD: 5/12/2022");
            image.add(R.drawable.img_voucher);
            image.add(R.drawable.img_voucher);
            image.add(R.drawable.img_voucher);
            image.add(R.drawable.img_voucher);

            VoucherArray.clear();

            for (int i = 0; i < image.size(); i++) {
                VoucherArray.add(new Voucher(i, image.get(i), title.get(i), free_cost.get(i), count.get(i), start_date.get(i), expiry_date.get(i)));
            }
        }


        CustomVoucherListViewAdapter myAdapter = new CustomVoucherListViewAdapter(getActivity(), R.layout.custom_voucher_listview, VoucherArray);
        listVoucher.setAdapter(myAdapter);

    }


}
