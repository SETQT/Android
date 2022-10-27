package com.example.androidproject08;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MylistAdapter extends ArrayAdapter<Notify> {

    ArrayList<Notify> noti = new ArrayList<Notify>();




    public MylistAdapter(Context context, int resource, ArrayList<Notify> objects){
        super(context, resource, objects);
        this.noti=objects;
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
        v = inflater.inflate(R.layout.item_listview, null);

        TextView textTitle = (TextView) v.findViewById(R.id.itemTitle);
        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        TextView textContent = (TextView) v.findViewById(R.id.content);
        TextView textDate = (TextView) v.findViewById(R.id.date);


        textTitle.setText(noti.get(position).getTitle());


        textContent.setText(noti.get(position).getContent());
        textDate.setText(noti.get(position).getDate());

        imageView.setImageResource(noti.get(position).getImage());

        return v;

    }
//    private final Activity context;
//    private final String title;
//    private final String content;
//    private final String date;
//    private final Integer imgid;


//    public MylistAdapter(Activity activity,String title,String content,String date,Integer id){
//        this.context=activity;
//        this.content=content;
//        this.title=title;
//        this.date=date;
//        this.imgid=id;
//
//    }

}
