package com.infotech.wedonate.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.infotech.wedonate.ui.home_module.map;
import com.infotech.wedonate.util.FetchLocation;
import com.infotech.wedonate.util.asyncTask;

public class locationservice extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //try {
                FetchLocation location = new FetchLocation(getApplicationContext());
                //int i =0;
               while (true) {
                    location.GetLocation(new asyncTask() {
                        @Override
                        public void actionPerformed() { }
                    });
                    location.storelocation(new asyncTask() {
                        @Override
                        public void actionPerformed() {

                        }
                    });
                    Log.d("Service", "started");
                   try {
                       Thread.sleep(30000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                }
            }
        }).start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


