package com.example.androidproject08;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CustomMyorderListViewAdapter extends ArrayAdapter<Myorder> {
    Context curContext;
    ArrayList<Myorder> orders = new ArrayList<>();
    Integer count_star;
    Integer state;
    String username;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference commentsRef = db.collection("comments");

    public CustomMyorderListViewAdapter(Context context, int resource, ArrayList<Myorder> objects, Integer state, String username) {
        super(context, resource, objects);
        this.orders = objects;
        this.curContext = context;
        this.state = state;
        this.username = username;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_myorder_listview, null);

        TextView name = (TextView) v.findViewById(R.id.myorder_custom_name);
        TextView size = (TextView) v.findViewById(R.id.myorder_custom_size);
        TextView old_cost = (TextView) v.findViewById(R.id.myorder_custom_old_cost);
        TextView new_cost = (TextView) v.findViewById(R.id.myorder_custom_new_cost);
        TextView count = (TextView) v.findViewById(R.id.myorder_custom_count);
        TextView total = (TextView) v.findViewById(R.id.myorder_custom_total);
        ImageView img = (ImageView) v.findViewById(R.id.myorder_custom_picture) ;

        TextView danhgia = (TextView) v.findViewById(R.id.myorder_custom_danhgia);
        if(state == 4) {
            danhgia.setVisibility(View.VISIBLE);
        }else {
            danhgia.setVisibility(View.INVISIBLE);
        }

        // kiểm tra user đã đánh giá sản phẩm chưa rồi rồi thì không cho đánh giá nữa
        checkUserCommented(position, danhgia);

        name.setText(orders.get(position).getName());
        size.setText("Kích thước: " + orders.get(position).getSize() + ", Màu sắc: " + orders.get(position).getColor());
        old_cost.setText("đ" + orders.get(position).getOldCost().toString());
        count.setText("Số lượng: " + orders.get(position).getCount().toString());
        new_cost.setText("đ" + orders.get(position).getNewCost().toString());
        total.setText("đ" + orders.get(position).getTotal().toString());
        Picasso.with(curContext).load(orders.get(position).getImage()).into(img);

        danhgia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showFormEvaluate(position, danhgia);
            }
        });

        return v;

    }

    void showFormEvaluate(Integer position, TextView danhgia){
        final Dialog dialog = new Dialog(this.getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.form_evaluate);

        View star1, star2, star3, star4, star5;

        star1 = dialog.findViewById(R.id.star1);
        star2 = dialog.findViewById(R.id.star2);
        star3 = dialog.findViewById(R.id.star3);
        star4 = dialog.findViewById(R.id.star4);
        star5 = dialog.findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                count_star = 1;
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                count_star = 2;
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                count_star = 3;
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                count_star = 4;
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                count_star = 5;
            }
        });

        EditText text_evaluate = dialog.findViewById(R.id.text_evaluate);
        Button btn_confirm = dialog.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment newComment = new Comment(orders.get(position).getId(), username, orders.get(position).getColor(), orders.get(position).getSize(), text_evaluate.getText().toString(), new Date(), count_star);
                commentsRef.add(newComment);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    void ResetBackgroundStar(View star1, View star2,View star3,View star4,View star5){
        star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
    }

    void checkUserCommented(Integer position, TextView danhgia) {
        commentsRef
                .whereEqualTo("idProduct", orders.get(position).getId())
                .whereEqualTo("user", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            danhgia.setClickable(false);
                            danhgia.setText("Đã đánh giá");
                        }
                    }
                });
    }
}