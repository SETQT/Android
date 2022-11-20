package com.example.androidproject08;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject08.Type_View_Product;

import java.util.List;


public class ListTypeProductAdapter extends RecyclerView.Adapter<ListTypeProductAdapter.MyViewHolder> {
        private List<Type_View_Product> listType;
        class MyViewHolder extends RecyclerView.ViewHolder {
            Button name_type;
            MyViewHolder(View view) {
                super(view);
                name_type = view.findViewById(R.id.name_type_product);
            }
        }
        public ListTypeProductAdapter(List<Type_View_Product> listType) {
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
            Type_View_Product movie = listType.get(position);
            holder.name_type.setText(movie.get_name_type());
        }
        @Override
        public int getItemCount() {
            return listType.size();
        }
    }
