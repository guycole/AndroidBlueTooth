package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.util.UUID;

/**
 * Server side echo service
 * @author gsc
 */
public class EchoService {
  private final String LOG_TAG = getClass().getName();

  // Name for the SDP record when creating server socket
  private static final String NAME_SECURE = "SecureEchoService";
  private static final String NAME_UNSECURE = "UnsecureEchoService";

  // Unique UUID for this application
  private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
  private static final UUID MY_UUID_UNSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

  //
  private final BluetoothAdapter adapter;
  private AcceptThread secureAcceptThread;
  private AcceptThread unsecureAcceptThread;
  private ConnectThread connectThread;
  private ConnectedThread connectedThread;

  public synchronized void start() {
    Log.d(LOG_TAG, "start");

    // Cancel any thread attempting to make a connection
    if (connectThread != null) {
      connectThread.cancel();
      connectThread = null;
    }

    // Cancel any thread currently running a connection
    if (connectedThread != null) {
      connectedThread.cancel();
      connectedThread = null;
    }

    setState(Constant.STATE_LISTEN);

    if (secureAcceptThread == null) {
      secureAcceptThread = new AcceptThread(true, NAME_SECURE, MY_UUID_SECURE);
      secureAcceptThread.start();
    }

    if (unsecureAcceptThread == null) {
      unsecureAcceptThread = new AcceptThread(false, NAME_UNSECURE, MY_UUID_UNSECURE);
      unsecureAcceptThread.start();
    }
  }

  public synchronized void connect(BluetoothDevice device, boolean secure) {
    Log.d(LOG_TAG, "connect to: " + device);

    if (state == Constant.STATE_CONNECTING) {
      if (connectThread != null) {
        connectThread.cancel();
        connectThread = null;
      }
    }

    if (connectedThread != null) {
      connectedThread.cancel();
      connectedThread = null;
    }

    connectThread = new ConnectThread(device, secure, MY_UUID_SECURE, MY_UUID_UNSECURE);
    connectThread.start();

    setState(Constant.STATE_CONNECTING);
  }

  public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType) {
    Log.d(TAG, "connected, Socket Type:" + socketType);

    // Cancel the thread that completed the connection
    if (connectThread != null) {
      connectThread.cancel();
      connectThread = null;
    }

    // Cancel any thread currently running a connection
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

  /**
   * Stop all threads
   */
  public synchronized void stop() {
    Log.d(LOG_TAG, "stop");

    if (connectThread != null) {
      connectThread.cancel();
      connectThread = null;
    }

    if (connectedThread != null) {
      connectedThread.cancel();
      connectedThread = null;
    }

    if (secureAcceptThread != null) {
      secureAcceptThread.cancel();
      secureAcceptThread = null;
    }

    if (unsecureAcceptThread != null) {
      unsecureAcceptThread.cancel();
      unsecureAcceptThread = null;
    }

    setState(Constant.STATE_NONE);
  }

  public void write(byte[] out) {
    ConnectedThread producer;

    synchronized(this) {
      if (state != Constant.STATE_CONNECTED) return;
      producer = connectedThread;
    }

    producer.write(out);
  }

  private void connectionFailed() {
    Log.e(LOG_TAG, "connection failed");

    // Start the service over to restart listening mode
    EchoService.this.start();
  }

  private void connectionLost() {
    Log.e(LOG_TAG, "connection lost");

    // Start the service over to restart listening mode
    EchoService.this.start();
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */