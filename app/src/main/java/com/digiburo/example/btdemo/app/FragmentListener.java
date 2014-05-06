package com.digiburo.example.btdemo.app;

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