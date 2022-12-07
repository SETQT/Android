package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_view_product extends Activity implements View.OnClickListener {
    // biến UI
    ListView listview_comment_view_product;
    View ic_back_view_product, icon_cart;
    RelativeLayout rectangle_add_to_card_view_product, rectangle_buy_now_view_product;
    TextView number_cart, custom_mycart_number_product, number_evaluate_comment;
    Spinner spiner_size_view_product, spiner_color_view_product;
    Button custom_mycart_icon_increase, custom_mycart_icon_decrease;

    // biến xử lý
    String previousActivity, idDoc;
    String username = "";
    String linkImage = "";
    String size, color;
    Integer amount;
    ArrayList<Comment> listComments = new ArrayList<>();

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");
    CollectionReference cartsRef = db.collection("carts");
    CollectionReference usersRef = db.collection("users");
    CollectionReference commentsRef = db.collection("comments");

    // sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        rectangle_add_to_card_view_product = (RelativeLayout) findViewById(R.id.rectangle_add_to_card_view_product);
        rectangle_add_to_card_view_product.setOnClickListener(this);
        rectangle_buy_now_view_product = (RelativeLayout) findViewById(R.id.rectangle_buy_now_view_product);
        rectangle_buy_now_view_product.setOnClickListener(this);

        spiner_size_view_product = (Spinner) findViewById(R.id.spiner_type_size_view_product);
        spiner_color_view_product = (Spinner) findViewById(R.id.spiner_type_color_view_product);

        listview_comment_view_product = (ListView) findViewById(R.id.listview_comment_view_product);

        ic_back_view_product = (View) findViewById(R.id.ic_back_view_product);
        ic_back_view_product.setOnClickListener(this);
        icon_cart = (View) findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);

        number_cart = (TextView) findViewById(R.id.number_cart);
        number_evaluate_comment = (TextView) findViewById(R.id.number_evaluate_comment);
        custom_mycart_number_product = (TextView) findViewById(R.id.custom_mycart_number_product);
        amount = Integer.parseInt(custom_mycart_number_product.getText().toString());

        custom_mycart_icon_increase = (Button) findViewById(R.id.custom_mycart_icon_increase);
        custom_mycart_icon_increase.setOnClickListener(this);
        custom_mycart_icon_decrease = (Button) findViewById(R.id.custom_mycart_icon_decrease);
        custom_mycart_icon_decrease.setOnClickListener(this);

        // nhận dữ liệu từ các activity khác gửi tới
        Intent intent = getIntent();
        previousActivity = intent.getStringExtra("name_activity");
        idDoc = intent.getStringExtra("idDoc");

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        username = c1.getString(0);

        // lấy số lượng sản phẩm
        usersRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                number_cart.setText(user.getCart().get("amount").toString());
                                break;
                            }
                        }
                    }
                });

        avp_asynctask avp_at = new avp_asynctask();
        avp_at.execute();

        comment_asynctask cmt_at = new comment_asynctask();
        cmt_at.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == ic_back_view_product.getId()) {
            Intent moveActivity = new Intent();

            if (previousActivity == null) {
                moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            } else {
                switch (previousActivity) {
                    case "activity_search":
                        moveActivity = new Intent(getApplicationContext(), activity_search.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_dashboard":
                        moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                        startActivity(moveActivity);
                        break;
                    default:
                        break;
                }
            }
        }

        if (view.getId() == icon_cart.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            moveActivity.putExtra("idDoc", idDoc);
            moveActivity.putExtra("name_activity", "activity_view_product");
            startActivity(moveActivity);
        }

        if (view.getId() == rectangle_add_to_card_view_product.getId()) {
            try {
                productsRef.document(idDoc).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Product product = document.toObject(Product.class);

                                    MyCart ToCart = new MyCart(amount, "", product.getImage(), product.getName(), product.getPrice(), product.getSale(), username, document.getId().toString(), size, color);

                                    cartsRef
                                            .whereEqualTo("idDoc", ToCart.getIdDoc())
                                            .whereEqualTo("ownCart", ToCart.getOwnCart())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        boolean isDuplicated = false;
                                                        Integer totalAmount = 0;

                                                        // kiểm tra thử sản phẩm đã có trong giỏ hàng trước đó chưa
                                                        // nếu có thì cộng thêm số lượng vào
                                                        for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                            MyCart cart = document2.toObject(MyCart.class);

                                                            // check sản phẩm có trùng size + color hay không
                                                            if (MyCart.checkDuplicatedCart(ToCart, cart)) {
                                                                isDuplicated = true;
                                                                totalAmount = cart.getAmount() + amount;
                                                                cartsRef.document(document2.getId()).update("amount", totalAmount);
                                                            }
                                                        }

                                                        // nếu không thì tạo cart mới
                                                        if (!isDuplicated) {
                                                            cartsRef.add(ToCart);
                                                            // tăng số lượng trong giỏ hàng cho user
                                                            usersRef
                                                                    .whereEqualTo("username", ToCart.getOwnCart())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                    User user = document.toObject(User.class);
                                                                                    Map<String, Integer> userCart = new HashMap<>();
                                                                                    userCart.put("amount", user.getCart().get("amount") + amount);
                                                                                    number_cart.setText(userCart.get("amount").toString());
                                                                                    usersRef.document(document.getId()).update("cart", userCart);
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    } else {
                                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });
                                    Toast.makeText(activity_view_product.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_view_product: " + error);
            }
        }

        if (view.getId() == rectangle_buy_now_view_product.getId()) {
            // lấy những thông tin cần thiết cho đơn hàng
            String id = idDoc;

            String image = linkImage;

            TextView name_view_product = (TextView) findViewById(R.id.name_view_product);
            String name = name_view_product.getText().toString();

            TextView old_cost_view_product = (TextView) findViewById(R.id.old_cost_view_product);
            Integer oldCost = Integer.parseInt(old_cost_view_product.getText().toString().substring(1));

            TextView new_cost_view_product = (TextView) findViewById(R.id.new_cost_view_product);
            Integer newCost = Integer.parseInt(new_cost_view_product.getText().toString().substring(1));

            Integer count = amount;
            Integer total = newCost * count;

            Myorder orderProduct = new Myorder(id, image, name, size, color, oldCost, newCost, count, total);

            Intent moveActivity = new Intent(getApplicationContext(), activity_payment.class);
            moveActivity.putExtra("name_activity", "activity_view_product");
            moveActivity.putExtra("product", orderProduct);
            startActivity(moveActivity);
        }

        if (view.getId() == custom_mycart_icon_increase.getId()) {
            amount += 1;
            custom_mycart_number_product.setText(amount.toString());
        }

        if (view.getId() == custom_mycart_icon_decrease.getId()) {
            amount -= 1;
            if (amount < 1) {
                amount = 1;
            }

            custom_mycart_number_product.setText(amount.toString());
        }
    }

    private class avp_asynctask extends AsyncTask<Void, Product, Product> {
        avp_asynctask() {

        }

        @Override
        protected Product doInBackground(Void... voids) {
            try {
                productsRef
                        .document(idDoc).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Product product = document.toObject(Product.class);
                                    publishProgress(product);
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_view_product: " + error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Product... products) {
            super.onProgressUpdate(products);

            // thiết lập các thuộc tính cơ bản của product
            ImageView imgProduct = (ImageView) findViewById(R.id.mono_1);
            Picasso.with(getApplicationContext()).load(products[0].getImage()).into(imgProduct);
            linkImage = products[0].getImage();

            TextView name_view_product = (TextView) findViewById(R.id.name_view_product);
            name_view_product.setText(products[0].getName());

            TextView new_cost_view_product = (TextView) findViewById(R.id.new_cost_view_product);
            new_cost_view_product.setText("đ" + products[0].getPrice().toString());

            Integer oldPrice = (products[0].getPrice() / (100 - products[0].getSale())) * 100;

            TextView old_cost_view_product = (TextView) findViewById(R.id.old_cost_view_product);
            old_cost_view_product.setText("đ" + oldPrice.toString());

            TextView rate_reduce = (TextView) findViewById(R.id.rate_reduce);
            rate_reduce.setText("-" + products[0].getSale().toString() + "%");

            TextView sales_view_product = (TextView) findViewById(R.id.sales_view_product);
            sales_view_product.setText("Đã bán: " + products[0].getAmountOfSold().toString());

            ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
            expTv.setText(products[0].getDescription());

            // thiết lập bảng size
            ArrayAdapter<String> adapter_size = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spiner_payment_methods, products[0].getTypeSize().toArray(new String[products[0].getTypeSize().size()]));
            spiner_size_view_product.setAdapter(adapter_size);

            spiner_size_view_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    size = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            // thiết lập bằng màu
            ArrayAdapter<String> adapter_color = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spiner_payment_methods, products[0].getTypeColor().toArray(new String[products[0].getTypeColor().size()]));
            spiner_color_view_product.setAdapter(adapter_color);
            spiner_color_view_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    color = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private class comment_asynctask extends AsyncTask<Void, Comment, Comment> {
        comment_asynctask() {
        }

        @Override
        protected Comment doInBackground(Void... voids) {
            try {
                commentsRef
                        .whereEqualTo("idProduct", idDoc)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Comment comment = document.toObject(Comment.class);
                                    publishProgress(comment);
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_view_product: " + error);
            }
            return null;
        }

        protected void onProgressUpdate(Comment... comments) {
            super.onProgressUpdate(comments);
            listComments.add(comments[0]);

            CustomListCommentAdapter myAdapter = new CustomListCommentAdapter(getApplicationContext(), R.layout.custom_listview_comment_view_product, listComments);
            listview_comment_view_product.setAdapter(myAdapter);
            setListViewHeightBasedOnChildren(listview_comment_view_product);

            Integer amountOfComment = listComments.size();
            number_evaluate_comment.setText("(" + amountOfComment.toString() + " đánh giá)");
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}