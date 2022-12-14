package com.example.androidproject08;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ScanFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_scan_pay main;
    ImageView qrcode;
    TextView name, option;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference scanRef = db.collection("scan");

    public static ScanFragmentSecond newInstance(String strArg1) {
        ScanFragmentSecond fragment = new ScanFragmentSecond();
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

        main = (activity_scan_pay) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout_second = (RelativeLayout) inflater.inflate(R.layout.custom_scan_pay_layout_fragment_second, null);

        qrcode = (ImageView) layout_second.findViewById(R.id.qrcode);
        name = (TextView) layout_second.findViewById(R.id.name);
        option = (TextView) layout_second.findViewById(R.id.option);


        ScanFragmentSecond.scan_asynctask v_at = new ScanFragmentSecond.scan_asynctask(1);
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
        ScanFragmentSecond.scan_asynctask s_at = new ScanFragmentSecond.scan_asynctask();

        switch (strValue) {
            case "momo":
                s_at = new ScanFragmentSecond.scan_asynctask(1);
                s_at.execute();
                break;
            case "zalopay":
                s_at = new ScanFragmentSecond.scan_asynctask(2);
                s_at.execute();
                break;
            case "banking":
                s_at = new ScanFragmentSecond.scan_asynctask(3);
                s_at.execute();
                break;
            default:
                break;
        }
    }

    @Override
    public void onObjectFromMainToFragment(Object value) {

    }

    class scan_asynctask extends AsyncTask<Void, Scan, Scan> {
        Integer state;

        public scan_asynctask() {
        }

        public scan_asynctask(Integer state) {
            this.state = state;
        }

        @Override
        protected Scan doInBackground(Void... voids) {
            try {
                scanRef
                        .whereEqualTo("state", state)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Boolean isHave = false;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Scan scan = document.toObject(Scan.class);
                                        isHave = true;
                                        publishProgress(scan);
                                    }
                                    if (!isHave) {
                                        publishProgress();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (
                    Exception error) {
                Log.e("ERROR", "VoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Scan... scans) {
            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
           super.onProgressUpdate(scans);

            try {
                name.setText(scans[0].getName());
                option.setText(scans[0].getOption());
                Picasso.with(getContext()).load(scans[0].getImg()).into(qrcode);
            } catch (Exception error) {
                Log.e("ERROR", "VoucherFragmentSecond: ", error);
                return;
            }
        }
    }

}
