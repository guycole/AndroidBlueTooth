package com.digiburo.example.btdemo.app.chat;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.digiburo.example.btdemo.app.ChatArrayAdapter;
import com.digiburo.example.btdemo.app.ChatContainer;
import com.digiburo.example.btdemo.app.Constant;
import com.digiburo.example.btdemo.app.Personality;
import com.digiburo.example.btdemo.app.chat.AbstractParent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gsc
 */
public class ConnectedThread extends AbstractParent implements Runnable {
  private final String LOG_TAG = getClass().getName();

  private final BluetoothDevice device;
  private final InputStream inStream;
  private final OutputStream outStream;

  /**
   *
   * @param socket
   * @param socketType
   */
  public ConnectedThread(BluetoothSocket socket, String socketType) {
    Log.d(LOG_TAG, "create ConnectedThread: " + socketType);

    InputStream tmpIn = null;
    OutputStream tmpOut = null;

    device = socket.getRemoteDevice();

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

  /**
   *
   */
  public void run() {
    Log.d(LOG_TAG, "connected run()");
    byte[] buffer = new byte[1024];
    int limit;

    // Keep listening to the InputStream while connected
    while (true) {
      try {
        // Read from the InputStream
        int readCount = inStream.read(buffer);
        String rx = new String(buffer, 0, readCount);
        Log.d(LOG_TAG, "read:" + readCount + ":" + rx);

//        String response = "response";
//        write(response.getBytes());

        handlerWrite(rx);
      } catch (IOException exception) {
        Log.e(LOG_TAG, "xxx xxx disconnected xxx xxx", exception);
        connectionLost();
        // TODO Start the service over to restart listening mode
        //EchoService.this.start();
        break;
      }
    }
  }

  private void handlerWrite(String message) {
    Bundle bundle = new Bundle();
    bundle.putString(Constant.AUTHOR_KEY, device.getName());
    bundle.putString(Constant.MESSAGE_KEY, message);

    Handler chatHandler = Personality.chatHandler;
    Message chatMessage = chatHandler.obtainMessage();
    chatMessage.setData(bundle);
    chatHandler.sendMessage(chatMessage);
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
    /*
    try {
      //socket.close();
    } catch (IOException e) {
      Log.e(LOG_TAG, "close() of connect socket failed", e);
    }
    */
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 30, 2014 by gsc
 */