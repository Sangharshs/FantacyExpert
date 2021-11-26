package com.crictone.teamexpert;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyServices extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("inside", "oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("not yet implement");

    }

    public Boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1 * 1000 - SystemClock.elapsedRealtime() % 1000);
            Intent brodcastIntent = new Intent();
            brodcastIntent.setAction(MainActivity.BrodcastStringForAction);
            brodcastIntent.putExtra("online_status", "" + isOnline(MyServices.this));
            sendBroadcast(brodcastIntent);
        }
    };
}
