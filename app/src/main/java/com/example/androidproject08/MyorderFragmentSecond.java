package com.example.androidproject08;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;

public class MyorderFragmentSecond extends Fragment implements FragmentCallbacks {
    // biến UI
    ListView listMyOrder;

    // sqlite
    SQLiteDatabase sqlite;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference ordersRef = db.collection("orders");

    // biến xử lý
    activity_myorder main;
    ArrayList<Order> MyOrderArray = new ArrayList<>();
    String username;

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

        // kết nối sqlite
        File storagePath = main.getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        username = c1.getString(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_myorder_layout_fragment_second, null);
        listMyOrder = (ListView) layout_second.findViewById(R.id.myorder_listview);

        order_asynctask o_at = new order_asynctask(1);
        o_at.execute();

        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        if (strValue == "Cho xac nhan") {
            order_asynctask o_at = new order_asynctask(1);
            o_at.execute();
        }
        if (strValue == "Cho lay hang") {
            order_asynctask o_at = new order_asynctask(2);
            o_at.execute();
        }

        if (strValue == "Dang giao hang") {
            order_asynctask o_at = new order_asynctask(3);
            o_at.execute();
        }

        if (strValue == "Da giao") {
            order_asynctask o_at = new order_asynctask(4);
            o_at.execute();
        }
    }

    class order_asynctask extends AsyncTask<Void, Order, Order> {
        ArrayList<Myorder> listOrder = new ArrayList<>();
        Integer state;

        public order_asynctask() {
        }

        public order_asynctask(Integer state) {
            this.state = state;
        }

        @Override
        protected Order doInBackground(Void... voids) {
            try {
                ordersRef
                        .whereEqualTo("ownOrder", username)
                        .whereEqualTo("state", state)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Boolean isHave = false;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Order order = document.toObject(Order.class);
                                        isHave = true;
                                        publishProgress(order);
                                    }

                                    if (!isHave) {
                                        publishProgress();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "VoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Order... orders) {
            // Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(orders);

            if (orders.length == 0) {
                listOrder.clear();
            } else {
                for (int i = 0; i < orders[0].getArrayOrder().size(); i++) {
                    listOrder.add(orders[0].getArrayOrder().get(i));
                }
            }

            try {
                CustomMyorderListViewAdapter myAdapter = new CustomMyorderListViewAdapter(getActivity(), R.layout.custom_voucher_listview, listOrder, state, username);
                listMyOrder.setAdapter(myAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "MyorderFragmentSecond: ", error);
                return;
            }
        }
    }
}
