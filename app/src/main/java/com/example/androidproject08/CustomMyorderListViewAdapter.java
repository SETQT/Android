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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomMyorderListViewAdapter extends ArrayAdapter<Myorder> {
    Context curContext;
    ArrayList<Myorder> orders = new ArrayList<>();
    Integer count_star;

    public CustomMyorderListViewAdapter(Context context, int resource, ArrayList<Myorder> objects) {
        super(context, resource, objects);
        this.orders = objects;
        this.curContext = context;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
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
        //Này state 4 thì value thuộc tính danhgia = "Đánh giá", còn 3 state còn lại = NULL
        danhgia.setText(orders.get(position).getDanhgia());

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
                showFormEvaluate();
            }
        });

        return v;

    }

    void showFormEvaluate(){
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
                Log.i("TAG", "onClick:" + text_evaluate.getText() + count_star);

                Intent moveActivity = new Intent();
                moveActivity = new Intent(curContext.getApplicationContext(), activity_dashboard.class);
                curContext.startActivity(moveActivity);

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(700, 650);
    }

    void ResetBackgroundStar(View star1, View star2,View star3,View star4,View star5){
        star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
    }
}