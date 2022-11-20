package com.example.androidproject08;

//import static io.grpc.Context.LazyStorage.storage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class activity_mycart extends Activity {
    // khai báo biến UI
    View icon_back;
    ListView listMyCart;
    Button MyCart_bg_buy;
    TextView MyCart_total_cost;

    // sqlite
    SQLiteDatabase sqlite;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    // biến xử lý



    public void uploadFile(){
        String path = Environment.getExternalStorageDirectory().getPath();
        String myJpgPath = path + "/Download/girl480x600.jpg";


        Uri file = Uri.fromFile(new File(myJpgPath));
//        Log.d("fileName12", " " + file.getPath());
        StorageReference test =storageRef.child("image/"+file.getLastPathSegment());

        UploadTask uploadTask = test.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), " Upload Thành công", Toast.LENGTH_SHORT).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(getApplicationContext(), " Upload Thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        uploadFile();
//        downloadFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        listMyCart = (ListView) findViewById(R.id.MyCart_listview);
        icon_back = (View) findViewById(R.id.icon_back);
        MyCart_bg_buy = (Button) findViewById(R.id.MyCart_bg_buy);
        MyCart_total_cost = (TextView) findViewById(R.id.MyCart_total_cost);

        // get tên của activity trước đỏ để back khi nhấn button back lại đúng vị trí
        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("name_activity");
        String idDoc = intent.getStringExtra("idDoc");
        // get username từ sqlite
        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        if (username == null) {
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cartsRef = db.collection("carts");

        cartsRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                cartsRef
                        .whereEqualTo("ownCart", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<MyCart> onChangeCart = new ArrayList<>();
                                    Integer totalCart = 0, totalMoney = 0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        MyCart cart = document.toObject(MyCart.class);
                                        cart.setIdDoc(document.getId().toString());

                                        totalCart += cart.getAmount();
                                        totalMoney += cart.getAmount() * cart.getPrice();
                                    }

                                    // hiển thị tổng sản phẩm và tổng tiền tất cả các mặt hàng có trong giỏ hàng
                                    MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                                    MyCart_total_cost.setText("đ" + totalMoney.toString());
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

        // query dữ liệu cho qua listview
        mycart_asynctask mc_at = new mycart_asynctask(activity_mycart.this, username);
        mc_at.execute();

        // trở lại activity trước đó
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent();

                switch (previousActivity) {
                    case "activity_dashboard":
                        moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                        break;
                    case "activity_notify":
                        moveActivity = new Intent(getApplicationContext(), activity_notify.class);
                        break;
                    case "activity_profile":
                        moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                        break;
                    case "activity_voucher":
                        moveActivity = new Intent(getApplicationContext(), activity_voucher.class);
                        break;
                    case "activity_myorder":
                        moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
                        break;
                    case "activity_view_product":
                        moveActivity = new Intent(getApplicationContext(), activity_view_product.class);
                        moveActivity.putExtra("idDoc", idDoc);
                        break;
                    default:
                        break;
                }

                startActivity(moveActivity);
            }
        });
    }


}