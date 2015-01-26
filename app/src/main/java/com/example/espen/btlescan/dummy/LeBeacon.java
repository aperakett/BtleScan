package com.example.espen.btlescan.dummy;

import android.bluetooth.BluetoothDevice;

/**
 * Classholder for BT devices
 */
public class LeBeacon {
    private BluetoothDevice btDevice;
    private int             rssi;

    public BluetoothDevice getBtDevice () {
        return btDevice;
    }

    public int getRssi () {
        return rssi;
    }
}
