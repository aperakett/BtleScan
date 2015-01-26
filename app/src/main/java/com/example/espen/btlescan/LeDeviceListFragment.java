package com.example.espen.btlescan;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.example.espen.btlescan.dummy.DummyContent;

import java.util.ArrayList;

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
    private LeDeviceListAdapter leDeviceListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static LeDeviceListFragment newInstance(String param1, String param2) {
        LeDeviceListFragment fragment = new LeDeviceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeDeviceListFragment() {
        Log.i("FRAGMENT", "Constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("FRAGMENT", "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FRAGMENT", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_ledevicelist, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        ((AdapterView<ListAdapter>) mListView).setAdapter(leDeviceListAdapter);


        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i("FRAGMENT", "onAttach");

        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }

        leDeviceListAdapter = new LeDeviceListAdapter();
        Bundle bundle = this.getArguments();
        ArrayList list = ((ArrayList) bundle.getParcelableArrayList("List"));
        //leDeviceListAdapter.setList(list);
//        Log.i("FRAGMENT", "List size: " + String.valueOf(list.size()));
//        Log.i("FRAGMENT", "List size: " + String.valueOf(leDeviceListAdapter.getCount()));

        leDeviceListAdapter.btleDevices = list;
        leDeviceListAdapter.setList(list);
        Log.i("FRAGMENT", "List size: " + String.valueOf(list.size()));
        Log.i("FRAGMENT", "List size: " + String.valueOf(leDeviceListAdapter.getCount()));
        //setListAdapter(leDeviceListAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        private ArrayList<BluetoothDevice> btleDevices;
        private LayoutInflater inflater;

        // Constructor
        public LeDeviceListAdapter() {
            super();
            btleDevices = new ArrayList<BluetoothDevice>();
            inflater = getActivity().getLayoutInflater();

        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
//            Log.i("LE_DEVICE_LIST_ACTIVITY", "getView / Items in list: " + String.valueOf(btleDevices.size()));
            if (view == null) {
                Log.i("LE_DEVICE_LIST_ACTIVITY", "view == null");
                view = inflater.inflate(R.layout.fragment_ledevicelist_list, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.le_addr);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.le_name);
                view.setTag(viewHolder);
            } else {
                Log.i("LE_DEVICE_LIST_ACTIVITY", "view != null");
                viewHolder = (ViewHolder) view.getTag();
            }
            Log.i("LE_DEVICE_LIST_ACTIVITY", "list size = " + String.valueOf(btleDevices.size()) + "/" + String.valueOf(i));

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
