package com.digiburo.example.btdemo.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.digiburo.example.btdemo.R;
import com.digiburo.example.btdemo.app.time.TimeClient;

import java.util.ArrayList;
import java.util.List;

/**
 * List of discovered bluetooth devices
 */
public class DiscoveryFragment extends ListFragment {
  public static final String FRAGMENT_TAG = "TAG_DISCOVERY";

  private DiscoveryArrayAdapter discoveryArrayAdapter;

  private List<BluetoothDevice> discoveryList = new ArrayList<BluetoothDevice>();

  private FragmentListener fragmentListener;

  private final String LOG_TAG = getClass().getName();

  /**
   * catch discovery events
   */
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Log.d(LOG_TAG, "onReceive:" + action);

      if (BluetoothDevice.ACTION_FOUND.equals(action)) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        discoveryArrayAdapter.add(device);
      } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
        Toast.makeText(getActivity(), R.string.toast_stop_discovery, Toast.LENGTH_LONG).show();
      }
    }
  };

  /**
   * mandatory empty ctor
   */
  public DiscoveryFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    fragmentListener = (FragmentListener) activity;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_discovery, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Button discoveryButton = (Button) getActivity().findViewById(R.id.buttonDiscovery);
    discoveryButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.isEnabled()) {
          discoveryArrayAdapter.clear();
          adapter.startDiscovery();
          Toast.makeText(getActivity(), R.string.toast_start_discovery, Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(getActivity(), R.string.toast_must_enable_bluetooth, Toast.LENGTH_LONG).show();
        }
      }
    });

    discoveryArrayAdapter = new DiscoveryArrayAdapter(getActivity(), discoveryList);
    setListAdapter(discoveryArrayAdapter);
    registerForContextMenu(getListView());
  }

  @Override
  public void onResume() {
    super.onResume();

    IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    getActivity().registerReceiver(broadcastReceiver, foundFilter);

    IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    getActivity().registerReceiver(broadcastReceiver, finishedFilter);
  }

  @Override
  public void onPause() {
    super.onPause();
    getActivity().unregisterReceiver(broadcastReceiver);
  }

  /**
   * row selection
   */
  public void onListItemClick(ListView listView, View view, int position, long id) {
    Log.d(LOG_TAG, "click:" + position + ":" + id);

    BluetoothDevice bluetoothDevice = discoveryArrayAdapter.getItem(position);
  }

  /**
   * menu for long press
   */
  @Override
  public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, view, menuInfo);
    MenuInflater inflater = getActivity().getMenuInflater();
    inflater.inflate(R.menu.discovery, menu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    Log.d(LOG_TAG, "on context item select:" + item + ":" + info.id + ":" + discoveryArrayAdapter.getItem(info.position));
    BluetoothDevice device = discoveryArrayAdapter.getItem(info.position);

    BluetoothClass bluetoothClass = device.getBluetoothClass();
    bluetoothClass.getDeviceClass();
    bluetoothClass.getMajorDeviceClass();

    BlueToothHelper blueToothHelper = new BlueToothHelper();
    Log.d(LOG_TAG, "device:" + blueToothHelper.deviceCode(bluetoothClass.getDeviceClass()));
    Log.d(LOG_TAG, "major device:" + blueToothHelper.majorDeviceCode(bluetoothClass.getMajorDeviceClass()));

    switch(item.getItemId()) {
      case R.id.actionChat:
        fragmentListener.startChatClient(device);
        return(true);
      case R.id.actionTime:
        TimeClient timeClient = new TimeClient();
        String timeStamp = timeClient.getRemoteTime(device);
        Toast.makeText(getActivity(), timeStamp, Toast.LENGTH_LONG).show();
        return(true);
    }

    Log.e(LOG_TAG, "unknown menu option");

    return super.onContextItemSelected(item);
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */