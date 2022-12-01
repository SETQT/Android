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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_record extends Activity {
    // sqlite
    SQLiteDatabase sqlite;

    // khai báo biến UI
    View record_ic_back;
    ImageView camera;
    ImageView cameraBackground;
    ImageView header;
    ImageView avatar;

    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE_BACKGROUND = 200;
    User userForimage;
    Uri imageUri;
    Uri imageBackUri;
    String userName;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openGalleryBackground() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_BACKGROUND);
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            CircleImageView avatar = (CircleImageView) findViewById(R.id.record_avatar);
            imageUri = data.getData();
            avatar.setImageURI(imageUri);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersRef = db.collection("users");
            usersRef
                    .whereEqualTo("username", userName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    user.setUserId(document.getId()); // lấy id document của user

                                    uploadFile(user.getUsername().toString()+"avatar", imageUri);
                                    usersRef.document(user.getUserId()).update("image", user.getUsername().toString()+"avatar");
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_BACKGROUND) {
            ImageView avatar = (ImageView) findViewById(R.id.record_rectangle_header_profile);
            imageBackUri = data.getData();
            avatar.setImageURI(imageBackUri);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersRef = db.collection("users");
            usersRef
                    .whereEqualTo("username", userName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    user.setUserId(document.getId()); // lấy id document của user
                                    uploadFile(user.getUsername().toString() + "background", imageBackUri);
                                    usersRef.document(user.getUserId()).update("imageBg", user.getUsername().toString()+"background");
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

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
        userName = username;


        loadImage(userName);

        if (username == null) {
            return;
        }

        // query dữ liệu cho qua listview
        record_asynctask r_at = new record_asynctask(activity_record.this, username, imageUri, imageBackUri);
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

    public void loadImage(String name) {
        header = (ImageView) findViewById(R.id.record_rectangle_header_profile);
        avatar = (ImageView) findViewById(R.id.record_avatar);

        try {
            usersRef
                    .whereEqualTo("username", name)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);

                                    if(user.getImage() != null) {
                                        downloadFile(avatar,name+"avatar");
                                    }

                                    if(user.getImageBg() != null) {
                                        downloadFile(header,name+"background");
                                    }

                                    if(user.getImage() != null && user.getImageBg() != null) {
                                        downloadFile(avatar,name+"avatar");
                                        downloadFile(header,name+"background");
                                    }
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception error) {
            Log.e("ERROR", "activity_profile loadImage: ", error);
        }
    }


    public void downloadFile(ImageView avatar,String name) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        StorageReference islandRef = storageRef.child("ProfileUser/"+name);

        try {
            File localFile = File.createTempFile("tempfile", ".jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    avatar.setImageBitmap(bitmap);
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

    public void uploadFile(String name, Uri avatar) {
        Log.d("upload", "uploadFile:okk ");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference test = storageRef.child("ProfileUser/" + name);

        UploadTask uploadTask = test.putFile(avatar);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplication(), "Upload Thất bại", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplication(), "  Upload Thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
