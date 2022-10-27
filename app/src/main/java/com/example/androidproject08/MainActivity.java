package com.example.androidproject08;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {



    ListView listNotification;
    ArrayList<Notify> notifiesArray =new ArrayList<Notify>();
    String[] content ={
            "Thái đẹp trai lorem asd asd a sdasd sfsfsfsfsfsfsf","HAHA",
    };
    String[] title ={
            "TB!!","TB!!",
    };

    String[] date ={
            "24/10/2022","25/10/2022"
    };

    Integer[] imgid={
            R.drawable.img_product,R.drawable.img_product

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_cart);
        setContentView(R.layout.notification);


        listNotification = (ListView) findViewById(R.id.idListView);

        for (int i=0;i<10;i++){
            notifiesArray.add(new Notify(i,title[1],content[0],date[0],R.drawable.img_product));
        }
        MylistAdapter myAdapter=new MylistAdapter(this,R.layout.item_listview,notifiesArray);
        listNotification.setAdapter(myAdapter);

    }
}