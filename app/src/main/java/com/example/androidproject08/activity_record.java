package com.example.androidproject08;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//import gun0912.tedbottompicker.TedBottomPicker;
//import gun0912.tedbottompicker.TedBottomSheetDialogFragment;


//import gun0912.tedbottompicker.TedBottomPicker;
//import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

//import gun0912.tedbottompicker.TedBottomPicker;
//import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class activity_record extends Activity {


    //request permission can thiet camera,thu vien ,...
    public void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(activity_record.this, " Cấp quyền thành công !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(activity_record.this, "Đã từ chối !!", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Không thể cập nhật do chưa cấp quyền truy cập!! \n\n Thay đổi bằng cách Setting -> Permission")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


    }

    // sqlite
    SQLiteDatabase sqlite;

    // khai báo biến UI
    View record_ic_back;
    ImageView camera;
    ImageView cameraBackground;

    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE_BACKGROUND = 200;
    Uri imageUri;

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    } private void openGalleryBackground() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_BACKGROUND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            ImageView avatar = (ImageView) findViewById(R.id.record_avatar);
            CircleImageView avatar = (CircleImageView) findViewById(R.id.record_avatar);
            imageUri = data.getData();
            avatar.setImageURI(imageUri);

        }
        else
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_BACKGROUND) {
            ImageView avatar = (ImageView) findViewById(R.id.record_rectangle_header_profile);

            imageUri = data.getData();
            avatar.setImageURI(imageUri);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
//            openGallery();
        record_ic_back = (View) findViewById(R.id.record_ic_back);
        camera = (ImageView) findViewById(R.id.record_camera_02);
        cameraBackground = (ImageView) findViewById(R.id.record_camera_01);
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

        // query dữ liệu cho qua listview
        record_asynctask r_at = new record_asynctask(activity_record.this, username);
        r_at.execute();

        // quay trở lại activity profile
        record_ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                startActivity(moveActivity);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage("avatar");


            }
        });
        cameraBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage("background");

            }
        });
    }

    public void changeImage(String type) {
        if (ContextCompat.checkSelfPermission(activity_record.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            if (type.equals("avatar"))
            openGallery();
            else openGalleryBackground();

        } else {
            requestPermission();

            if (ContextCompat.checkSelfPermission(activity_record.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                if (type.equals("avatar"))
                    openGallery();
                else openGalleryBackground();

            }

        }
    }
//
//    public void changeImage(String type) {
//        if (ContextCompat.checkSelfPermission(activity_record.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                PackageManager.PERMISSION_GRANTED) {
//            openGallery();
//
//        } else {
//            requestPermission();
//
//            if (ContextCompat.checkSelfPermission(activity_record.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                    PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            }
//
//        }
//    }

    public void downloadFile(User user) {
//        StorageReference islandRef = storageRef.child("image/girl480x600.jpg");
        FirebaseStorage storage = FirebaseStorage.getInstance();
//        Log.d("USER", "downdFile: "+user.getImage());
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("ProfileUser").child(user.getImage().toString());//+".jpg");
//
//    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    v = inflater.inflate(R., null);
        try {
            File localFile = File.createTempFile("tempfile", ".jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("down", "success: ");

                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ImageView imgProduct = (ImageView) findViewById(R.id.profile_avatar);
                    imgProduct.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("down", "onFailure: ");
                }
            });

        } catch (IOException e) {

        }

    }
}
