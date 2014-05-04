package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import java.util.Set;

/**
 * @author gsc
 */

public class BlueToothHelper {

  private final String LOG_TAG = getClass().getName();

  /**
   *
   * @return
   */
  public BluetoothAdapter getAdapter() {
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    if (adapter == null) {
      Log.d(LOG_TAG, "null bluetooth adapter");
    }

    return adapter;
  }

  /**
   *
   * @param bluetoothAdapter
   * @return true success
   */
  public boolean setup(BluetoothAdapter bluetoothAdapter) {
    if (bluetoothAdapter == null) {
      Log.d(LOG_TAG, "null bluetooth adapter");
      return false;
    }

    if (bluetoothAdapter.isEnabled()) {
      Log.d(LOG_TAG, "address:" + bluetoothAdapter.getAddress());
      Log.d(LOG_TAG, "name:" + bluetoothAdapter.getName());

      Set<BluetoothDevice> bonded = bluetoothAdapter.getBondedDevices();
      System.out.println("bonded:" + bonded.size());
    }

    return(false);
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */