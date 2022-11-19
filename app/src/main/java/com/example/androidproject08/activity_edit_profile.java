package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_edit_profile extends Activity implements View.OnClickListener {

    // khai báo biến UI
    View icon_back;
    Button record_btn_save_profile;
    EditText edit_profile;

    // kết nối đến firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // Biến xử lý
    String userId = "", propNeedUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);
        record_btn_save_profile = (Button) findViewById(R.id.record_btn_save_profile);
        record_btn_save_profile.setOnClickListener(this);
        edit_profile = (EditText) findViewById(R.id.edit_profile);

        // nhận dữ liệu đúng thuộc tính cần chỉnh sửa
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        propNeedUpdate = bundle.getString("propNeedUpdate");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            // trở lại trang xem thông tin chi tiết
            Intent moveActivity = new Intent(getApplicationContext(), activity_record.class);
            startActivity(moveActivity);
        }

        if (view.getId() == record_btn_save_profile.getId()) {
            try {
                // lưu thông tin sau khi đã edit => trở lại trang xem thông tin chi tiết
                usersRef.document(userId).update(propNeedUpdate, edit_profile.getText().toString());
                Toast.makeText(getApplication(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                Intent moveActivity = new Intent(getApplicationContext(), activity_record.class);
                startActivity(moveActivity);
            } catch (Exception error) {
                Log.e("TAG", "onClick: ", error);
                return;
            }
        }
    }
}
