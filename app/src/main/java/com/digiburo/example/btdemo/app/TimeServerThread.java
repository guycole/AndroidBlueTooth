package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothSocket;
import android.text.format.Time;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gsc
 */
public class TimeServerThread extends AbstractParent implements Runnable {
  private final String LOG_TAG = getClass().getName();

  private final BluetoothSocket socket = null;
  private final InputStream inStream;
  private final OutputStream outStream;

  public TimeServerThread(BluetoothSocket socket, String socketType) {
    Log.d(LOG_TAG, "create TimeServerThread: " + socketType);

    InputStream tmpIn = null;
    OutputStream tmpOut = null;

    try {
      tmpIn = socket.getInputStream();
      tmpOut = socket.getOutputStream();
    } catch (IOException exception) {
      Log.e(LOG_TAG, "socket creation failure", exception);
    }

    inStream = tmpIn;
    outStream = tmpOut;
  }

  public void run() {
    Log.d(LOG_TAG, "time server run()");

    Time time = new Time();
    time.setToNow();

    try {
      outStream.write(time.toString().getBytes());
    } catch (IOException exception) {
      Log.e(LOG_TAG, "time write failure", exception);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */