package com.example.espen.btlescan;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Storage class for the bluetooth low energy devices
 *
 */
public class LeDeviceList {//} extends BaseAdapter {
//    private ArrayList<BluetoothDevice> btleDevices;
    private ArrayListBeacon btleDevices;
    private LayoutInflater inflater;

    // Constructor
    public LeDeviceList() {
        super();

        btleDevices = new ArrayListBeacon();
    }
    public LeDeviceList(ArrayListBeacon list) {
        super();
        btleDevices = list.clone();

    }
    // Add device to list
    public void addDevice(LeBeacon beacon) {
        if (!btleDevices.contains(beacon)) {
            btleDevices.add(beacon);

            //Log.i("LE Device list", "Adding device(" + String.valueOf(btleDevices.size()) + "): " + beacon.getBtDevice().getAddress());

        }
    }

    // Clear the list (purge all devices)
    public void clear() {
        btleDevices.clear();
    }

    // Returns the number of devices in list
    public int getCount() {
        if (btleDevices != null)
            return btleDevices.size();
        else
            return 0;
    }

    public LeBeacon getItem(int i) {
        return btleDevices.get(i);
    }

//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }

    public void setList (ArrayListBeacon list) {
        btleDevices = list;
    }

    public ArrayListBeacon getList () {
//        return btleDevices.clone();
        return btleDevices;
    }

//    public View getView(int i, View view, ViewGroup viewGroup) {
//        return null;
//    }

}







