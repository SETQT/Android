package com.example.androidproject08;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {


    ListView listMyCart;
    ArrayList<MyCart> MyCartArray =new ArrayList<MyCart>();

    public DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

//    String[] name = {"Áo khoác Mono cực chất lượng", "Áo Liver giúp ra hang đầu mùa giải", "Áo anh 7 dự bị", "Giày độn", "Áo khoác", "Mũ lưỡi trai"};
//    String[] old_cost = {"đ500.000","đ400.000","đ300.000","đ700.000","đ100.000","đ200.000" };
//    String[] new_cost = {"đ300.000","đ200.000","đ200.000","đ500.000","đ80.000","đ150.000" };
//    String[] number = {"02","01","02","01","01","01" };
//    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1,R.drawable.mono1,R.drawable.mono1  };

    private void readData(Integer id,FirebaseCallback callback){
//    Integer id =1242;
        Query allPost=mDatabase.child("user").child(id.toString()).child("cart").child("product");
        allPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                String total =item.child("total").getValue(). toString();
                ArrayList<String> listId = new ArrayList<String>();
                ArrayList<String> listSale = new ArrayList<String>();
                ArrayList<String> listPrice = new ArrayList<String>();
                ArrayList<String> listName = new ArrayList<String>();
                ArrayList<String> listAmount = new ArrayList<String>();
                ArrayList<String> listImage = new ArrayList<String>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {

                    String id=item.child("id").getValue(). toString();
                    String amount=item.child("amount").getValue(). toString();

                    String price=item.child("price").getValue(). toString();
                    String sale=item.child("sale").getValue(). toString();
                    String image=item.child("image").getValue(). toString();
                    String name=item.child("name").getValue(). toString();

                    listId.add(id);
                    listSale.add(sale);
                    listName.add(name);
                    listPrice.add(price);
                    listImage.add(image);
                    listAmount.add(amount);
//
                }
                callback.onCallback(listId,listName,listPrice,listSale,listAmount,listImage);
//                callback.onCallback(listAmount,"amount");
//                AsynchronousChannel float price= loadPriceFromid(126,125);
//                                    Log.i("testdb", "loadById: "+price);

//                String result = dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private interface FirebaseCallback{
    void  onCallback(List<String> list1,List<String> list2, List<String> list3,List<String> list4,List<String> list5, List<String> list6 );

    }

//    android:id="@+id/MyCart_total_cost"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");


        readData(1242,new FirebaseCallback() {
//            List<String> result;
//            ArrayList<String> Arr = new ArrayList<String>();
            @Override
            public void onCallback(List<String> list,List<String> list2,
            List<String> list3,List<String> list4,List<String> list5,List<String> list6) {
//
                listMyCart = (ListView) findViewById(R.id.MyCart_listview);
                ArrayList<MyCart> finalList = new ArrayList<MyCart>();
                for (int i=0;i<list.size();i++){
                    Integer a =Integer.parseInt(list.get(i).toString());
                    String b= list2.get(i);
                    String c= list3.get(i);
                    Float d= Float.parseFloat(list4.get(i));
                    Float price = Float.parseFloat(list3.get(i));
                    String newPrice = Float.toString(price-(d/100)*price);
                    String e= list5.get(i);
                    Integer f= Integer.parseInt(list6.get(i));
                    finalList.add(new MyCart(a,b,c,newPrice,e,f));

//                Log.i("CART---", "loadById: "+a+b+c+d+e+f);
                }
                CustomMycartListViewAdapter myAdapter = new CustomMycartListViewAdapter(getApplicationContext(),R.layout.custom_notify_listview, finalList);
                listMyCart.setAdapter(myAdapter);


            }

        });
//        myRef.setValue("Hello, World!");


        listMyCart = (ListView) findViewById(R.id.MyCart_listview);

//        for (int i=0;i<6;i++){
//            MyCartArray.add(new MyCart(i, name[i],old_cost[i], new_cost[i], number[i], image[i]));
//        }
//        CustomMycartListViewAdapter myAdapter = new CustomMycartListViewAdapter(this,R.layout.custom_notify_listview, MyCartArray);
//        listMyCart.setAdapter(myAdapter);
    }
}