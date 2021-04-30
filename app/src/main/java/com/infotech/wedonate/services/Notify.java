package com.infotech.wedonate.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.Token_model;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.member.notification_intent;


public class Notify extends FirebaseMessagingService {


    String title,body;
    String ch_id = "1";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("TOKEN",s+"heelo");
        data_bank.noti_token = new Token_model(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try{
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");
        }catch (Exception e){
            e.printStackTrace();
        }
        notification();
    }

    private void notification(){
        createNotificationChannel();
        Intent i;
        PendingIntent pendingintent;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ch_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(data_bank.curUser.getUsertype().equalsIgnoreCase("member")) {
            i = new Intent(this, notification_intent.class);
            Log.d("notibody",body.substring(16));
            i.putExtra("donoremail",body.substring(16));
            i.putExtra("activity","notification");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingintent = PendingIntent.getActivity(this, 1,i, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingintent);
        }


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(404, builder.build());

    }
    private void createNotificationChannel() {
        //Only for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my notification";
            String description = "notify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ch_id, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}