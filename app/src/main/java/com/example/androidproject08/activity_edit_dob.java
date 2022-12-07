package com.example.androidproject08;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class activity_edit_dob extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // khai báo biến UI
    View icon_back;
    Button record_btn_save_profile;
    TextView text_dob;
    Button select_dob;

    // kết nối đến firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // Biến xử lý
    String userId = "", propNeedUpdate = "";
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dob);

        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);
        record_btn_save_profile = (Button) findViewById(R.id.record_btn_save_profile);
        record_btn_save_profile.setOnClickListener(this);
        text_dob = (TextView) findViewById(R.id.text_dob);
        text_dob.setOnClickListener(this);

        select_dob = (Button) findViewById(R.id.select_dob);
        select_dob.setOnClickListener(this);

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
            showConfirmPassword();
        }

        if (view.getId() == select_dob.getId()) {
            showDateTimeDialog(text_dob);
        }
    }

    private void showDateTimeDialog(final TextView edittext_dob) {
        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                edittext_dob.setText("Ngày sinh: " + simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(activity_edit_dob.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // hiện form xác nhận mật khẩu => thay đổi lên db nếu mật khẩu đúng
    void showConfirmPassword() {
        final Dialog dialog = new Dialog(activity_edit_dob.this);

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
                                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                                Date newDob = df.parse(simpleDateFormat.format(calendar.getTime()));
                                                // lưu thông tin sau khi đã edit => trở lại trang xem thông tin chi tiết
                                                usersRef.document(userId).update(propNeedUpdate, newDob);
                                                Toast.makeText(getApplication(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

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
                } catch (Exception error) {
                    Log.e("ERROR", "Activity_change_password onClick: ", error);
                    return;
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}