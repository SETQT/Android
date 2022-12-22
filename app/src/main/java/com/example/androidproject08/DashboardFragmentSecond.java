package com.example.androidproject08;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DashboardFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_dashboard main;
    GridView GridProduct;
    LinearLayout layout_second;
    ProgressBar progressBar;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");

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
        layout_second = (LinearLayout) inflater.inflate(R.layout.custom_dashboard_layout_fragment_second, null);
        progressBar = (ProgressBar) layout_second.findViewById(R.id.progressBar);
        loading(true);

        GridProduct = (GridView) layout_second.findViewById(R.id.dashboard_gridview);

        dashboard_asynctask db_at = new dashboard_asynctask(main, layout_second, "Tất cả");
        db_at.execute();

        productsRef
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        dashboard_asynctask db_at = null;
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    db_at = new dashboard_asynctask(main, layout_second, "Tất cả");
                                    db_at.execute();
                                    break;
                                case MODIFIED:
                                    break;
                                case REMOVED:
                                    db_at = new dashboard_asynctask(main, layout_second, "Tất cả");
                                    db_at.execute();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });

        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }
        loading(false);
        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        dashboard_asynctask db_at = new dashboard_asynctask(main, layout_second, strValue);
        db_at.execute();

        productsRef
                .whereEqualTo("category", strValue)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        dashboard_asynctask db_at = null;
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    db_at = new dashboard_asynctask(main, layout_second, strValue);
                                    db_at.execute();
                                    break;
                                case MODIFIED:
                                    break;
                                case REMOVED:
                                    db_at = new dashboard_asynctask(main, layout_second, strValue);
                                    db_at.execute();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void onObjectFromMainToFragment(Object value) {

    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
