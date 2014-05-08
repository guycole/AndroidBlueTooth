package com.digiburo.example.btdemo.app.chat;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.digiburo.example.btdemo.app.Constant;
import com.digiburo.example.btdemo.app.Personality;

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

  synchronized void connect(BluetoothDevice device, boolean secure) {
    Log.d(LOG_TAG, "connect to: " + device);

    // Cancel any thread attempting to make a connection
    if (Personality.getState() == Constant.STATE_CONNECTING) {
      if (Personality.connectThread != null) {
        Utility.clearConnectThread();
      }
    }

    // Cancel any thread currently running a connection
    if (Personality.connectedThread != null) {
      Utility.clearConnectedThread();
    }

    Utility.startConnectThread(device, secure);

    Personality.setState(Constant.STATE_CONNECTING);
  }

  /**
   * Start the ConnectedThread to begin managing a Bluetooth connection
   * @param socket  The BluetoothSocket on which the connection was made
   */
  synchronized void connected(BluetoothSocket socket, final String socketType) {
    Log.d(LOG_TAG, "connected socketType:" + socketType);

    Utility.clearConnectThread();
    Utility.clearConnectedThread();
    Utility.clearAcceptThread(true);
    Utility.clearAcceptThread(false);

    Utility.startConnectedThread(socket, socketType);

    Personality.setState(Constant.STATE_CONNECTED);
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */