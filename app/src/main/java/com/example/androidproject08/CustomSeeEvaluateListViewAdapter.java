package com.example.androidproject08;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomSeeEvaluateListViewAdapter extends ArrayAdapter<CommentEvaluate> {
    ArrayList<CommentEvaluate> comments = new ArrayList<>();

    public CustomSeeEvaluateListViewAdapter(Context context, int resource, ArrayList<CommentEvaluate> comments) {
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
        v = inflater.inflate(R.layout.custom_listview_see_evaluate, null);

        TextView username = (TextView) v.findViewById(R.id.username);
        CircleImageView avatar = (CircleImageView) v.findViewById(R.id.avatar);
        ExpandableTextView text_evaluate = (ExpandableTextView) v.findViewById(R.id.text_evaluate);
        ImageView img_product = (ImageView) v.findViewById(R.id.img_product);
        TextView name_product = (TextView) v.findViewById(R.id.name_product);
        TextView size_color_product = (TextView) v.findViewById(R.id.size_color_product);
        TextView date_time = (TextView) v.findViewById(R.id.date_time);
        RelativeLayout reply_evaluate = (RelativeLayout)  v.findViewById(R.id.reply_evaluate);
        TextView text_reply_evaluate = (TextView) v.findViewById(R.id.text_reply_evaluate);

        // format ngày tháng năm
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Picasso.with(getContext()).load(comments.get(position).getImgProduct()).into(img_product);
        Picasso.with(getContext()).load(comments.get(position).getAvatar()).into(avatar);
        name_product.setText(comments.get(position).getNameProduct());
        username.setText(comments.get(position).getUser());
        size_color_product.setText("Size: " + comments.get(position).getSizeProduct() + ", Màu sắc: " + comments.get(position).getColorProduct());
        text_evaluate.setText(comments.get(position).getContent());
        date_time.setText(simpleDateFormat.format(comments.get(position).getCreatedAt()));

        if (comments.get(position).getReply() == ""){
            reply_evaluate.setVisibility(View.GONE);
        } else {
            text_reply_evaluate.setText(comments.get(position).getReply());
        }

        Integer count_star = comments.get(position).getCountStar();

        View star1, star2, star3, star4, star5;
        star1 = (View) v.findViewById(R.id.star1);
        star2 = (View) v.findViewById(R.id.star2);
        star3 = (View) v.findViewById(R.id.star3);
        star4 = (View) v.findViewById(R.id.star4);
        star5 = (View) v.findViewById(R.id.star5);

        Handle.setStar(star1, star2, star3, star4, star5, count_star);

        return v;
    }

}
