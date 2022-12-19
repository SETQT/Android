package com.example.androidproject08;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class activity_register extends Activity implements View.OnClickListener {
    // khai báo dữ liệu UI
    View btn_login;
    Button btn_register, btn_google_dk, btn_facebook_dk;
    EditText edittext_tk_dk, edittext_mk_dk, edittext_nhaplaimk;

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
        setContentView(R.layout.activity_register);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        btn_login = (View) findViewById(R.id.rectangle_3);
        btn_login.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.btn_dangky);
        edittext_tk_dk = (EditText) findViewById(R.id.edittext_tk_dk);
        edittext_mk_dk = (EditText) findViewById(R.id.edittext_mk_dk);
        edittext_nhaplaimk = (EditText) findViewById(R.id.edittext_nhaplaimk);
        btn_google_dk = (Button) findViewById(R.id.btn_google_dk);
        btn_google_dk.setOnClickListener(this);

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
                usersRef.whereEqualTo("username", tk)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean isExisted = false;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        isExisted = true;
                                    }
                                    if (isExisted) {
                                        Toast.makeText(activity_register.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        User newUser = new User(tk, mk);
                                        usersRef.add(newUser);

                                        // chuyển sang giao diện đăng nhập
                                        Intent moveActivity = new Intent(getApplicationContext(), activity_login.class);
                                        startActivity(moveActivity);
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btn_login.getId()) {
            Intent moveActivity = new Intent(activity_register.this, activity_login.class);
            startActivity(moveActivity);
        }

        if (view.getId() == btn_google_dk.getId()) {
            SignInWithGoogle();
        }

        if (view.getId() == btn_register.getId()) {
            String tk = edittext_tk_dk.getText().toString();
            String mk = edittext_mk_dk.getText().toString();
            String mkAgain = edittext_nhaplaimk.getText().toString();

            if (tk.equals("") || mk.equals("") || mkAgain.equals("")) {
                new AlertDialog.Builder(activity_register.this)
                        .setMessage("Vui lòng điền đầy đủ thông tin!")
                        .setCancelable(true)
                        .show();
                return;
            }

            // kiểm tra mật khẩu nhập lại có khớp hay không
            if (!mk.equals(mkAgain)) {
                new AlertDialog.Builder(activity_register.this)
                        .setMessage("Xác thực mật khẩu không chính xác")
                        .setCancelable(true)
                        .show();
                return;
            }

            // check tài khoản đã tồn tại hay chưa
            usersRef.whereEqualTo("username", tk)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean isExisted = false;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    isExisted = true;
                                }

                                if (!isExisted) {
                                    User newUser = new User(tk, mk);
                                    usersRef.add(newUser);

                                    // chuyển sang giao diện đăng nhập
                                    Intent moveActivity = new Intent(getApplicationContext(), activity_login.class);
                                    startActivity(moveActivity);
                                } else {
                                    new AlertDialog.Builder(activity_register.this)
                                            .setMessage("Tài khoản này đã được sử dụng!")
                                            .setCancelable(true)
                                            .show();
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
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

                                                if (!isHave) {
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
                                        }
                                    });
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
                Toast.makeText(activity_register.this, "Thất bại!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
