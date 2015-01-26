package com.example.espen.btlescan;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {
    public LeDeviceList btleDeviceList;
    public LeScannerService mService;
    public boolean mBound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LeScannerService.LocalBinder binder = (LeScannerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_le_device_list);

        // Create new instance of Bluetooth Device list class
        btleDeviceList = new LeDeviceList();

        // Bind service, binds the service so it's reachable from different classes
        Intent intent = new Intent(this, LeScannerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        startService(intent);

        // start the scan schedule
        schedulePeriodicalScan();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void showBtleDevices (View view) {

//        if (mBound) {
            int num = mService.getNumberOfDevices();
            Toast.makeText(this, "Devices discovered: " + String.valueOf(num), Toast.LENGTH_SHORT).show();
//        }
    }

    public void updateDeviceList (View view) {
//        // This use the activity
//        Intent startBtleScanIntent = new Intent(this, BtleDeviceScanActivity.class);
//        startActivity(startBtleScanIntent);

//        // This uses the service
//        Intent intent = new Intent(this, LeDeviceListActivity.class);
//        intent.putParcelableArrayListExtra("List", mService.getList());
//        startActivity(intent);

        //TODO FIXME, HIGLY EXPERIMAENTAL
        Fragment frag = new LeDeviceListFragment();
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("List", mService.getList());
        frag.setArguments(bundle);
        tran.replace(R.id.fragment, frag);
        tran.commit();
    }

    // Schedules periodical BTLE scan
    public void schedulePeriodicalScan () {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Runnable scan = new Runnable() {
            @Override
            public void run() {
                mService.scan();
            }
        };

        final ScheduledFuture scannerHandle = scheduler.scheduleAtFixedRate(scan, 5, 10, TimeUnit.SECONDS);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                scannerHandle.cancel(false);
            }
        }, 60 * 60, TimeUnit.SECONDS);
    }

}
