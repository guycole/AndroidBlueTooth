package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gsc
 */
public class TimeClient {
  private final String LOG_TAG = getClass().getName();

  public String getRemoteTime(BluetoothDevice device) {

    try {
      BluetoothSocket blueToothSocket = device.createInsecureRfcommSocketToServiceRecord(TimeService.UUID_INSECURE);
      blueToothSocket.connect();

      InputStream inStream = blueToothSocket.getInputStream();

      try {
        Thread.sleep(1000L);
      } catch(Exception e) {}

      byte[] buffer = new byte[1024];
      int readCount = inStream.read(buffer);
      String timeString = new String(buffer, 0, readCount);

      Log.d(LOG_TAG, "read:" + readCount + ":" + timeString);
      blueToothSocket.close();
      return(timeString);
    } catch(IOException exception) {
      Log.e(LOG_TAG, "choke", exception);
    }

    return "No Remote Response";
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on May 4, 2014 by gsc
 */