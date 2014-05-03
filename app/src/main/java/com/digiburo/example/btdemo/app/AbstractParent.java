package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * @author gsc
 */
public class AbstractParent {
  private final String LOG_TAG = getClass().getName();

  AcceptThread secureAcceptThread;
  AcceptThread unsecureAcceptThread;

  ConnectThread connectThread;
  ConnectedThread connectedThread;

  int state;

  public synchronized int getState() {
    return state;
  }

  private synchronized void setState(int state) {
    Log.d(LOG_TAG, "setState() " + this.state + " -> " + state);
    this.state = state;
  }

  /**
   * Start the ConnectedThread to begin managing a Bluetooth connection
   * @param socket  The BluetoothSocket on which the connection was made
   * @param device  The BluetoothDevice that has been connected
   */
  public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType) {
    Log.d(LOG_TAG, "connected, Socket Type:" + socketType);

    // Cancel the thread that completed the connection
    if (connectThread != null) {
      connectThread.cancel();
      connectThread = null;
    }

    if (connectedThread != null) {
      connectedThread.cancel();
      connectedThread = null;
    }

    // Cancel the accept thread because we only want to connect to one device
    if (secureAcceptThread != null) {
      secureAcceptThread.cancel();
      secureAcceptThread = null;
    }

    if (unsecureAcceptThread != null) {
      unsecureAcceptThread.cancel();
      unsecureAcceptThread = null;
    }

    // Start the thread to manage the connection and perform transmissions
    connectedThread = new ConnectedThread(socket, socketType);
    connectedThread.start();

    setState(Constant.STATE_CONNECTED);
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */