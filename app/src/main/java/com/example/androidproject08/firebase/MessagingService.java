package com.example.androidproject08.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.androidproject08.R;
import com.example.androidproject08.activities.ChatActivity;
import com.example.androidproject08.activity_dashboard;
import com.example.androidproject08.activity_myorder;
import com.example.androidproject08.activity_voucher;
import com.example.androidproject08.models.UserChat;
import com.example.androidproject08.utilities.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String channelId = "", title = "", body = "";
        int notificationId = new Random().nextInt();
        PendingIntent pendingIntent = null;
        Intent intent = null;
        String typeNotification = "";
        try {
            typeNotification = remoteMessage.getNotification().getTag();
        } catch (Exception error) {

        }

        switch (typeNotification) {
            case "SERVER_ORDER":
                channelId = "notification_admin_to_user";
                title = remoteMessage.getNotification().getTitle();
                body = remoteMessage.getNotification().getBody();

                intent = new Intent(this, activity_myorder.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("stateMyOrder", "2");
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                break;
            case "SERVER_PRODUCT":
                channelId = "notification_admin_to_user";
                title = remoteMessage.getNotification().getTitle();
                body = remoteMessage.getNotification().getBody();

                intent = new Intent(this, activity_dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                break;
            case "SERVER_VOUCHER":
                channelId = "notification_admin_to_user";
                title = remoteMessage.getNotification().getTitle();
                body = remoteMessage.getNotification().getBody();

                intent = new Intent(this, activity_voucher.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                break;
            default:
                break;
        }

        if (typeNotification.equals("")) {
            UserChat user = new UserChat();
            user.id = remoteMessage.getData().get(Constants.KEY_ADMIN_ID);
            user.fullName = remoteMessage.getData().get(Constants.KEY_ADMIN_NAME);
            user.token = remoteMessage.getData().get(Constants.KEY_FCM_TOKEN);

            title = user.fullName;
            body = remoteMessage.getData().get(Constants.KEY_MESSAGE);

            channelId = "chat_message";

            intent = new Intent(this, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("admin", user);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.icon_logo);
        builder.setContentTitle(title);
        builder.setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                body
        ));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = channelId;
            String channelDescription = "This notification channel is used for chat message notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, builder.build());
    }
}
