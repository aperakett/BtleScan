package com.example.espen.btlescan;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.espen.btlescan.dummy.DummyContent;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class LeDeviceListFragment extends Fragment implements AbsListView.OnItemClickListener {




    private OnFragmentInteractionListener mListener;
    private LeScannerService mService;
    private View mView;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private LeDeviceListAdapter leDeviceListAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeDeviceListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("FRAGMENT", "onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FRAGMENT", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_ledevicelist, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i("FRAGMENT", "onAttach()");
        super.onAttach(activity);

        if (mService == null) {
            Bundle bundle = getArguments();
            if (bundle == null) {
                Log.i("DOHDOHDOH", "bundle == null.....");
                bundle = activity.getIntent().getExtras();
                if (bundle == null)
                    Log.i("DOHDOHDOH", "bundle == null.....again...");

            }
            IBinder iBinder = bundle.getBinder("binder");
            if (iBinder == null)
                Log.i("DOHDOHDOH", "binder == null.....");
            LeScannerService.LocalBinder binder = (LeScannerService.LocalBinder) iBinder;
            mService = binder.getService();
        }
        ArrayListBeacon list = mService.getList();
//        leDeviceListAdapter = new LeDeviceListAdapter();
//        leDeviceListAdapter.setList(list);

    }

    @Override
    public void onStop () {
        super.onStop();
        Log.i("FRAGMENT", "onStop()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("FRAGMENT", "onDetatch()");
//        mListener = null;
    }

//    public void onPause () {
//        super.onPause();
//        Log.i("FRAGMENT", "onPause()");
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        Toast.makeText(parent.getContext(), leDeviceListAdapter.getItem(position).getBtDevice().getName() + " - " + leDeviceListAdapter.getItem(position).getBtDevice().getAddress(), Toast.LENGTH_SHORT).show();

        registerForContextMenu(view);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);

        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    class LeDeviceListAdapter extends com.example.espen.btlescan.LeDeviceList {
//        private LayoutInflater inflater;

        // Constructor
        public LeDeviceListAdapter() {
            super();
//            inflater = getActivity().getLayoutInflater();
        }

//        //        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            ViewHolder viewHolder;
//            if (view == null) {
//                view = inflater.inflate(R.layout.fragment_ledevicelist_list, null);
//                mView = view;
//                viewHolder = new ViewHolder();
//                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.le_addr);
//                viewHolder.deviceSignal = (TextView) view.findViewById(R.id.le_rssi);
//                viewHolder.deviceName = (TextView) view.findViewById(R.id.le_name);
//                view.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) view.getTag();
//            }
//
//            LeBeacon beacon = this.getItem(i);
//            BluetoothDevice device = beacon.getBtDevice();
//            final String deviceName = device.getName();
//
//            if (deviceName != null && deviceName.length() > 0) {
//                viewHolder.deviceName.setText(deviceName);
//            } else {
//                viewHolder.deviceName.setText(R.string.unknown_device);
//            }
//            viewHolder.deviceAddress.setText(device.getAddress());
//            viewHolder.deviceSignal.setText(String.valueOf(beacon.getRssi()));
//            return view;
//
//        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceSignal;
        TextView deviceAddress;
    }

}
