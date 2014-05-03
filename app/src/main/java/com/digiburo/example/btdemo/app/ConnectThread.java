package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * @author gsc
 */
public class ConnectThread extends AbstractParent implements Runnable {
  private final String LOG_TAG = getClass().getName();

  private final BluetoothSocket socket;
  private final BluetoothDevice device;
  private String socketType;

  private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

  /**
   *
   * @param device
   * @param secure
   * @param uuidSecure
   * @param uuidUnsecure
   */
  public ConnectThread(BluetoothDevice device, boolean secure, UUID uuidSecure, UUID uuidUnsecure) {
    this.device = device;
    socketType = secure ? "Secure" : "Insecure";

    BluetoothSocket bluetoothSocket = null;

    try {
      if (secure) {
        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuidSecure);
      } else {
        bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuidUnsecure);
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Socket Type: " + socketType + "create() failed", e);
    }

    socket = bluetoothSocket;
  }

  public void run() {
    Log.i(LOG_TAG, "BEGIN mConnectThread SocketType:" + socketType);
    setName("ConnectThread" + socketType);

    // Always cancel discovery because it will slow down a connection
    adapter.cancelDiscovery();

    // Make a connection to the BluetoothSocket
    try {
      // This is a blocking call and will only return on a
      // successful connection or an exception
      socket.connect();
    } catch (IOException e) {
      // Close the socket
      try {
        socket.close();
      } catch (IOException e2) {
        Log.e(LOG_TAG, "unable to close() " + socketType + " socket during connection failure", e2);
      }
      connectionFailed();
      return;
    }

    // Reset the ConnectThread because we're done
    synchronized(ConnectThread.this) {
   //   mConnectThread = null;
    }

    // Start the connected thread
    connected(socket, device, socketType);
  }


  public void cancel() {
    try {
      socket.close();
    } catch (IOException e) {
      Log.e(LOG_TAG, "close() of connect " + socketType + " socket failed", e);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */
