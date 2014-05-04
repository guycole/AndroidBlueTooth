package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;

/**
 * @author gsc
 */
public class Utility {

  public static void clearAcceptThread(boolean secure) {
    if (secure) {
      AcceptThread secureAcceptThread = Personality.secureAcceptThread;

      if (secureAcceptThread != null) {
        secureAcceptThread.cancel();
        Personality.secureAcceptThread = null;
      }
    } else {
      AcceptThread acceptThread = Personality.acceptThread;

      if (acceptThread != null) {
        acceptThread.cancel();
        Personality.acceptThread = null;
      }
    }
  }

  public static void clearConnectThread() {
    ConnectThread connectThread = Personality.connectThread;

    if (connectThread != null) {
      connectThread.cancel();
      Personality.connectThread = null;
    }
  }

  public static void clearConnectedThread() {
    ConnectedThread connectedThread = Personality.connectedThread;

    if (connectedThread != null) {
      connectedThread.cancel();
      Personality.connectedThread = null;
    }
  }

  public static void startAcceptThread(boolean secure) {
    AcceptThread acceptThread;
    AcceptThread temp;
    String name;

    if (secure) {
      acceptThread = Personality.secureAcceptThread;
      temp = new AcceptThread(Personality.blueToothAdapter, true, Constant.NAME_SECURE, Constant.MY_UUID_SECURE);
      name = "AcceptThread:secure";
    } else {
      acceptThread = Personality.acceptThread;
      temp = new AcceptThread(Personality.blueToothAdapter, false, Constant.NAME_INSECURE, Constant.MY_UUID_INSECURE);
      name = "AcceptThread:insecure";
    }

    if (acceptThread != null) {
      return;
    }

    Thread thread = new Thread(temp);
    thread.setName(name);
    thread.start();

    if (secure) {
      Personality.secureAcceptThread = acceptThread;
    } else {
      Personality.acceptThread = acceptThread;
    }
  }

  public static void startConnectThread(BluetoothDevice device, boolean secure) {
    ConnectThread connectThread = new ConnectThread(device, secure, Constant.MY_UUID_SECURE, Constant.MY_UUID_INSECURE);
    Thread thread = new Thread(connectThread);
    thread.setName("ConnectThread");
    thread.start();

    Personality.connectThread = connectThread;
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */