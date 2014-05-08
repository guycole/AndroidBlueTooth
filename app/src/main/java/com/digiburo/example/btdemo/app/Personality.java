package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;

import com.digiburo.example.btdemo.app.chat.AcceptThread;
import com.digiburo.example.btdemo.app.chat.ConnectThread;
import com.digiburo.example.btdemo.app.chat.ConnectedThread;

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
  public static AcceptThread insecureAcceptThread;
  public static ConnectThread connectThread;
  public static ConnectedThread connectedThread;

  public static Handler chatHandler;

  private static int state;

  public synchronized static int getState() {
    return state;
  }

  public synchronized static void setState(int state) {
    Log.d("personality", "setState() " + Personality.state + " -> " + state);
    Personality.state = state;
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */