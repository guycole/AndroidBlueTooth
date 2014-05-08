package com.digiburo.example.btdemo.app.chat;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.digiburo.example.btdemo.app.Constant;
import com.digiburo.example.btdemo.app.Personality;

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

      acceptThread = new AcceptThread(Personality.blueToothAdapter, true);

      Thread thread = new Thread(acceptThread);
      thread.setName("AcceptThread:secure");
      thread.start();

      Personality.secureAcceptThread = acceptThread;
    } else {
      acceptThread = Personality.insecureAcceptThread;
      if (acceptThread != null) {
        return;
      }

      acceptThread = new AcceptThread(Personality.blueToothAdapter, false);

      Thread thread = new Thread(acceptThread);
      thread.setName("AcceptThread:insecure");
      thread.start();

      Personality.insecureAcceptThread = acceptThread;
    }
  }

  public static void startConnectThread(BluetoothDevice device, boolean secure) {
    Log.d("utility", "startConnectThread:" + secure);
    ConnectThread connectThread = new ConnectThread(device, secure);
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
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */