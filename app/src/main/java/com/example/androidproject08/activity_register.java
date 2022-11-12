package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activity_register extends Activity {
    // khai báo dữ liệu UI
    View btn_login;
    Button btn_register, btn_google_dk, btn_facebook_dk;
    EditText edittext_tk_dk, edittext_mk_dk, edittext_nhaplaimk;

    // khai báo dữ liệu BE

    // facebook
    private CallbackManager mCallbackManager;

    // kết nối sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login = (View) findViewById(R.id.rectangle_3);
        btn_register = (Button) findViewById(R.id.btn_dangky);
        edittext_tk_dk = (EditText) findViewById(R.id.edittext_tk_dk);
        edittext_mk_dk = (EditText) findViewById(R.id.edittext_mk_dk);
        edittext_nhaplaimk = (EditText) findViewById(R.id.edittext_nhaplaimk);
        btn_google_dk = (Button) findViewById(R.id.btn_google_dk);

        // đăng ký bằng tài khoản google
        btn_google_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // xử lý

            }
        });

        // đăng nhập/ đăng ký bằng facebook
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setFacebookData(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(activity_register.this, "Đăng nhập bằng facebook thất bại!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(activity_register.this, "Đăng nhập bằng facebook thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edittext_tk_dk.getText().toString();
                String mk = edittext_mk_dk.getText().toString();
                String mkAgain = edittext_nhaplaimk.getText().toString();

                if (tk.equals("") || mk.equals("") || mkAgain.equals("")) {
                    Toast.makeText(activity_register.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // kiểm tra mật khẩu nhập lại có khớp hay không
                if (!mk.equals(mkAgain)) {
                    Toast.makeText(activity_register.this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check tài khoản đã tồn tại hay chưa
                Handle.readData(new FirestoreCallBack() {
                    @Override
                    public void onCallBack(List<User> list) {
                        if (Handle.usernameList.size() == 0) {
                            // tạo user mới và thêm vào database
                            User newUser = new User(tk, mk);
                            Handle.usersRef.add(newUser);

                            // chuyển sang giao diện đăng nhập
                            Intent moveActivity = new Intent(getApplicationContext(), activity_login.class);
                            startActivity(moveActivity);
                        } else { // tài khoản đã tồn tại
                            Toast.makeText(activity_register.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }, tk);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(activity_register.this, activity_login.class);
                startActivity(moveActivity);
            }
        });
    }

    // lấy dữ liệu từ facebook => đưa dữ liệu lên firestore
    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            // vì id của facebook cung cấp cho user là độc nhất nên khi người dùng sử dụng tính năng đăng ký với facebook thì username = id
                            String email = response.getJSONObject().getString("email");

                            String firstName = response.getJSONObject().getString("first_name");
                            String middleName = response.getJSONObject().getString("middle_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String fullname = firstName + " " + middleName + " " + lastName;

                            String username = response.getJSONObject().getString("id");


                            // check tài khoản đã tồn tại hay chưa
                            Handle.readData(new FirestoreCallBack() {
                                @Override
                                public void onCallBack(List<User> list) {
                                    if (Handle.usernameList.size() == 0) {
                                        // tạo user mới và thêm vào database
                                        User newUser = new User(username, fullname, email);
                                        Handle.usersRef.add(newUser);
                                    }

                                    // thêm vào cookie => lần sau vào không cần đăng nhập nữa
                                    File storagePath = getApplication().getFilesDir();
                                    String myDbPath = storagePath + "/" + "loginDb";
                                    sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

                                    if(!Handle.tableExists(sqlite, "USER")) {
                                        // create table USER
                                        sqlite.execSQL("create table USER ("
                                                + "username text PRIMARY KEY);");
                                    }

                                    sqlite.execSQL("insert into USER(username) values ('" + username + "');");

                                    // chuyển sang giao diện dash_board
                                    Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                                    startActivity(moveActivity);
                                }
                            }, username);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name, middle_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
