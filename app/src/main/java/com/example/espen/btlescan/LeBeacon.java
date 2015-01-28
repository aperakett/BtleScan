package com.example.espen.btlescan;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classholder for BT devices
 */
public class LeBeacon implements Parcelable {
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

    protected LeBeacon(Parcel in) {
        btDevice = (BluetoothDevice) in.readValue(BluetoothDevice.class.getClassLoader());
        rssi = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(btDevice);
        dest.writeInt(rssi);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LeBeacon> CREATOR = new Parcelable.Creator<LeBeacon>() {
        @Override
        public LeBeacon createFromParcel(Parcel in) {
            return new LeBeacon(in);
        }

        @Override
        public LeBeacon[] newArray(int size) {
            return new LeBeacon[size];
        }
    };
}
//package com.example.espen.btlescan;
//
//import android.bluetooth.BluetoothDevice;
//
///**
// * Classholder for BT devices
// */
//public class LeBeacon {
//    private BluetoothDevice btDevice;
//    private int             rssi;
//
//    public LeBeacon (BluetoothDevice device, int signal) {
//        this.btDevice = device;
//        this.rssi = signal;
//    }
//
//    public BluetoothDevice getBtDevice () {
//        return btDevice;
//    }
//
//    public int getRssi () {
//        return rssi;
//    }
//
//    public void putRssi (int strength) {
//        this.rssi = strength;
//    }
//
//    public void putDevice (BluetoothDevice device) {
//        this.btDevice = device;
//    }
//}
