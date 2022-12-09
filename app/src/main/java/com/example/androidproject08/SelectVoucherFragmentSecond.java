package com.example.androidproject08;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class SelectVoucherFragmentSecond extends Fragment implements FragmentCallbacks {
    // khai báo biến UI
    activity_select_voucher main;
    ListView select_voucher_listview;


    ArrayList<Voucher> VoucherArray = new ArrayList<Voucher>();

    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> free_cost = new ArrayList<>();
    ArrayList<Date> start_date = new ArrayList<>();
    ArrayList<Date> expiry_date = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();

//    // kết nối firestore
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference vouchersRef = db.collection("vouchers");

    public static SelectVoucherFragmentSecond newInstance(String strArg1) {
        SelectVoucherFragmentSecond fragment = new SelectVoucherFragmentSecond();
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

        main = (activity_select_voucher) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_select_voucher_layout_fragment_second, null);

        select_voucher_listview = (ListView) layout_second.findViewById(R.id.select_voucher_listview);

//        voucher_asynctask v_at = new voucher_asynctask("All");
//        v_at.execute();

        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }
        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);
        Toast.makeText(main, "data_send:" + strValue, Toast.LENGTH_SHORT).show();


//        voucher_asynctask v_at = new voucher_asynctask();
//
//        switch (strValue) {
//            case "All":
//                v_at = new voucher_asynctask("All");
//                v_at.execute();
//                break;
//            case "FreeShip":
//                v_at = new voucher_asynctask("freeship");
//                v_at.execute();
//                break;
//            case "Shop":
//                v_at = new voucher_asynctask("shop");
//                v_at.execute();
//                break;
//            default:
//                break;
//        }
    }

//    class voucher_asynctask extends AsyncTask<Void, Voucher, Voucher> {
//        ArrayList<Voucher> listVoucher = new ArrayList<>();
//        String type;
//
//        public voucher_asynctask() {}
//
//        public voucher_asynctask(String type) {
//            this.type = type;
//        }
//
//        @Override
//        protected Voucher doInBackground(Void... voids) {
//            try {
//                if(type.equals("All")) {
//                    vouchersRef
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        Boolean isHave = false;
//
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Voucher voucher = document.toObject(Voucher.class);
//                                            isHave = true;
//                                            publishProgress(voucher);
//                                        }
//
//                                        if(!isHave) {
//                                            publishProgress();
//                                        }
//                                    } else {
//                                        Log.d("TAG", "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }else {
//                    vouchersRef
//                            .whereEqualTo("type", type)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Voucher voucher = document.toObject(Voucher.class);
//                                            publishProgress(voucher);
//                                        }
//                                    } else {
//                                        Log.d("TAG", "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }
//            } catch (Exception error) {
//                Log.e("ERROR", "VoucherFragmentSecond doInBackground: ", error);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Voucher... vouchers) {
//            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
//            super.onProgressUpdate(vouchers);
//
//            if (vouchers.length == 0) {
//                listVoucher.clear();
//            } else {
//                listVoucher.add(vouchers[0]);
//            }
//
//            try {
//                CustomVoucherListViewAdapter myAdapter = new CustomVoucherListViewAdapter(getActivity(), R.layout.custom_voucher_listview, listVoucher);
//                voucher_listview.setAdapter(myAdapter);
//            }catch (Exception error) {
//                Log.e("ERROR", "VoucherFragmentSecond: ", error);
//                return;
//            }
//        }
//    }
}