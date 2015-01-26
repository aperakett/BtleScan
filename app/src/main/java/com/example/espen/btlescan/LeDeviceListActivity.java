package com.example.espen.btlescan;

import android.app.ListActivity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class LeDeviceListActivity extends ListActivity {
//    public class LeDeviceListActivity extends ListActivity {
    private LeDeviceListAdapter leDeviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList list = ((ArrayList) getIntent().getParcelableArrayListExtra("List"));

        leDeviceListAdapter = new LeDeviceListAdapter();
        //leDeviceListAdapter.setList(list);
        this.leDeviceListAdapter.btleDevices = list;

        Log.i("CREATING LIST", String.valueOf( leDeviceListAdapter.getCount() ) + " / " + String.valueOf(list.size()));

        setListAdapter(leDeviceListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_le_device_list, menu);
        Log.i("CREATING LE DEVICE ACTIVITY", "onCreateOptionsMenu");

//        ArrayList list = ((ArrayList) getIntent().getExtras().get("List"));
//        btleDeviceList.setList(list);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.i("CREATING LE DEVICE ACTIVITY", "onOptionsItemSelected");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LeDeviceListAdapter extends com.example.espen.btlescan.LeDeviceList {
        private ArrayList<BluetoothDevice> btleDevices;
        private LayoutInflater inflater;

        // Constructor
        public LeDeviceListAdapter() {
            super();
            //btleDevices = new ArrayList<BluetoothDevice>();
            inflater = LeDeviceListActivity.this.getLayoutInflater();

        }

//        @Override
//        public void setList (ArrayList<BluetoothDevice> list) {
//            this.btleDevices = list;
//        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            Log.i("LE_DEVICE_LIST_ACTIVITY", "getView / Items in list: " + String.valueOf(btleDevices.size()));
            if (view == null) {
                Log.i("LE_DEVICE_LIST_ACTIVITY", "view == null");
                view = inflater.inflate(R.layout.activity_le_device_list, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.le_addr);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.le_name);
                view.setTag(viewHolder);
            } else {
                Log.i("LE_DEVICE_LIST_ACTIVITY", "view != null");
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

}
