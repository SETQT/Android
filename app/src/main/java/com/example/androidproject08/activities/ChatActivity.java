package com.example.androidproject08.activities;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject08.adapters.ChatAdapter;
import com.example.androidproject08.databinding.ActivityChatBinding;
import com.example.androidproject08.models.ChatMessage;
import com.example.androidproject08.models.UserChat;
import com.example.androidproject08.utilities.Constants;
import com.example.androidproject08.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private UserChat receiverUser;
    private UserChat senderUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // kết nối firestore
        db = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
        receiverUser = new UserChat();

        setListeners();
        loadInfoSender();
        getAdmins(); // receiver get info
        //init();

    }

    private void init(UserChat receiverUser) {
        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
                receiverUser.image,
                preferenceManager.getString(Constants.KEY_USER_ID) // id của mình ( là userID người dùng)
        );
        binding.chatRecyclerView.setAdapter(chatAdapter);
        db = FirebaseFirestore.getInstance();
        listenerMessages();
    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }
            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
    };


    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, binding.inputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        db.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        binding.inputMessage.setText(null);
    }

    private void listenerMessages() {
        db.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.id)
                .addSnapshotListener(eventListener);
        db.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    // hàm load thông tin người nhận ( tức là admin)
    private void loadReceiverDetails(UserChat receiverUser) {
        //receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        if (receiverUser != null) {
            binding.nameUserChat.setText(receiverUser.fullName);
            loadImageReceiver(receiverUser.image, binding);
        }

    }

    private void setListeners() {
        binding.iconBack.setOnClickListener(v -> onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    // lấy thông tin admin cần chat
    private void getAdmins() {
        db.collection("admin")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<UserChat> admins = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            UserChat admin = new UserChat();
                            admin.id = queryDocumentSnapshot.getId();
                            admin.fullName = queryDocumentSnapshot.getString("name");
                            admin.image = queryDocumentSnapshot.getString("image");
                            admin.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            admins.add(admin);
                        }
                        if (admins.size() > 0) {
                            receiverUser = admins.get(0);
                            loadReceiverDetails(receiverUser);// lấy amdin đầu tiên: thật ra còn nhiều admin khác, phát triển sau
                            init(receiverUser);
                        } else {
                            loadReceiverDetails(null);
                        }
                    } else {
                        loadReceiverDetails(null);
                    }
                });
    }


    private void loadImageReceiver(String image, ActivityChatBinding binding) {
        try {
            Picasso.with(getApplicationContext()).load(image).into(binding.chatProfileAvatar);
        } catch (Exception error) {
            Log.e("ERROR", "activity_profile loadImage: ", error);
        }
    }


    private void loadInfoSender() {
        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        SQLiteDatabase sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        try {
            // lấy thông tin senderID
            db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        //Log.d("DOC", "sldoc: " + task.getResult().getDocuments().size());
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            senderUser = new UserChat();
                            senderUser.id = documentSnapshot.getId();
                            senderUser.fullName = documentSnapshot.getString("fullname");
                            senderUser.image = documentSnapshot.getString("image");
//                            Log.d("user", "loadInfoSender: img: " + senderUser.image);
//                            Log.d("user", "loadInfoSender: id: " + senderUser.id);
                            preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());// lưu ID sender
                        } else {

                            Log.d("ERROR", "loadInfoSender: lấy thông tin không thành công! ");
                        }
                    });
        } catch (Exception err){
            Log.d("ERROR", "loadInfoSender: ERROr" + err);
        }


    }


}