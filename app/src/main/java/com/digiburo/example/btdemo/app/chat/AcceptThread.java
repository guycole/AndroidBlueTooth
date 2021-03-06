package com.digiburo.example.btdemo.app.chat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.digiburo.example.btdemo.app.Constant;
import com.digiburo.example.btdemo.app.Personality;

import java.io.IOException;

/**
 * Service incoming connections
 * @author gsc
 */
public class AcceptThread extends AbstractParent implements Runnable {
  private final String LOG_TAG = getClass().getName();

  private final BluetoothAdapter adapter;
  private final BluetoothServerSocket serverSocket;
  private final String socketType;

  /**
   *
   * @param bluetoothAdapter
   * @param secure
   * @param name
   * @param uuid
   */
  public AcceptThread(BluetoothAdapter bluetoothAdapter, boolean secure) {
    adapter = bluetoothAdapter;
    socketType = secure ? "Secure":"Insecure";
    BluetoothServerSocket tempSocket = null;

    try {
      if (secure) {
        tempSocket = adapter.listenUsingRfcommWithServiceRecord(Constant.NAME_SECURE, Constant.UUID_SECURE);
      } else {
        tempSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(Constant.NAME_INSECURE, Constant.UUID_INSECURE);
      }
    } catch(IOException exception) {
      Log.e(LOG_TAG, "accept:" + socketType + ":listen failure", exception);
    }

    serverSocket = tempSocket;
  }

  /**
   *
   */
  public void run() {
    Log.d(LOG_TAG, "accept socketType:" + socketType + ":run()");

    BluetoothSocket socket = null;

    while(Personality.getState() != Constant.STATE_CONNECTED) {
      Log.d(LOG_TAG, "accept top of loop:" + Personality.getState());

      try {
        System.out.println("serverSocket:" + serverSocket);
        socket = serverSocket.accept();
      } catch(IOException exception) {
        Log.e(LOG_TAG, "socketType:" + socketType + ":accept failure", exception);
        break;
      }

      Log.d(LOG_TAG, "connection accepted:" + Personality.getState());

      if (socket != null) {
        synchronized(AcceptThread.this) {
          switch (Personality.getState()) {
            case Constant.STATE_LISTEN:
              Log.d(LOG_TAG, "listen");
            case Constant.STATE_CONNECTING:
              Log.d(LOG_TAG, "connecting");
              connected(socket, socketType);
              break;
            case Constant.STATE_NONE:
              Log.d(LOG_TAG, "none");
            case Constant.STATE_CONNECTED:
              Log.d(LOG_TAG, "connected");
              try {
                socket.close();
              } catch (IOException exception) {
                Log.e(LOG_TAG, "close failure socketType:" + socketType, exception);
              }
              break;
          }
        }
      }
    }

    Log.d(LOG_TAG, "run() exit");
  }

  /**
   *
   */
  public void cancel() {
    Log.d(LOG_TAG, "socketType:" + socketType + ":cancel:" + this);

    try {
      serverSocket.close();
    } catch (IOException exception) {
      Log.e(LOG_TAG, "close failure socketType:" + socketType, exception);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */