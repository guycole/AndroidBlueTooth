package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * @author gsc
 */
public class Utility {

  public static void clearAcceptThread(boolean secure) {
    Log.d("utility", "clearAcceptThread:" + secure);

    if (secure) {
      AcceptThread secureAcceptThread = Personality.secureAcceptThread;

      if (secureAcceptThread != null) {
        secureAcceptThread.cancel();
        Personality.secureAcceptThread = null;
      }
    } else {
      AcceptThread acceptThread = Personality.insecureAcceptThread;

      if (acceptThread != null) {
        acceptThread.cancel();
        Personality.insecureAcceptThread = null;
      }
    }
  }

  public static void clearConnectThread() {
    Log.d("utility", "clearConnectThread");

    ConnectThread connectThread = Personality.connectThread;
    if (connectThread != null) {
      connectThread.cancel();
      Personality.connectThread = null;
    }
  }

  public static void clearConnectedThread() {
    Log.d("utility", "clearConnectedThread");

    ConnectedThread connectedThread = Personality.connectedThread;
    if (connectedThread != null) {
      connectedThread.cancel();
      Personality.connectedThread = null;
    }
  }

  public static void startAcceptThread(boolean secure) {
    Log.d("utility", "startAcceptThread:" + secure);

    AcceptThread acceptThread;

    if (secure) {
      acceptThread = Personality.secureAcceptThread;
      if (acceptThread != null) {
        return;
      }

      acceptThread = new AcceptThread(Personality.blueToothAdapter, true, Constant.NAME_SECURE, Constant.MY_UUID_SECURE);

      Thread thread = new Thread(acceptThread);
      thread.setName("AcceptThread:secure");
      thread.start();

      Personality.secureAcceptThread = acceptThread;
    } else {
      acceptThread = Personality.insecureAcceptThread;
      if (acceptThread != null) {
        return;
      }

      acceptThread = new AcceptThread(Personality.blueToothAdapter, false, Constant.NAME_INSECURE, Constant.MY_UUID_INSECURE);

      Thread thread = new Thread(acceptThread);
      thread.setName("AcceptThread:insecure");
      thread.start();

      Personality.insecureAcceptThread = acceptThread;
    }
  }

  public static void startConnectThread(BluetoothDevice device, boolean secure) {
    Log.d("utility", "startConnectThread:" + secure);
    ConnectThread connectThread = new ConnectThread(device, secure, Constant.MY_UUID_SECURE, Constant.MY_UUID_INSECURE);
    Thread thread = new Thread(connectThread);
    thread.setName("ConnectThread:" + secure);
    thread.start();

    Personality.connectThread = connectThread;
  }

  public static void startConnectedThread(BluetoothSocket socket, String socketType) {
    Log.d("utility", "startConnectedThread");
    ConnectedThread connectedThread = new ConnectedThread(socket, socketType);
    Thread thread = new Thread(connectedThread);
    thread.setName("ConnectedThread:" + socketType);
    thread.start();

    Personality.connectedThread = connectedThread;
  }

  public static void startTimeServerThread(BluetoothSocket socket, String socketType) {
    Log.d("utility", "startTimeServerThread");
    TimeServerThread timeServerThread = new TimeServerThread(socket, socketType);
    Thread thread = new Thread(timeServerThread);
    thread.setName("TimeServerThread:" + socketType);
    thread.start();

    Personality.timeServerThread = timeServerThread;
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */