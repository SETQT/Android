package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class CustomListCommentAdapter extends ArrayAdapter<com.example.androidproject08.MyListComment> {
    ArrayList<com.example.androidproject08.MyListComment> comments = new ArrayList<com.example.androidproject08.MyListComment>();


    public CustomListCommentAdapter(Context context, int resource, ArrayList<com.example.androidproject08.MyListComment> objects) {
        super(context, resource, objects);
        this.comments = objects;
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
        v = inflater.inflate(R.layout.custom_listview_comment_view_product, null);

        TextView username = (TextView) v.findViewById(R.id.name_user_comment_view_product);
        TextView type_product = (TextView) v.findViewById(R.id.type_product_user_buy);
        TextView value_comment = (TextView) v.findViewById(R.id.value_comment_user_view_product);
        TextView time_comment = (TextView) v.findViewById(R.id.time_user_comment);


        username.setText(comments.get(position).getUserName());
        type_product.setText(comments.get(position).getType_product());
        value_comment.setText(comments.get(position).getComment());
        time_comment.setText(comments.get(position).getTime_comment());

        return v;
    }
}
