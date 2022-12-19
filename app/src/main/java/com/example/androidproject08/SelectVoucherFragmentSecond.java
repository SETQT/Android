package com.example.androidproject08;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SelectVoucherFragmentSecond extends Fragment implements FragmentCallbacks {
    // khai báo biến UI
    activity_select_voucher main;
    ListView select_voucher_listview;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference vouchersRef = db.collection("vouchers");

    // biễn xử lý
    ArrayList<Myorder> ListOrderArray = new ArrayList<>();
    Voucher usedVoucher, voucherFromMain;
    Integer totalMoneyInListOrderArray = 0;
    String username;

    // sqlite
    SQLiteDatabase sqlite;

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
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_select_voucher_layout_fragment_second, null);

        select_voucher_listview = (ListView) layout_second.findViewById(R.id.select_voucher_listview);

        voucher_asynctask v_at = new voucher_asynctask();
        v_at.execute();

        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        voucher_asynctask v_at = new voucher_asynctask(strValue);
        v_at.execute();
    }

    @Override
    public void onObjectFromMainToFragment(Object value) {
        if (value != null) {
            if (value.getClass() == Voucher.class) {
                voucherFromMain = (Voucher) value;
            } else {
                ListOrderArray = (ArrayList<Myorder>) value;

                for (int i = 0; i < ListOrderArray.size(); i++) {
                    totalMoneyInListOrderArray += ListOrderArray.get(i).getTotal();
                }
            }
        }
    }

    class voucher_asynctask extends AsyncTask<Void, Voucher, Voucher> {
        ArrayList<Voucher> listVoucher = new ArrayList<>();
        String dataSearch;
        Date curDate;

        public voucher_asynctask() {
            this.curDate = new Date();
        }

        public voucher_asynctask(String dataSearch) {
            this.dataSearch = dataSearch;
            this.curDate = new Date();
        }

        @Override
        protected Voucher doInBackground(Void... voids) {
            try {
                if (dataSearch != null) {
                    dataSearch = dataSearch.toUpperCase();
                    vouchersRef
                            .whereEqualTo("id", dataSearch)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Boolean isHave = false;

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Voucher voucher = document.toObject(Voucher.class);
                                            isHave = true;
                                            publishProgress(voucher);
                                        }

                                        if (!isHave) {
                                            publishProgress();
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                } else {
                    vouchersRef
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Boolean isHave = false;

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Voucher voucher = document.toObject(Voucher.class);
                                            voucher.setIdDoc(document.getId());
                                            if (curDate.after(voucher.getStartedAt()) && curDate.before(voucher.getFinishedAt())) {
                                                isHave = true;
                                                publishProgress(voucher);
                                            }
                                        }

                                        if (!isHave) {
                                            publishProgress();
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            } catch (Exception error) {
                Log.e("ERROR", "VoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Voucher... vouchers) {
            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(vouchers);

            if (vouchers.length == 0) {
                listVoucher.clear();
            } else {
                if (curDate.after(vouchers[0].getStartedAt()) && curDate.before(vouchers[0].getFinishedAt()) && (vouchers[0].getAmoutOfUsed() != vouchers[0].getAmount())) {
                    listVoucher.add(vouchers[0]);
                }
            }

            try {
                CustomSelectVoucherListViewAdapter myAdapter = new CustomSelectVoucherListViewAdapter(main.getApplicationContext(), R.layout.custom_select_voucher_layout_fragment_second, listVoucher);
                select_voucher_listview.setAdapter(myAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "VoucherFragmentSecond: ", error);
                return;
            }
        }
    }

    private class CustomSelectVoucherListViewAdapter extends ArrayAdapter<Voucher> {
        ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
        Context curContext;
        ArrayList<Integer> stateCheckbox = new ArrayList<>();

        public CustomSelectVoucherListViewAdapter(Context context, int resource, ArrayList<Voucher> objects) {
            super(context, resource, objects);
            this.vouchers = objects;
            this.curContext = context;
            for (int i = 0; i < objects.size(); i++) {
                if (vouchers.get(i).equals(voucherFromMain)) {
                    this.stateCheckbox.add(1);
                } else {
                    this.stateCheckbox.add(0);
                }
            }
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_select_voucher_listview, null);

            TextView title = (TextView) v.findViewById(R.id.custom_select_voucher_title);
            TextView min_cost = (TextView) v.findViewById(R.id.custom_select_voucher_min_cost);
            TextView free_cost = (TextView) v.findViewById(R.id.custom_select_voucher_free_cost);
            CheckBox checkbox = (CheckBox) v.findViewById(R.id.custom_select_voucher_checkbox);
            TextView start_date = (TextView) v.findViewById(R.id.custom_select_voucher_start_date);
            TextView expiry_date = (TextView) v.findViewById(R.id.custom_select_voucher_expiry_date);

            if (stateCheckbox.get(position) == 1) {
                usedVoucher = vouchers.get(position);

                main.onObjectFromFragToMain("RED-FRAG", usedVoucher);

                checkbox.setChecked(true);
            } else if (stateCheckbox.get(position) == 3) {
                checkbox.setEnabled(false);
            } else {
                checkbox.setEnabled(true);
                checkbox.setChecked(false);
            }

            Handle.checkUserUsedVoucher(username, vouchers.get(position).getIdDoc());

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (Handle.isIsUsed()) {
                            new AlertDialog.Builder(main)
                                    .setMessage("Bạn chỉ được dùng voucher này 1 lần thôi!")
                                    .setCancelable(true)
                                    .show();
                        } else {
                            if (vouchers.get(position).getMinimumCost() > totalMoneyInListOrderArray) {
                                stateCheckbox.set(position, 0);

                                new AlertDialog.Builder(main)
                                        .setMessage("Bạn không đủ điều kiện để sử dụng voucher này!")
                                        .setCancelable(true)
                                        .show();

                                for (int i = 0; i < stateCheckbox.size() && i != position; i++) {
                                    stateCheckbox.set(i, 0);
                                }
                            } else {
                                stateCheckbox.set(position, 1);

                                for (int i = 0; i < stateCheckbox.size() && i != position; i++) {
                                    stateCheckbox.set(i, 3);
                                }
                            }
                        }

                        notifyDataSetChanged();
                    } else {
                        stateCheckbox.set(position, 0);

                        for (int i = 0; i < stateCheckbox.size() && i != position; i++) {
                            stateCheckbox.set(i, 0);
                        }

                        notifyDataSetChanged();
                    }
                }
            });

            ImageView img = (ImageView) v.findViewById(R.id.custom_select_voucher_picture);

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

            title.setText(vouchers.get(position).getTitle());
            min_cost.setText("Đơn hàng tối thiểu: " + Handle.kFortmatter(vouchers.get(position).getMinimumCost().toString()));
            free_cost.setText("Tối đa " + Handle.kFortmatter(vouchers.get(position).getMoneyDeals().toString()));
            start_date.setText("NBD: " + formatDate.format(vouchers.get(position).getStartedAt()).toString());
            expiry_date.setText("HSD: " + formatDate.format(vouchers.get(position).getFinishedAt()).toString());
            Picasso.with(curContext).load(vouchers.get(position).getImage()).into(img);

            return v;
        }

        public ArrayList<Integer> getStateCheckbox() {
            return stateCheckbox;
        }
    }
}
