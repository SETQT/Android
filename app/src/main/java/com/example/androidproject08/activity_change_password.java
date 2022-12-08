package com.example.androidproject08;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_change_password extends Activity implements View.OnClickListener {

    // khai báo biến UI
    View ic_back_change_password;
    Button record_btn_save_profile;
    EditText edittext_new_password, edittext_confirm_new_password;

    // kết nối đến firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // Biến xử lý
    String userId = "", propNeedUpdate = "";
    static String CHANNEL_ID = "GROUP8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ic_back_change_password = (View) findViewById(R.id.ic_back_change_password);
        ic_back_change_password.setOnClickListener(this);
        record_btn_save_profile = (Button) findViewById(R.id.record_btn_save_profile);
        record_btn_save_profile.setOnClickListener(this);
        edittext_confirm_new_password = (EditText) findViewById(R.id.edittext_confirm_new_password);
        edittext_new_password = (EditText) findViewById(R.id.edittext_new_password);

        // nhận dữ liệu đúng thuộc tính cần chỉnh sửa
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        propNeedUpdate = bundle.getString("propNeedUpdate");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == record_btn_save_profile.getId()) {
            String password = edittext_confirm_new_password.getText().toString();
            String passwordConfirm = edittext_confirm_new_password.getText().toString();

            // invalid password
            if (password.equals("") || passwordConfirm.equals("")) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                return;
            }

            // sai mật khẩu nhập lại
            if (!password.equals(passwordConfirm)) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            showConfirmPassword(password);
        }

        // trở lại activity myrecord
        if (view.getId() == ic_back_change_password.getId()) {
            try {
                Intent moveActivity = new Intent(getApplicationContext(), activity_record.class);
                startActivity(moveActivity);
            } catch (Exception error) {
                Log.e("ERROR", "Activity_change_password onClick: ", error);
                throw error;
            }
        }
    }

    // hiện form xác nhận mật khẩu => thay đổi lên db nếu mật khẩu đúng
    void showConfirmPassword(String newPassword) {
        final Dialog dialog = new Dialog(activity_change_password.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_authentication);

        EditText input_password_authentication = dialog.findViewById(R.id.input_password_authentication);
        Button btn_send_password_authentication = dialog.findViewById(R.id.btn_send_password_authentication);

        btn_send_password_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = input_password_authentication.getText().toString();
                try {
                    usersRef.document(userId)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        User user = document.toObject(User.class);
                                        if (user.checkPassword(password)) {
                                            try {
                                                // mã hóa password
                                                String hashPassword = user.hashPassword(newPassword);

                                                // lưu thông tin sau khi đã edit => trở lại trang xem thông tin chi tiết
                                                usersRef.document(userId).update(propNeedUpdate, hashPassword);
                                                Toast.makeText(getApplication(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                                                NotificationBar.createdNotification(getApplication(), CHANNEL_ID, "Thông báo", "Mật khẩu của bạn vừa được thay đổi thành công!");

                                                Intent moveActivity = new Intent(getApplicationContext(), activity_record.class);
                                                startActivity(moveActivity);
                                            } catch (Exception error) {
                                                Log.e("Error", "activity_edit_profile: ", error);
                                                return;
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            });
                }catch (Exception error) {
                    Log.e("ERROR", "Activity_change_password onClick: ", error);
                    return;
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}