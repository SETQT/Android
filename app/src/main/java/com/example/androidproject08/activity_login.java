package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class activity_login extends Activity {
    View btn_register;
    EditText edittext_tk, edittext_mk;
    Button btn_login;

    // facebook
    private CallbackManager mCallbackManager;

    // kết nối sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = (View) findViewById(R.id.rectangle_4);
        btn_login = (Button) findViewById(R.id.btn_dangnhap);
        edittext_tk = (EditText) findViewById(R.id.edittext_tk);
        edittext_mk = (EditText) findViewById(R.id.edittext_mk); // mật khẩu người dùng

        // login bằng facebook
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
                        Toast.makeText(getApplicationContext(), "Đăng nhập bằng facebook thất bại!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(), "Đăng nhập bằng facebook thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edittext_tk.getText().toString();// tài khoản người dùng
                String mk = edittext_mk.getText().toString();// mật khẩu người dùng

                // kiem tra empty
                if (tk.equals("") || mk.equals("")) {
                    Toast.makeText(activity_login.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }


//                activity_login_asynctask login_asynctask = new activity_login_asynctask(activity_login.this, tk);
//                login_asynctask.execute();

                Handle.readData(new FirestoreCallBack() {
                    @Override
                    public void onCallBack(List<User> list) {
                        if (Handle.usernameList.size() == 0) {
                            Toast.makeText(activity_login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            // kiểm tra password
                            if (Handle.usernameList.get(0).checkPassword(mk)) {
                                // thêm vào cookie => lần sau vào không cần đăng nhập nữa
                                // khi người dùng đăng nhập vào thì ta lấy username của người dùng lưu lại
                                // khi khởi động ứng dụng ta truy vấn vào sqlite này để xem nếu username tồn tại rồi thì
                                // không cần đăng nhập vào thẳng trang dash_board cho người dùng
                                File storagePath = getApplication().getFilesDir();
                                String myDbPath = storagePath + "/" + "loginDb";
                                sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

                                if (!Handle.tableExists(sqlite, "USER")) {
                                    // create table USER
                                    sqlite.execSQL("create table USER ("
                                            + "username text PRIMARY KEY);");
                                }

                                sqlite.execSQL("insert into USER(username) values ('" + tk + "');");

                                // chuyển sang giao diện chính
                                Intent moveActivity = new Intent(activity_login.this, activity_dashboard.class);
                                startActivity(moveActivity);
                            } else {
                                Toast.makeText(activity_login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                }, tk);
            }
        });

        // chuyển sang activity đăng ký
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(activity_login.this, activity_register.class);
                startActivity(moveActivity);
            }
        });
    }

    // lấy dữ liệu từ facebook => đưa dữ liệu lên firestore
    private void setFacebookData(final LoginResult loginResult) {
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

                                    if (!Handle.tableExists(sqlite, "USER")) {
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
