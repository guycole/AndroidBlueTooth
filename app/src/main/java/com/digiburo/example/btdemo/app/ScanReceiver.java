package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author gsc
 */


/* 
 * Copyright 2014 Digital Burro, INC
 * Created 4/28/14 by gsc
 */

public class ScanReceiver extends BroadcastReceiver {
  private final String LOG_TAG = getClass().getName();

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d(LOG_TAG, "onReceive entry");

    String action = intent.getAction();
    // When discovery finds a device
    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
      // Get the BluetoothDevice object from the Intent
      BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
      // Add the name and address to an array adapter to show in a ListView
      //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
    }
  }
}
