package com.example.espen.btlescan;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/**
 * Storage class for the bluetooth low energy devices
 *
 */
public class LeDeviceList extends BaseAdapter {
    private ArrayList<BluetoothDevice> btleDevices;
    private LayoutInflater inflater;

    // Constructor
    public LeDeviceList() {
        super();
        btleDevices = new ArrayList<BluetoothDevice>();
    }

    // Add device to list
    public void addDevice(BluetoothDevice device) {
        if (!btleDevices.contains(device)) {
            btleDevices.add(device);

            Log.i("LE Device list", "Adding device(" + String.valueOf(btleDevices.size()) + "): " + device.getAddress());

        }
    }

    // Clear the list (purge all devices)
    public void clear() {
        btleDevices.clear();
    }

    // Returns the number of devices in list
    public int getCount() {
        return btleDevices.size();
    }

    public BluetoothDevice getItem(int i) {
        return btleDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void setList (ArrayList<BluetoothDevice> list) {
       this.btleDevices = list;
    }

    public ArrayList getList () {
        return new ArrayList(btleDevices);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}



