package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;

/**
 * @author gsc
 */
public class Personality {

  /**
   * empty ctor to block instantiation
   */
  private Personality() {
    //empty
  }

  //
  public static BluetoothAdapter blueToothAdapter;

  //
  public static AcceptThread secureAcceptThread;
  public static AcceptThread acceptThread;
  public static ConnectThread connectThread;
  public static ConnectedThread connectedThread;

  public static int state;

/*
  public synchronized int getState() {
    return state;
  }

  public synchronized void setState(int state) {
    Log.d(LOG_TAG, "setState() " + this.state + " -> " + state);
    this.state = state;
  }
*/
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */