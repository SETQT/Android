package com.example.androidproject08;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomMycartListViewAdapter extends ArrayAdapter<MyCart> {

    ArrayList<MyCart> my_cart = new ArrayList<MyCart>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartsRef = db.collection("carts");

    public CustomMycartListViewAdapter(Context context, int resource, ArrayList<MyCart> objects) {
        super(context, resource, objects);
        this.my_cart = objects;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_mycart_listview, null);

        TextView name = (TextView) v.findViewById(R.id.custom_mycart_name_product);
        TextView old_cost = (TextView) v.findViewById(R.id.custom_mycart_old_cost_product);
        TextView new_cost = (TextView) v.findViewById(R.id.custom_mycart_new_cost_product);
        TextView number = (TextView) v.findViewById(R.id.custom_mycart_number_product);
        ImageView img = (ImageView) v.findViewById(R.id.custom_mycart_picture);

        View subButton = (View) v.findViewById((R.id.custom_mycart_icon_decrease));
        View addButton = (View) v.findViewById((R.id.custom_mycart_icon_increase));
        View garbage = (View) v.findViewById((R.id.custom_mycart_icon_garbage));
        View addTotal = (View) v.findViewById((R.id.custom_mycart_icon_done));

        Integer oldCost = (my_cart.get(position).getPrice() / (100 - my_cart.get(position).getSale())) * 100; // tính lại giá cũ

        name.setText(my_cart.get(position).getName());
        old_cost.setText(oldCost.toString());
        new_cost.setText(my_cart.get(position).getPrice().toString());
        number.setText(my_cart.get(position).getAmount().toString());
//        img.setImageResource(my_cart.get(position).getImage().toString());

        // trừ số lượng san phẩm trong cart khi bấm vào button "-"
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newNumber = Integer.parseInt(number.getText().toString()) - 1;
                if (newNumber >= 0) {
                    number.setText(newNumber.toString());

                    // cập nhật lên firestore
                    cartsRef.document(my_cart.get(position).getIdDoc())
                            .update("amount", newNumber);
                }
            }
        });

        // thêm số lượng sản phẩm trong cart
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newNumber = Integer.parseInt(number.getText().toString())+1;
                if (newNumber != 10000) {
                    number.setText(newNumber.toString());

                    // cập nhật lên firestore
                    cartsRef.document(my_cart.get(position).getIdDoc())
                            .update("amount", newNumber);
                }
            }
        });

        // xóa mặt hàng khỏi giỏ hàng
        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cập nhật lên firestore
                cartsRef.document(my_cart.get(position).getIdDoc()).delete();
            }
        });

        return v;
    }
}