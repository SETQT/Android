package com.example.androidproject08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class activity_login extends Activity implements View.OnClickListener {
    View btn_register;
    EditText edittext_tk, edittext_mk;
    Button btn_login, btn_google;

    // google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    // facebook
    private CallbackManager mCallbackManager;

    // kết nối sqlite
    SQLiteDatabase sqlite;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        btn_register = (View) findViewById(R.id.rectangle_4);
        btn_register.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_dangnhap);
        btn_login.setOnClickListener(this);
        edittext_tk = (EditText) findViewById(R.id.edittext_tk);
        edittext_mk = (EditText) findViewById(R.id.edittext_mk); // mật khẩu người dùng
        btn_google = (Button) findViewById(R.id.btn_google);
        btn_google.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btn_register.getId()) {
            // chuyển sang activity đăng ký
            Intent moveActivity = new Intent(activity_login.this, activity_register.class);
            startActivity(moveActivity);
        }

        if (view.getId() == btn_login.getId()) {
            String tk = edittext_tk.getText().toString();// tài khoản người dùng
            String mk = edittext_mk.getText().toString();// mật khẩu người dùng

            // kiem tra empty
            if (tk.equals("") || mk.equals("")) {
                new AlertDialog.Builder(activity_login.this)
                        .setMessage("Vui lòng điền đầy đủ thông tin!")
                        .setCancelable(true)
                        .show();

                return;
            }

            usersRef.whereEqualTo("username", tk)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    if (user.checkPassword(mk)) {
                                        if (user.getStatus() == 0) {
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
                                            new AlertDialog.Builder(activity_login.this)
                                                    .setTitle("KHÓA TÀI KHOẢN")
                                                    .setMessage("Chúng tôi rất lấy làm tiết tài khoản của bạn đã bị cấm vì vi phạm chính sách của chúng tôi!")
                                                    .setCancelable(true)
                                                    .show();
                                        }
                                    } else {
                                        new AlertDialog.Builder(activity_login.this)
                                                .setMessage("Tài khoản hoặc mật khẩu sai!")
                                                .setCancelable(true)
                                                .show();
                                    }
                                }
                            }
                        }
                    });
        }

        if (view.getId() == btn_google.getId()) {
            SignInWithGoogle();
        }
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

    void SignInWithGoogle() {
        Intent singInIntent = gsc.getSignInIntent();
        startActivityForResult(singInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                // lấy thông tin từ gg
                GoogleSignInAccount accountGG = GoogleSignIn.getLastSignedInAccount(this);

                String email = accountGG.getEmail().toString();
                String fullname = accountGG.getDisplayName().toString();
                String username = accountGG.getId().toString();
                User newUser = new User(username, fullname, email);
                newUser.setImage(accountGG.getPhotoUrl().toString());

                usersRef.whereEqualTo("username", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean isHave = false;

                                    for (DocumentSnapshot document : task.getResult()) {
                                        isHave = true;
                                    }

                                    // chưa có thì tạo account mới
                                    if (!isHave) {
                                        usersRef.add(newUser);
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
                            }
                        });

            } catch (Exception e) {
                Toast.makeText(activity_login.this, "Thất bại!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
