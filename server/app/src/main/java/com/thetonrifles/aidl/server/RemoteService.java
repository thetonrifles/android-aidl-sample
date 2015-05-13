package com.thetonrifles.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.Date;

public class RemoteService extends Service {

    private final IRemoteServiceBinder.Stub mBinder = new IRemoteServiceBinder.Stub() {

        @Override
        public long getCurrentTime() throws RemoteException {
            return (new Date()).getTime();
        }

        @Override
        public void showToast() throws RemoteException {
            (new Handler(Looper.getMainLooper())).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteService.this, "test", Toast.LENGTH_SHORT).show();
                }
            });
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
