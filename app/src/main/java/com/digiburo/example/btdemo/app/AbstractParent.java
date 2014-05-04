package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * @author gsc
 */
public abstract class AbstractParent {
  private final String LOG_TAG = getClass().getName();


  /**
   * Indicate that the connection attempt failed and notify the UI Activity.
   */
  public void connectionFailed() {
    /*
    // Send a failure message back to the Activity
    Message msg = mHandler.obtainMessage(BluetoothChat.MESSAGE_TOAST);
    Bundle bundle = new Bundle();
    bundle.putString(BluetoothChat.TOAST, "Unable to connect device");
    msg.setData(bundle);
    mHandler.sendMessage(msg);

    // Start the service over to restart listening mode
    BluetoothChatService.this.start();
    */
  }

  public void connectionLost() {
    /*
    // Send a failure message back to the Activity
    Message msg = mHandler.obtainMessage(BluetoothChat.MESSAGE_TOAST);
    Bundle bundle = new Bundle();
    bundle.putString(BluetoothChat.TOAST, "Device connection was lost");
    msg.setData(bundle);
    mHandler.sendMessage(msg);

    // Start the service over to restart listening mode
    BluetoothChatService.this.start();
    */
  }


  /**
   * Start the ConnectedThread to begin managing a Bluetooth connection
   * @param socket  The BluetoothSocket on which the connection was made
   * @param device  The BluetoothDevice that has been connected
   */
  synchronized void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType) {
    Log.d(LOG_TAG, "connected socketType:" + socketType);

    Utility.clearConnectThread();
    Utility.clearConnectedThread();
    Utility.clearAcceptThread(true);
    Utility.clearAcceptThread(false);

    Utility.startConnectThread(device, false);

    Personality.state = Constant.STATE_CONNECTED;
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */