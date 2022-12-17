package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class activity_see_evaluate extends Activity implements View.OnClickListener {
    ListView see_evaluate_listview;
    View icon_back;

    User user;

    ArrayList<CommentEvaluate> listComments = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference commentsRef = db.collection("comments");
    CollectionReference productsRef = db.collection("products");
    CollectionReference usersRef = db.collection("users");

    String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_evaluate);

        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        see_evaluate_listview = (ListView) findViewById(R.id.see_evaluate_listview);

        usersRef
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);
                            }
                        }
                    }
                });

        activity_see_evaluate.comment_asynctask cmt_at = new activity_see_evaluate.comment_asynctask();
        cmt_at.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent();
            moveActivity = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(moveActivity);
        }
    }


    private class comment_asynctask extends AsyncTask<Void, CommentEvaluate, CommentEvaluate> {
        comment_asynctask() {
        }

        String idDoc = "";

        CommentEvaluate commentEvaluate = new CommentEvaluate();

        @Override
        protected CommentEvaluate doInBackground(Void... voids) {
            try {
                commentsRef
                        .whereEqualTo("user", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Boolean isHave = false;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Comment comment = document.toObject(Comment.class);
                                        isHave = true;
                                        idDoc = comment.getIdProduct();
                                        productsRef.document(idDoc).get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document2 = task.getResult();
                                                            Product product = document2.toObject(Product.class);
                                                            commentEvaluate = merge(comment, product, user);
                                                            publishProgress(commentEvaluate);
                                                        }
                                                    }
                                                });
                                    }
                                    if (!isHave) {
                                        publishProgress();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_see_evaluate: " + error);
            }
            return null;
        }

        protected void onProgressUpdate(CommentEvaluate... comments) {
            super.onProgressUpdate(comments);
            if (comments.length == 0) {
                listComments.clear();
            } else {
                listComments.add(comments[0]);
            }
            try {
                CustomSeeEvaluateListViewAdapter myAdapter = new CustomSeeEvaluateListViewAdapter(getApplicationContext(), R.layout.custom_listview_see_evaluate, listComments);
                see_evaluate_listview.setAdapter(myAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "activity_see_evaluate: ", error);
                return;
            }
        }
    }

    public CommentEvaluate merge(Comment cmt, Product pro, User u) {
        CommentEvaluate commentEvaluate = new CommentEvaluate();

        commentEvaluate.setNameProduct(pro.getName());
        commentEvaluate.setImgProduct(pro.getImage());
        commentEvaluate.setColorProduct(cmt.getColorProduct());
        commentEvaluate.setContent(cmt.getContent());
        commentEvaluate.setCountStar(cmt.getCountStar());
        commentEvaluate.setCreatedAt(cmt.getCreatedAt());
        commentEvaluate.setIdProduct(cmt.getIdProduct());
        commentEvaluate.setSizeProduct(cmt.getSizeProduct());
        commentEvaluate.setReply(cmt.getReply());
        commentEvaluate.setUser(cmt.getUser());
        commentEvaluate.setAvatar(u.getImage());

        return commentEvaluate;
    }

}
