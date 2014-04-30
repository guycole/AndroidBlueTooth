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

  public BluetoothAdapter getAdapter() {
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    if (adapter == null) {
      Log.d(LOG_TAG, "null bluetooth adapter");
    }

    return adapter;
  }

  public boolean setup(BluetoothAdapter bluetoothAdapter) {
    if (bluetoothAdapter == null) {
      Log.d(LOG_TAG, "null bluetooth adapter");
      return false;
    }

    if (bluetoothAdapter.isEnabled()) {
      System.out.println("ghghgghghghghghg");
      System.out.println("address:" + bluetoothAdapter.getAddress());
      System.out.println("name:" + bluetoothAdapter.getName());

      Set<BluetoothDevice> bonded = bluetoothAdapter.getBondedDevices();
      System.out.println("bonded:" + bonded.size());

    }

    return(false);


  }

  public void scan() {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter == null) {
      System.out.println("null bt adapter");
    }

    if (bluetoothAdapter.isEnabled()) {
      System.out.println("bluetooth enabled");
      bluetoothAdapter.startDiscovery();
    } else {
      System.out.println("bluetooth disabled");
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */