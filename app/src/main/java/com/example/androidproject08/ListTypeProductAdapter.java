package com.example.androidproject08;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListTypeProductAdapter extends RecyclerView.Adapter<ListTypeProductAdapter.MyViewHolder> {
    private List<String> listType;

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button name_type;

        MyViewHolder(View view) {
            super(view);
            name_type = view.findViewById(R.id.name_type_product);
        }
    }

    public ListTypeProductAdapter(List<String> listType) {
        this.listType = listType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_type_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String movie = listType.get(position);
        holder.name_type.setText(movie);
    }

    @Override
    public int getItemCount() {
        return listType.size();
    }
}
