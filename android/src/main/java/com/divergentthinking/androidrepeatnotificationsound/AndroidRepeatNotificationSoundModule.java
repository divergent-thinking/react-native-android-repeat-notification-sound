package com.divergentthinking.androidrepeatnotificationsound;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class AndroidRepeatNotificationSoundModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    public static final String LOG_TAG = "RNRepeatNSound";

    public AndroidRepeatNotificationSoundModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AndroidRepeatNotificationSound";
    }

    @ReactMethod
    public void createRepeatNotificationWithRepeatSound(String title, String message, String channelID) {
        String packageName = reactContext.getPackageName();
        Intent launchIntent = reactContext.getPackageManager().getLaunchIntentForPackage(packageName);
        int iconResId = getAppResourceId("ic_stat_ic_notification", "drawable");
        try {
            String className = launchIntent.getComponent().getClassName();
            Class<?> activityClass = Class.forName(className);
            Intent activityIntent = new Intent(reactContext, activityClass);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pIntent = PendingIntent.getActivity(reactContext, 0, activityIntent, 0);
            Notification n  = new Notification.Builder(this.reactContext, channelID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pIntent)
                    .setSmallIcon(iconResId)
                    .setAutoCancel(true)
                    .build();
            n.flags = Notification.FLAG_INSISTENT;
            NotificationManager notificationManager = (NotificationManager) reactContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Send Notification", e);
            return;
        }
    }
    private int getAppResourceId(String resName, String resType) {
        return reactContext.getResources().getIdentifier(resName, resType, reactContext.getPackageName());
    }
 }
