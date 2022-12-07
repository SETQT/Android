package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomListCommentAdapter extends ArrayAdapter<Comment> {
    ArrayList<Comment> comments = new ArrayList<>();

    public CustomListCommentAdapter(Context context, int resource, ArrayList<Comment> comments) {
        super(context, resource, comments);
        this.comments = comments;
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        username.setText(comments.get(position).getUser());
        type_product.setText("Kích thước: " + comments.get(position).getSizeProduct() + ", Màu sắc: " + comments.get(position).getColorProduct());
        value_comment.setText(comments.get(position).getContent());
        time_comment.setText(simpleDateFormat.format(comments.get(position).getCreatedAt()));

        return v;
    }
}
