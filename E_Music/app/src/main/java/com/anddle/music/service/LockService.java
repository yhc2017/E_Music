package com.anddle.music.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.anddle.music.widget.MyLockScreenActivity;

/**
 * 在需要的地方开启我们的服务，
 * startService(new Intent(this, LockService.class)).
 */

public class LockService extends Service {
    private String TAG = this.getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        IntentFilter mScreenOnFilter = new IntentFilter();
        mScreenOnFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mScreenOnFilter.addAction(Intent.ACTION_SCREEN_ON);
        LockService.this.registerReceiver(mScreenActionReceiver, mScreenOnFilter);

    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mScreenActionReceiver);
        // 在此重新启动,使服务常驻内存
        startService(new Intent(this, LockService.class));
    }


    private BroadcastReceiver mScreenActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                Intent LockIntent = new Intent(LockService.this, MyLockScreenActivity.class);
//如果已经启动了四个Activity：A，B，C和D，在D Activity里，想再启动一个Actvity B，但不变成A,B,C,D,B，而是希望是A,C,D,B
                LockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LockIntent);
            } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e(TAG, "screen off");
            }
        }
    };
}
