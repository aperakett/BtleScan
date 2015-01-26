package com.example.espen.btlescan;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class BtleDeviceScanActivity extends ListActivity {
    private Handler handler;
    private BluetoothAdapter btAdapter;
    private LeDeviceListAdapter btleDeviceListAdapter;
    private boolean scanning;
    private int sleepPeriod;

    private BluetoothAdapter.LeScanCallback btleScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btleDeviceListAdapter.addDevice(device);
                            btleDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle(R.string.btle_device_scan);
        //setContentView(R.layout.actionbar_indeterminate_progress);

//        onCreateOptionsMenu(null);

        //Check if BTLE is supported by device
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.btle_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // create the handler used for timer aborted scanning...
        handler = new Handler();

        // Set the default sleep interval in ms.
        sleepPeriod = 8000;

        // Set up the bluetoooth adapter through manager
        BluetoothManager btMan = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btMan.getAdapter();

        // check if BT is supported
        if (btAdapter == null) {
            Toast.makeText(this, R.string.bt_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // BT is supported, but disabled, try enabling it
        else if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        btleDeviceListAdapter = new LeDeviceListAdapter();
        setListAdapter(btleDeviceListAdapter);

        // Start the scan by initiating the scanning variable.
        scanning = false;
        scanBtleDevices(scanning);
     }

    public void scanBtleDevices (boolean enabled) {
        if (!enabled) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    btAdapter.stopLeScan(btleScanCallback);
                }
            }, sleepPeriod);

            scanning = true;
            btAdapter.startLeScan(btleScanCallback);
        }
        else {
            scanning = false;
            btAdapter.stopLeScan(btleScanCallback);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_btle_device_scan, menu);
        if (!scanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        }
        else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_indeterminate_progress);
        }
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

    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> btleDevices;
        private LayoutInflater inflater;

        // initiator
        public LeDeviceListAdapter() {
            super();
            btleDevices = new ArrayList<BluetoothDevice>();
            inflater = BtleDeviceScanActivity.this.getLayoutInflater();
        }

        // adds a BTLE device to list
        public void addDevice(BluetoothDevice device) {
            if (!btleDevices.contains(device)) {
                btleDevices.add(device);
            }
        }

        // gets a device from the list
        public BluetoothDevice getDevice(int position) {
            return btleDevices.get(position);
        }

        // clears the list
        public void clear() {
            btleDevices.clear();
        }

        // gets the list count (numbers of devices in list)
        public int getCount() {
            return btleDevices.size();
        }

        // Gets the object number i from the list
        public Object getItem(int i) {
            return btleDevices.get(i);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (view == null) {
                view = inflater.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.beacon_addr);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.beacon_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = btleDevices.get(i);
            final String deviceName = device.getName();

            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceName.setText(deviceName);
            }
            else {
                viewHolder.deviceName.setText(R.string.unknown_device);
            }
            viewHolder.deviceAddress.setText(device.getAddress());
            return view;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }

    static class HeadLineHolder {
        TextView headLine;
    }
}
