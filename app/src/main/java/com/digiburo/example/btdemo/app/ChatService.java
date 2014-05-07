package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.text.format.Time;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Server side chat service.
 * @author gsc
 */
public class ChatService {
  private final String LOG_TAG = getClass().getName();

  public static final String TIME_SECURE = "InsecureTimeService";
  public static final UUID UUID_SECURE = UUID.fromString("4ada7ecd-28e4-4631-9e54-8055cecbf9d0");

  private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  private BluetoothServerSocket serverSocket = null;

  private Thread thread;
  private boolean runFlag;

  public synchronized void start() {
    Log.d(LOG_TAG, "start start start");
    runFlag = true;
  }

  public synchronized void stop() {
    Log.d(LOG_TAG, "stop stop stop");
    runFlag = false;

  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on May 6, 2014 by gsc
 */