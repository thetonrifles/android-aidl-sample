package com.thetonrifles.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Date;

public class RemoteService extends Service {

    private final IRemoteServiceBinder.Stub mBinder = new IRemoteServiceBinder.Stub() {

        @Override
        public long getCurrentTime() throws RemoteException {
            return (new Date()).getTime();
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
