package com.example.espen.btlescan;

import android.bluetooth.BluetoothDevice;

/**
 * Classholder for BT devices
 */
public class LeBeacon {
    private BluetoothDevice btDevice;
    private int             rssi;

    public LeBeacon (BluetoothDevice device, int signal) {
        this.btDevice = device;
        this.rssi = signal;
    }

    public BluetoothDevice getBtDevice () {
        return btDevice;
    }

    public int getRssi () {
        return rssi;
    }

    public void putRssi (int strength) {
        this.rssi = strength;
    }

    public void putDevice (BluetoothDevice device) {
        this.btDevice = device;
    }
}
