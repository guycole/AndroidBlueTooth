package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gsc
 */
public class ConnectedThread extends AbstractParent implements Runnable {
  private final String LOG_TAG = getClass().getName();

  private final BluetoothSocket socket = null;
  private final InputStream inStream;
  private final OutputStream outStream;

  public ConnectedThread(BluetoothSocket socket, String socketType) {
    Log.d(LOG_TAG, "create ConnectedThread: " + socketType);

//    this.socket = socket;
    InputStream tmpIn = null;
    OutputStream tmpOut = null;

    // Get the BluetoothSocket input and output streams
    try {
      tmpIn = socket.getInputStream();
      tmpOut = socket.getOutputStream();
    } catch (IOException exception) {
      Log.e(LOG_TAG, "temp sockets not created", exception);
    }

    inStream = tmpIn;
    outStream = tmpOut;
  }

  public void run() {
    Log.d(LOG_TAG, "connected run()");
    byte[] buffer = new byte[1024];
    int bytes;

    // Keep listening to the InputStream while connected
    while (true) {
      try {
        // Read from the InputStream
        bytes = inStream.read(buffer);
        Log.d(LOG_TAG, "read:" + bytes + ":" + new String(buffer, 0, bytes));

        String response = "response";
        write(response.getBytes());
      } catch (IOException exception) {
        Log.e(LOG_TAG, "xxx xxx disconnected xxx xxx", exception);
        connectionLost();
        // Start the service over to restart listening mode
        //EchoService.this.start();
        break;
      }
    }
  }

  /**
   * Write to the connected OutStream.
   * @param buffer  The bytes to write
   */
  public void write(byte[] buffer) {
    Log.d(LOG_TAG, "write write write");
    try {
      outStream.write(buffer);
    } catch (IOException e) {
      Log.e(LOG_TAG, "Exception during write", e);
    }
  }

  public void cancel() {
    Log.d(LOG_TAG, "cancel cancel cancel");
    try {
      socket.close();
    } catch (IOException e) {
      Log.e(LOG_TAG, "close() of connect socket failed", e);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */