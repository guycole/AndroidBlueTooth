package com.digiburo.example.btdemo.app.chat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.util.Log;

import com.digiburo.example.btdemo.app.Constant;
import com.digiburo.example.btdemo.app.Personality;

/**
 * Server side chat service.
 * @author gsc
 */
public class ChatService {
  private final String LOG_TAG = getClass().getName();

  private BluetoothServerSocket serverSocket = null;

  private Thread thread;
  private boolean runFlag;

  public ChatService() {
    Personality.blueToothAdapter = BluetoothAdapter.getDefaultAdapter();
  }

  public synchronized void start() {
    Log.d(LOG_TAG, "start start start");
    runFlag = true;

    Utility.clearConnectThread();
    Utility.clearConnectedThread();

    Personality.setState(Constant.STATE_LISTEN);

    Utility.startAcceptThread(true);
//    Utility.startAcceptThread(false);
  }

  public synchronized void stop() {
    Log.d(LOG_TAG, "stop stop stop");
    runFlag = false;

  }

  public synchronized void connect(BluetoothDevice device, boolean secure) {
    Log.d(LOG_TAG, "connect:" + device);

    Utility.clearConnectThread();
    Utility.clearConnectedThread();

    Utility.startConnectThread(device, secure);

    Personality.setState(Constant.STATE_CONNECTING);
  }


  public void write(String message) {
    System.out.println("write write write:" + message);

    ConnectedThread connectedThread = Personality.connectedThread;
    if (connectedThread == null) {
      Log.e(LOG_TAG, "null connect thread");
      return;
    }

    connectedThread.write(message.getBytes());

  // connectThread.write(message);
  }

  /*
  public void write(byte[] out) {
    // Create temporary object
    ConnectedThread r;
    // Synchronize a copy of the ConnectedThread
    synchronized (this) {
      if (mState != STATE_CONNECTED) return;
      r = mConnectedThread;
    }
    // Perform the write unsynchronized
    r.write(out);
  }
  */

}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on May 6, 2014 by gsc
 */