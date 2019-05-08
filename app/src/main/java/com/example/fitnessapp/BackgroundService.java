package com.example.fitnessapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class BackgroundService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


//background thread that executes code every hour if user selects office mode..
public void displayNotificaiton(){
    Thread t = new Thread() {
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(10000*60*60);

                    //display notifications every 60 minutes
                    NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify=new Notification.Builder
                            (getApplicationContext()).setContentTitle("Messsage").setContentText("Take a break, walk around!").
                            setContentTitle("Stay fit!").setSmallIcon(R.drawable.notiificaiton).build();

                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                    notif.notify(0, notify);

                } catch (InterruptedException ie) {
                    Toast.makeText(BackgroundService.this, "Try again...", Toast.LENGTH_SHORT).show();

                }
            }
        }
    };
    t.start();
}

    //execute code in the background
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        displayNotificaiton();
        return START_STICKY;


    }

    //code to be executed to stop background activity
    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
