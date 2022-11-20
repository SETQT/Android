package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ListTypeOptionDashboard extends RecyclerView.Adapter<ListTypeOptionDashboard.MyViewHolder> {
    private List<ListViewOptionDashboard> listType;
    private IClickItemListener IClickItemListener;
    List<TextView> textviewList = new ArrayList<>();

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        private ListViewOptionDashboard list;

        MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.button_option);
        }
    }

    public ListTypeOptionDashboard(List<ListViewOptionDashboard> listType, IClickItemListener listener) {
        this.listType = listType;
        this.IClickItemListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_option_dashboard, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ListViewOptionDashboard l = listType.get(position);
        ListViewOptionDashboard movie = listType.get(position);
        holder.text.setText(movie.getText());
        textviewList.add(holder.text);

        if (holder.text.getText() == listType.get(0).getText()){
            holder.text.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
            holder.text.setTextAppearance(R.style.setTextAfterClickDashBoard);
        }

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IClickItemListener.onClickItem(l);
                for(TextView textView : textviewList){
                    textView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    textView.setTextAppearance(R.style.setTextNotClickDashBoard);
                }
                holder.text.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                holder.text.setTextAppearance(R.style.setTextAfterClickDashBoard);
            }

        });



    }


    @Override
    public int getItemCount() {
        return listType.size();
    }


}