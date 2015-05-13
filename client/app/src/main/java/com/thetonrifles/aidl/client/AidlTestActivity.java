package com.thetonrifles.aidl.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thetonrifles.aidl.server.IRemoteServiceBinder;

public class AidlTestActivity extends ActionBarActivity {

    private static final String LOG_TAG = "AidlTest";

    // ui elements
    private Button mButtonGetTime;

    // service connection
    private IRemoteServiceBinder mRemoteServiceBinder;
    private ServiceConnection mRemoteServiceConnection;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);

        mBound = false;

        mButtonGetTime = (Button) findViewById(R.id.btn_get_current_time);
        mButtonGetTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (mRemoteServiceBinder != null) {
                        String msg = "" + mRemoteServiceBinder.getCurrentTime();
                        Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (RemoteException ex) {
                    Log.e(LOG_TAG, "remote exception");
                }
            }

        });

        mRemoteServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LOG_TAG, "remote service connected");
                mBound = true;
                mRemoteServiceBinder = IRemoteServiceBinder.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "remote service disconnected");
                mBound = false;
            }

        };
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "on start");
        if (!mBound) {
            Log.d(LOG_TAG, "binding service");
            Intent intent = new Intent();
            intent.setClassName("com.thetonrifles.aidl.server", "com.thetonrifles.aidl.server.RemoteService");
            bindService(intent, mRemoteServiceConnection, Service.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "on stop");
        if (mBound) {
            unbindService(mRemoteServiceConnection);
        }
    };

}
