package com.example.androidproject08;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomMycartListViewAdapter extends ArrayAdapter<MyCart> {
    // biến xử lý
    ArrayList<MyCart> my_cart = new ArrayList<MyCart>();
    Context curContext;
    int resource;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartsRef = db.collection("carts");
    CollectionReference usersRef = db.collection("users");

    // kết nối firestore để lấy ảnh
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public CustomMycartListViewAdapter(Context context, int resource, ArrayList<MyCart> objects) {
        super(context, resource, objects);
        this.my_cart = objects;
        this.curContext = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_mycart_listview, null);

        TextView name = (TextView) v.findViewById(R.id.custom_mycart_name_product);
        TextView old_cost = (TextView) v.findViewById(R.id.custom_mycart_old_cost_product);
        TextView new_cost = (TextView) v.findViewById(R.id.custom_mycart_new_cost_product);
        TextView number = (TextView) v.findViewById(R.id.custom_mycart_number_product);
        ImageView img = (ImageView) v.findViewById(R.id.custom_mycart_picture);

        View subButton = (View) v.findViewById((R.id.custom_mycart_icon_decrease));
        View addButton = (View) v.findViewById((R.id.custom_mycart_icon_increase));
        View garbage = (View) v.findViewById((R.id.custom_mycart_icon_garbage));
        View addTotal = (View) v.findViewById((R.id.custom_mycart_checkbox));

        CheckBox custom_mycart_checkbox = (CheckBox) v.findViewById(R.id.custom_mycart_checkbox);
        custom_mycart_checkbox.setChecked(true);

        custom_mycart_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    Log.i("TAG", "onCheckedChanged: checked" );
                }else {
                    Log.i("TAG", "onCheckedChanged: unchecked" );
                }
            }
        });

        Integer oldCost = (my_cart.get(position).getPrice() / (100 - my_cart.get(position).getSale())) * 100; // tính lại giá cũ

        downloadFile(v, my_cart.get(position).getImage());
        name.setText(my_cart.get(position).getName());
        old_cost.setText(oldCost.toString());
        new_cost.setText(my_cart.get(position).getPrice().toString());
        number.setText(my_cart.get(position).getAmount().toString());

        // trừ số lượng san phẩm trong cart khi bấm vào button "-"
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newNumber = Integer.parseInt(number.getText().toString()) - 1;
                if (newNumber >= 0) {
                    number.setText(newNumber.toString());

                    // cập nhật lên firestore
                    cartsRef.document(my_cart.get(position).getIdDoc())
                            .update("amount", newNumber);
                }
            }
        });

        // thêm số lượng sản phẩm trong cart
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newNumber = Integer.parseInt(number.getText().toString()) + 1;
                if (newNumber != 10000) {
                    number.setText(newNumber.toString());

                    // cập nhật lên firestore
                    cartsRef.document(my_cart.get(position).getIdDoc())
                            .update("amount", newNumber);
                }
            }
        });

        // xóa mặt hàng khỏi giỏ hàng
        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cập nhật lên firestore
                cartsRef.document(my_cart.get(position).getIdDoc()).delete();

                // tăng số lượng trong giỏ hàng cho user
                usersRef
                        .whereEqualTo("username", my_cart.get(position).getOwnCart())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User user = document.toObject(User.class);
                                        Map<String, Integer> userCart = new HashMap<>();
                                        userCart.put("amount", user.getCart().get("amount") - 1);
                                        usersRef.document(document.getId()).update("cart", userCart);
                                    }

                                    my_cart.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }
        });

        return v;
    }

    public void downloadFile(View v, String id) {
        StorageReference islandRef = storageRef.child("image").child(id.toString());//+".jpg");

        try {
            File localFile = File.createTempFile("tempfile", ".jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ImageView imgProduct = (ImageView) v.findViewById(R.id.custom_mycart_picture);
                    imgProduct.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("down", "onFailure: ");
                }
            });

        } catch (IOException e) {
            Log.e("ERROR", "Custommycart downloadFile: ", e);
        }

    }
}