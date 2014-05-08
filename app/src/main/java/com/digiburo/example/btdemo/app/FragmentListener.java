package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

/**
 * @author gsc
 */
public interface FragmentListener {

  /**
   * request the platform bluetooth enable dialog
   */
  void requestBlueToothDialog();

  /**
   * make this platform discoverable
   */
  void makeDiscoverable();

  /**
   * send a chat message to remote device
   * @param payload
   */
  void sendChatMessage(String payload);

  /**
   * start chat client
   * @param device
   */
  void startChatClient(BluetoothDevice device);

  /**
   * start chat server
   */
  void startChatServer();

  /**
   * stop chat server
   */
  void stopChatServer();

  /**
   * start time server
   */
  void startTimeServer();

  /**
   * stop time server
   */
  void stopTimeServer();
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */