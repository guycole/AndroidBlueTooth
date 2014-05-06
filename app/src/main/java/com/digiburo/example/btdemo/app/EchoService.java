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

  public EchoService() {
    Personality.blueToothAdapter = BluetoothAdapter.getDefaultAdapter();
    Personality.setState(Constant.STATE_NONE);
  }

  public synchronized void start() {
    Log.d(LOG_TAG, "start");

    System.out.println("ghgghghghghghghghghghghghghghg");
    System.out.println("ghgghghghghghghghghghghghghghg");
    System.out.println("ghgghghghghghghghghghghghghghg");
    System.out.println("ghgghghghghghghghghghghghghghg");

    Utility.clearConnectThread();
    Utility.clearConnectedThread();

    Personality.setState(Constant.STATE_LISTEN);

    Utility.startAcceptThread(true);
    Utility.startAcceptThread(false);
  }

  public synchronized void connect(BluetoothDevice device, boolean secure) {
    Log.d(LOG_TAG, "connect: " + device);

    /*
    if (Personality.state == Constant.STATE_CONNECTING) {
      Utility.clearConnectThread();
    }

    Utility.clearConnectedThread();

    Utility.startConnectThread(device, secure);

    Personality.state = Constant.STATE_CONNECTING;
    */
  }

  /*
  public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType) {
    Log.d(LOG_TAG, "connected, Socket Type:" + socketType);

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
//    connectedThread.start();

//    setState(Constant.STATE_CONNECTED);
  }
  */

  /**
   * Stop all threads
   */
  public synchronized void stop() {
    Log.d(LOG_TAG, "stop");

    Utility.clearConnectThread();
    Utility.clearConnectedThread();

    Utility.clearAcceptThread(true);
    Utility.clearAcceptThread(false);

    Personality.setState(Constant.STATE_NONE);
  }


  public void write(byte[] out) {
    ConnectedThread producer = null;
    /*
    synchronized(this) {
      if (state != Constant.STATE_CONNECTED) return;
      producer = connectedThread;
    }
    */

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