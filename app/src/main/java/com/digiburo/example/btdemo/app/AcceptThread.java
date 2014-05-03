package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Service incoming connections
 * @author gsc
 */
public class AcceptThread extends AbstractParent implements Runnable {
//  public class AcceptThread extends Thread {

  private final String LOG_TAG = getClass().getName();

  private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
  private final BluetoothServerSocket serverSocket;
  private String socketType;
  private int state;

  public AcceptThread(boolean secure, String name, UUID uuid) {
    BluetoothServerSocket bluetoothServerSocket = null;
    socketType = secure ? "Secure":"Insecure";

    try {
      if (secure) {
        bluetoothServerSocket = adapter.listenUsingRfcommWithServiceRecord(name, uuid);
      } else {
        bluetoothServerSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(name, uuid);
      }
    } catch(IOException exception) {
      Log.e(LOG_TAG, "Socket Type: " + socketType + "listen() failed", exception);
    }

    serverSocket = bluetoothServerSocket;
  }

  public void run() {
    Log.d(LOG_TAG, "Socket Type: " + socketType + "BEGIN mAcceptThread" + this);
//    setName("AcceptThread:" + socketType);

    BluetoothSocket socket = null;

    while(state != Constant.STATE_CONNECTED) {
      try {
        // This is a blocking call and will only return on a successful connection or an exception
        socket = serverSocket.accept();
      } catch(IOException exception) {
        Log.e(LOG_TAG, "Socket Type: " + socketType + "accept() failed", exception);
        break;
      }

      Log.d(LOG_TAG, "connection accepted");
      if (socket != null) {
        synchronized(AcceptThread.this) {
          switch (state) {
            case Constant.STATE_LISTEN:
            case Constant.STATE_CONNECTING:
              connected(socket, socket.getRemoteDevice(), socketType);
              break;
            case Constant.STATE_NONE:
            case Constant.STATE_CONNECTED:
              // Either not ready or already connected. Terminate new socket.
              try {
                socket.close();
              } catch(IOException exception) {
                Log.e(LOG_TAG, "Could not close unwanted socket", exception);
              }
              break;
          }
        }
      }
    }

    Log.i(LOG_TAG, "END mAcceptThread, socket Type: " + socketType);
  }

  public void cancel() {
    Log.d(LOG_TAG, "Socket Type" + socketType + "cancel " + this);

    try {
      serverSocket.close();
    } catch (IOException e) {
      Log.e(LOG_TAG, "Socket Type" + socketType + "close() of server failed", e);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */
