package com.digiburo.example.btdemo.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.digiburo.example.btdemo.R;

/**
 * Shameless self prom
 * Implemented as a WebView w/local HTML content
 */
public class DiscoveryFragment extends Fragment {

  public static final String FRAGMENT_TAG = "TAG_SCAN";

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
        System.out.println(device.getName() + ":" + device.getAddress());
      }
    }
  };

  /*
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d(LOG_TAG, "onReceive entry");

      String action = intent.getAction();
      // When discovery finds a device
      if (BluetoothDevice.ACTION_FOUND.equals(action)) {
        // Get the BluetoothDevice object from the Intent
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        // Add the name and address to an array adapter to show in a ListView
//        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        System.out.println("device:" + device);
      }
    }
  };
  */


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

      /**
       * invoke scan function
       * @param view
       */
      @Override
      public void onClick(View view) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.isEnabled()) {
          adapter.startDiscovery();
          Toast.makeText(getActivity(), R.string.toast_start_discovery, Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(getActivity(), R.string.toast_must_enable_bluetooth, Toast.LENGTH_LONG).show();
        }
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    getActivity().registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
  }

  @Override
  public void onPause() {
    super.onPause();
    getActivity().unregisterReceiver(broadcastReceiver);
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */