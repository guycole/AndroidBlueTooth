package com.digiburo.example.btdemo.app.chat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.digiburo.example.btdemo.app.Constant;

import java.io.IOException;
import java.util.UUID;

/**
 * @author gsc
 */
public class ConnectThread extends AbstractParent implements Runnable {
  private final String LOG_TAG = getClass().getName();

  private final BluetoothSocket socket;
  private final BluetoothDevice device;
  private final String socketType;

  private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

  /**
   *
   * @param device
   * @param secure
   * @param uuidSecure
   * @param uuidInsecure
   */
  public ConnectThread(BluetoothDevice device, boolean secure) {
    this.device = device;
    socketType = secure ? "Secure":"Insecure";

    BluetoothSocket blueToothSocket = null;

    try {
      if (secure) {
        blueToothSocket = device.createRfcommSocketToServiceRecord(Constant.UUID_SECURE);
      } else {
        blueToothSocket = device.createInsecureRfcommSocketToServiceRecord(Constant.UUID_INSECURE);
      }
    } catch (IOException exception) {
      Log.e(LOG_TAG, "connect: " + socketType + "create failed", exception);
    }

    socket = blueToothSocket;
  }

  public void run() {
    Log.d(LOG_TAG, "connected run()");
    Log.d(LOG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    Log.d(LOG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    Log.d(LOG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    Log.d(LOG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

    // cancel discovery because it will slow down a connection
    adapter.cancelDiscovery();

    try {
      socket.connect();
    } catch (IOException exception) {
      Log.e(LOG_TAG, "connect failure", exception);

      try {
        socket.close();
      } catch (IOException empty) {
        //empty
      }

      connectionFailed();
      return;
    }

    // Reset the ConnectThread because we're done
    synchronized(ConnectThread.this) {
   //   mConnectThread = null;
    }

    // Start the connected thread
    //connected(socket, device, socketType);
    connected(socket, socketType);

    Log.d(LOG_TAG, "run() exit");
  }

  public void cancel() {
    try {
      socket.close();
    } catch (IOException exception) {
      Log.e(LOG_TAG, "close socket failure:" + socketType, exception);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */
