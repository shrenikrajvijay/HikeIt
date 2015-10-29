package com.mountaineer.trekking.hikeit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by vijayshrenikraj on 5/13/15.
 */
public class MyTimerTask extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void run() {

    }
}
