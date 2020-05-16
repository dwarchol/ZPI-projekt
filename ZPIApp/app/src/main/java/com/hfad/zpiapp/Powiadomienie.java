package com.hfad.zpiapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.EventLogTags;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Powiadomienie {
    Context ctx;
    static String CHANNEL_ID = "SV";
    static String CHANNEL_ID2 = "S";
    static String CHANNEL_ID3 = "V";
    static String CHANNEL_ID4 = "silence";
    SharedPreferences preferences;
    int notificationId=0;

    public Powiadomienie(Context c) {
        this.ctx=c;
        preferences = ctx.getApplicationContext().getSharedPreferences("APP_SETTINGS",0);
        createNotificationChannelSV();
        createNotificationChannelS();
        createNotificationChannelV();
        createNotificationChannelSilence();
    }


    private void createNotificationChannelSV() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.NotifyCh);
            String description = ctx.getString(R.string.NotifyD);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
    private void createNotificationChannelS() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.NotifyCh2);
            String description = ctx.getString(R.string.NotifyD2);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID2, name, importance);
            channel.setDescription(description);
            channel.enableVibration(false);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationChannelV() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.NotifyCh3);
            String description = ctx.getString(R.string.NotifyD3);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID3, name, importance);
            channel.setDescription(description);
            channel.setSound(null,null);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationChannelSilence() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.NotifyCh4);
            String description = ctx.getString(R.string.NotifyD4);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID4, name, importance);
            channel.setDescription(description);
            channel.enableVibration(false);
            channel.setSound(null,null);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void sendNotification(String Title, String Description) //mozna dodać zdjęcie, inny dźwięk, inny wzór wibracji etc.
    {
        // Create an explicit intent for an Activity in your app
       // Intent intent = new Intent(ctx, AlertDetails.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
        NotificationCompat.Builder builder;
        if(preferences.getBoolean("soundBool",true)==true && preferences.getBoolean("vibrationBool",true)==true) {
                    builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                            .setSmallIcon(R.drawable.book)
                    .setContentTitle(Title)
                    .setContentText(Description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                   // .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }
        else
            if(preferences.getBoolean("soundBool",true)==true && preferences.getBoolean("vibrationBool",true)==false) {
                        builder = new NotificationCompat.Builder(ctx, CHANNEL_ID2)
                                .setSmallIcon(R.drawable.book)
                        .setContentTitle(Title)
                        .setContentText(Description)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                       // .setContentIntent(pendingIntent)
                        .setVibrate(null)
                        .setAutoCancel(true);
            }
            else

                if(preferences.getBoolean("soundBool",true)==false && preferences.getBoolean("vibrationBool",true)==true) {
                            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID3)
                                    .setSmallIcon(R.drawable.book)
                            .setContentTitle(Title)
                            .setContentText(Description)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                          //  .setContentIntent(pendingIntent)
                            .setSound(null)
                            .setAutoCancel(true);
                }
                else
                if(preferences.getBoolean("soundBool",true)==false && preferences.getBoolean("vibrationBool",true)==false) {
                            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID4)
                            .setSmallIcon(R.drawable.book)
                            .setContentTitle(Title)
                            .setContentText(Description)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                          //  .setContentIntent(pendingIntent)
                            .setVibrate(null)
                            .setAutoCancel(true);
                }
                else
                {
                    builder = new NotificationCompat.Builder(ctx, CHANNEL_ID);
                }

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
                    // notificationId is a unique int for each notification that you must define
                notificationManager.notify(notificationId, builder.build());
                notificationId++;
        }

    public void sendNotificationWithIntent(String Title, String Description) //mozna dodać zdjęcie, inny dźwięk, inny wzór wibracji etc.
    {
        // Create an explicit intent for an Activity in your app
       // PackageManager pm = ctx.getPackageManager();
       // Intent launchIntent = pm.getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
         Intent intent = new Intent(ctx, Glowna.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
        NotificationCompat.Builder builder;
        if(preferences.getBoolean("soundBool",true)==true && preferences.getBoolean("vibrationBool",true)==true) {
            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                    .setSmallIcon(R.drawable.book)
                    .setContentTitle(Title)
                    .setContentText(Description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }
        else
        if(preferences.getBoolean("soundBool",true)==true && preferences.getBoolean("vibrationBool",true)==false) {
            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID2)
                    .setSmallIcon(R.drawable.book)
                    .setContentTitle(Title)
                    .setContentText(Description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setVibrate(null)
                    .setAutoCancel(true);
        }
        else

        if(preferences.getBoolean("soundBool",true)==false && preferences.getBoolean("vibrationBool",true)==true) {
            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID3)
                    .setSmallIcon(R.drawable.book)
                    .setContentTitle(Title)
                    .setContentText(Description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                     .setContentIntent(pendingIntent)
                    .setSound(null)
                    .setAutoCancel(true);
        }
        else
        if(preferences.getBoolean("soundBool",true)==false && preferences.getBoolean("vibrationBool",true)==false) {
            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID4)
                    .setSmallIcon(R.drawable.book)
                    .setContentTitle(Title)
                    .setContentText(Description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setVibrate(null)
                    .setAutoCancel(true);
        }
        else
        {
            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }
    }




