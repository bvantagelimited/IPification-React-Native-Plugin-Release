package com.reactlibrarynativeipification;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.util.List;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.ipification.mobile.sdk.im.ui.IMVerificationActivity;
import com.ipification.mobile.sdk.android.CellularService;
import com.ipification.mobile.sdk.android.IPConfiguration;
import com.ipification.mobile.sdk.android.callback.CellularCallback;
import com.ipification.mobile.sdk.android.exception.CellularException;
import com.ipification.mobile.sdk.android.response.CoverageResponse;

import org.jetbrains.annotations.NotNull;

public class RNIPNotificationModule extends ReactContextBaseJavaModule {
    private final Context context;
    private static final String TAG = "RNIPNotification";

    RNIPNotificationModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

 
    @Override
    public String getName() {
        return "RNIPNotification";
    }

    private boolean isNotificationActivityRunning() {
        Log.d("SDK", "isNotificationActivityRunning");
        ActivityManager activityManager = (ActivityManager) this.context.getSystemService(this.context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if (tasks != null && !tasks.isEmpty()) {
            return tasks.get(0).topActivity.getClassName().equals(IMVerificationActivity.class.getName());
        }
        return false;
    }

    @ReactMethod
    public void showAndroidNotification(String title, String messageBody, String notificationFolder , String notificationFileName,  Promise p) {
        Class accessClass = IMVerificationActivity.class;
        if (!isNotificationActivityRunning()) {
            // if app is not running , do nothing
            return;
        }
        
        Log.d(TAG, "isNotificationActivityRunning()$isNotificationActivityRunning");
        Intent intent = new Intent(this.context, accessClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent;
        // support android 12
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(
                    this.context, IPConfiguration.getInstance().getREQUEST_CODE() /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
        }else{
            pendingIntent = PendingIntent.getActivity(
                    this.context, IPConfiguration.getInstance().getREQUEST_CODE()  /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this.context, channelId)
                .setSmallIcon(this.context.getResources().getIdentifier(notificationFileName, notificationFolder , this.context.getPackageName()))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(this.context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "IPification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(IPConfiguration.getInstance().getNOTIFICATION_ID() /* ID of notification */, notificationBuilder.build());
    }

    
}
