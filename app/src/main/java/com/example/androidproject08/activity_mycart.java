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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class activity_mycart extends Activity {
    // khai báo biến UI
    View icon_back;
    ListView listMyCart;

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
//    public  void downloadFile(){
//       StorageReference islandRef = storageRef.child("image/girl480x600.jpg");
//
////        File localFile = File.createTempFile("tempFile", ".jpg");
//        String path = Environment.getExternalStorageDirectory().getPath();
//        String myJpgPath = path + "/Download/girl480x600.jpg";
//        try {
//            File localFile = File.createTempFile("tempfile", ".jpg");
//
//        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                Log.d("down", "success: ");
//
//                // Local temp file has been created
//                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                ImageView imgProduct = (ImageView)  findViewById(R.id.custom_mycart_picture);
//                imgProduct.setImageBitmap(bitmap);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.d("down", "onFailure: ");
//            }
//        });
//
//        }catch ( IOException e){
//
//        }
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        uploadFile();
//        downloadFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        listMyCart = (ListView) findViewById(R.id.MyCart_listview);
        icon_back = (View) findViewById(R.id.icon_back);

        // get tên của activity trước đỏ để back khi nhấn button back lại đúng vị trí
        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("name_activity");

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
                // query dữ liệu cho qua listview
                mycart_asynctask mc_at = new mycart_asynctask(activity_mycart.this, username);
                mc_at.execute();
            }
        });



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
                    default:
                        break;
                }

                startActivity(moveActivity);
            }
        });
    }
}