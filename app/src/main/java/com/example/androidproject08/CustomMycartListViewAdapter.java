package com.example.androidproject08;

//import static androidx.core.graphics.drawable.IconCompat.getResources;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomMycartListViewAdapter extends ArrayAdapter<MyCart> {

    ArrayList<MyCart> my_cart = new ArrayList<MyCart>();
    Integer total=0 ;
    Float price=0F;
    public DatabaseReference database= FirebaseDatabase.getInstance().getReference();
    private int activity_mycart= R.layout.activity_mycart;
//    private int viewTotal= fin;



//    final View container = new View(getContext().getApplicationContext());
//    TextView totalCost = (TextView) container.findViewById(R.id.MyCart_total_cost);

    public CustomMycartListViewAdapter(Context context, int resource, ArrayList<MyCart> objects) {
        super(context, resource, objects);
        this.my_cart = objects;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    public void update(){
//        setConten
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_mycart_listview, null);

        View vFooter  ;
        LayoutInflater inflater2 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vFooter = inflater2.inflate(R.layout.activity_mycart, null);

        TextView name = (TextView) v.findViewById(R.id.custom_mycart_name_product);
        TextView old_cost = (TextView) v.findViewById(R.id.custom_mycart_old_cost_product);
        TextView new_cost = (TextView) v.findViewById(R.id.custom_mycart_new_cost_product);
        TextView number = (TextView) v.findViewById(R.id.custom_mycart_number_product);
        ImageView img = (ImageView) v.findViewById(R.id.custom_mycart_picture);

        View subButton = (View) v.findViewById((R.id.custom_mycart_icon_decrease)) ;
        View addButton = (View) v.findViewById((R.id.custom_mycart_icon_increase)) ;
        View garbage = (View) v.findViewById((R.id.custom_mycart_icon_garbage)) ;
        View addTotal = (View) v.findViewById((R.id.custom_mycart_icon_done)) ;


        TextView totalCost = (TextView) vFooter.findViewById(R.id.MyCart_total_cost) ;
//        container.findViewById(R.id.btn);


        name.setText(my_cart.get(position).getName());
        old_cost.setText(my_cart.get(position).getOld_cost());
        new_cost.setText(my_cart.get(position).getNew_cost());
        number.setText(my_cart.get(position).getNumber());
        img.setImageResource(my_cart.get(position).getImage());

//        final View container = new View(getContext().getApplicationContext());
//    TextView totalCost = (TextView) activity_mycart.fin(R.id.MyCart_total_cost);
//

        User user = new User();
        user.setUsername("1242");

        String nameProduct = name.getText().toString();
        DatabaseReference item = database.child("user").child(user.getUsername()).child("cart").child("product");
//        removeItem =database.child("user").child(user.getUsername()).child("cart").child("
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newNumber = Integer.parseInt(number.getText().toString())-1;
                if (newNumber !=-1) {
                    number.setText(newNumber.toString());
                    Integer id =my_cart.get(position).getId();
//                    Log.i("sdf", "onClisdfck: " + id.toString());
                    item.child(id.toString()).child("amount").setValue(newNumber.toString());
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newNumber = Integer.parseInt(number.getText().toString())+1;
                if (newNumber !=10000) {
                    number.setText(newNumber.toString());
                    Integer id =my_cart.get(position).getId();
//                    Log.i("sdf", "onClisdfck: " + id.toString());
                    item.child(id.toString()).child("amount").setValue(newNumber.toString());
                }
            }
        });
        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id =my_cart.get(position).getId();
                item.child(id.toString()).removeValue();

            }
        });
        addTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total++;

                String cost = new_cost.getText().toString();
                price+=Float.parseFloat(cost);


                totalCost.setText(price.toString());
                Log.i("sd", "onClick: "+total+" "+price+totalCost.getText().toString());

            }
        });

        return v;

    }
}