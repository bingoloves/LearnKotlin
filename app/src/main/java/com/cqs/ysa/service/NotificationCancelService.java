package com.cqs.ysa.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * 通知栏点击取消
 */

public class NotificationCancelService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cancel = intent.getStringExtra("key_cancel");
        String confirm = intent.getStringExtra("key_conform");
        if (cancel != null) {
            Toast.makeText(getApplication(), "你点击了取消", Toast.LENGTH_LONG).show();
        }
        if (confirm != null) {
            Toast.makeText(getApplication(), "你点击了确定", Toast.LENGTH_LONG).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
