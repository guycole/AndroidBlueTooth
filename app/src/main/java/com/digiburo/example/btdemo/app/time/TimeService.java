package com.digiburo.example.btdemo.app.time;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.text.format.Time;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Server side time service.  Insecure only.
 * @author gsc
 */
public class TimeService {
  private final String LOG_TAG = getClass().getName();

  public static final String TIME_INSECURE = "InsecureTimeService";
  public static final UUID UUID_INSECURE = UUID.fromString("4ada7ecd-28e4-4631-9e54-8055cecbf9d0");

  private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  private BluetoothServerSocket serverSocket = null;

  private Thread thread;
  private boolean runFlag;

  public synchronized void start() {
    Log.d(LOG_TAG, "start start start");
    runFlag = true;

    AcceptTimeThread acceptThread = new AcceptTimeThread();
    thread = new Thread(acceptThread);
    thread.start();
  }

  public synchronized void stop() {
    Log.d(LOG_TAG, "stop stop stop");
    runFlag = false;

    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch(Exception exception) {
      //empty
    }
  }

  class AcceptTimeThread implements Runnable {
    final String LOG_TAG = getClass().getName();

    OutputStream outStream = null;
    BluetoothSocket socket = null;

    public void run() {
      while(runFlag) {
        Log.d(LOG_TAG, "run run run");

        try {
          serverSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(TIME_INSECURE, UUID_INSECURE);
          socket = serverSocket.accept();
          if (socket != null) {
            serverSocket.close();

            Time time = new Time();
            time.setToNow();

            outStream = socket.getOutputStream();
            outStream.write(time.toString().getBytes());
          }
        } catch (IOException exception) {
          Log.e(LOG_TAG, "accept failure", exception);
        } finally {
          if (socket != null) {
            try {
              socket.close();
            } catch(Exception exception) {
              //empty
            }
          }
        }
      }

      Log.e(LOG_TAG, "run exit");
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on May 4, 2014 by gsc
 */