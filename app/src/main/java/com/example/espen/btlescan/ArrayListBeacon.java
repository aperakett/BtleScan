
package com.example.espen.btlescan;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
* Extension of the ArrayList class adapted to fit the LeBeacon class
*/
public class ArrayListBeacon extends ArrayList {
//    private ArrayList<LeBeacon> list;
    private ArrayList<LeBeacon> list;

    public ArrayListBeacon() {
        list = new ArrayList<LeBeacon>();
    }

    public void clear () {
        list.clear();
    }

    public LeBeacon get (int i) {
        return ((LeBeacon) list.get(i));
    }

    public void add (LeBeacon beacon) {
        list.add(beacon);
    }

    public int size () {
        return list.size();
    }

    public ArrayListBeacon clone () {
        ArrayListBeacon new_list = new ArrayListBeacon();
        new_list.list = ((ArrayList) list.clone());

        return new_list;
    }

    // Check if beacon is contained in list
    public boolean contains (LeBeacon beacon) {

        for (int i = 0; i < this.size(); i++) {
            LeBeacon b = ((LeBeacon) this.get(i));

            // if this is true, the beacon is found in the list
            if (b.getBtDevice().getAddress().equals( beacon.getBtDevice().getAddress() )) {

                // Update the RSSI variable while here..
                b.putRssi(beacon.getRssi());
                return true;
            }
        }
        return false;
    }

//    protected ArrayListBeacon(Parcel in) {
//        if (in.readByte() == 0x01) {
//            list = new ArrayList<LeBeacon>();
//            list = in.readArrayList(LeBeacon.class.getClassLoader());
//
//        } else {
//            list = null;
//        }
//    }

//    @Override
//    public void writeToParcel (Parcel dest, int flags) {
//        if (list == null) {
//            dest.writeByte(((byte) 0x0));
//        }
//        else {
//            dest.writeByte((byte) (0x01));
//            dest.writeTypedList(list);
//        }
//    }
//
//    public int describeContents () {
//        return 0;
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<ArrayListBeacon> CREATOR = new Parcelable.Creator<ArrayListBeacon>() {
//        @Override
//        public ArrayListBeacon createFromParcel(Parcel in) {
//            return new ArrayListBeacon(in);
//        }
//
//        @Override
//        public ArrayListBeacon[] newArray(int size) {
//            return new ArrayListBeacon[size];
//        }
//    };

}
