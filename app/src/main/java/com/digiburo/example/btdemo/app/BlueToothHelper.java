package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import java.util.Set;

/**
 * @author gsc
 */

public class BlueToothHelper {

  private final String LOG_TAG = getClass().getName();

  /**
   *
   * @return
   */
  public BluetoothAdapter getAdapter() {
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    if (adapter == null) {
      Log.d(LOG_TAG, "null bluetooth adapter");
    }

    return adapter;
  }

  /**
   *
   * @param bluetoothAdapter
   * @return true success
   */
  public boolean setup(BluetoothAdapter bluetoothAdapter) {
    if (bluetoothAdapter == null) {
      Log.d(LOG_TAG, "null bluetooth adapter");
      return false;
    }

    if (bluetoothAdapter.isEnabled()) {
      Log.d(LOG_TAG, "address:" + bluetoothAdapter.getAddress());
      Log.d(LOG_TAG, "name:" + bluetoothAdapter.getName());

      Set<BluetoothDevice> bonded = bluetoothAdapter.getBondedDevices();
      System.out.println("bonded:" + bonded.size());
    }

    return(false);
  }

  public String serviceCode(int target) {
    String result = "service unknown";

    switch(target) {
      case BluetoothClass.Service.AUDIO:
        result = "audio";
        break;
      case BluetoothClass.Service.CAPTURE:
        result = "capture";
        break;
      case BluetoothClass.Service.INFORMATION:
        result = "information";
        break;
      case BluetoothClass.Service.LIMITED_DISCOVERABILITY:
        result = "limited discovery";
        break;
      case BluetoothClass.Service.NETWORKING:
        result = "networking";
        break;
      case BluetoothClass.Service.OBJECT_TRANSFER:
        result = "object transfer";
        break;
      case BluetoothClass.Service.POSITIONING:
        result = "positioning";
        break;
      case BluetoothClass.Service.RENDER:
        result = "render";
        break;
      case BluetoothClass.Service.TELEPHONY:
        result = "telephony";
        break;
    }

    return(Integer.toHexString(target) + ":service " + result);
  }

  public String deviceCode(int target) {
    String result = "unknown";

    switch(target) {
      case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER:
        result = "audio/video camcorder";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO:
        result = "audio/video car audio";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
        result = "audio/video handsfree";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
        result = "audio/video headphones";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO:
        result = "audio/video hifi audio";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
        result = "audio/video loudspeaker";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE:
        result = "audio/video microphone";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO:
        result = "audio/video portable audio";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX:
        result = "audio/video set top";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED:
        result = "audio/video uncategorized";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_VCR:
        result = "audio/video vcr";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA:
        result = "audio/video video camera";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING:
        result = "audio/video video conferencing";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER:
        result = "audio/video video display and speaker";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY:
        result = "audio/video video game";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR:
        result = "audio/video monitor";
        break;
      case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET:
        result = "audio/video wearable headset";
        break;
      case BluetoothClass.Device.COMPUTER_DESKTOP:
        result = "computer desktop";
        break;
      case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA:
        result = "computer pda";
        break;
      case BluetoothClass.Device.COMPUTER_LAPTOP:
        result = "computer laptop";
        break;
      case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA:
        result = "computer palm";
        break;
      case BluetoothClass.Device.COMPUTER_SERVER:
        result = "computer server";
        break;
      case BluetoothClass.Device.COMPUTER_UNCATEGORIZED:
        result = "computer uncategorized";
        break;
      case BluetoothClass.Device.COMPUTER_WEARABLE:
        result = "computer wearable";
        break;
      case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
        result = "health blood pressure";
        break;
      case BluetoothClass.Device.HEALTH_DATA_DISPLAY:
        result = "health data display";
        break;
      case BluetoothClass.Device.HEALTH_GLUCOSE:
        result = "health glucose";
        break;
      case BluetoothClass.Device.HEALTH_PULSE_OXIMETER:
        result = "health pulse oximeter";
        break;
      case BluetoothClass.Device.HEALTH_PULSE_RATE:
        result = "health pulse rate";
        break;
      case BluetoothClass.Device.HEALTH_THERMOMETER:
        result = "health thermometer";
        break;
      case BluetoothClass.Device.HEALTH_UNCATEGORIZED:
        result = "health uncategorized";
        break;
      case BluetoothClass.Device.HEALTH_WEIGHING:
        result = "health weighing";
        break;
      case BluetoothClass.Device.PHONE_CELLULAR:
        result = "phone cellular";
        break;
      case BluetoothClass.Device.PHONE_CORDLESS:
        result = "phone cordless";
        break;
      case BluetoothClass.Device.PHONE_ISDN:
        result = "phone isdn";
        break;
      case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY:
        result = "phone modem";
        break;
      case BluetoothClass.Device.PHONE_SMART:
        result = "phone smart";
        break;
      case BluetoothClass.Device.PHONE_UNCATEGORIZED:
        result = "phone uncategorized";
        break;
      case BluetoothClass.Device.TOY_CONTROLLER:
        result = "toy controller";
        break;
      case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE:
        result = "toy doll";
        break;
      case BluetoothClass.Device.TOY_GAME:
        result = "toy game";
        break;
      case BluetoothClass.Device.TOY_ROBOT:
        result = "toy robot";
        break;
      case BluetoothClass.Device.TOY_UNCATEGORIZED:
        result = "toy uncategorized";
        break;
      case BluetoothClass.Device.TOY_VEHICLE:
        result = "toy vehicle";
        break;
      case BluetoothClass.Device.WEARABLE_GLASSES:
        result = "wearable glasses";
        break;
      case BluetoothClass.Device.WEARABLE_HELMET:
        result = "wearable helmet";
        break;
      case BluetoothClass.Device.WEARABLE_JACKET:
        result = "wearable jacket";
        break;
      case BluetoothClass.Device.WEARABLE_PAGER:
        result = "wearable pager";
        break;
      case BluetoothClass.Device.WEARABLE_UNCATEGORIZED:
        result = "wearable uncategorized";
        break;
      case BluetoothClass.Device.WEARABLE_WRIST_WATCH:
        result = "wearable wrist watch";
        break;
    }

    return(Integer.toHexString(target) + ":device " + result);
  }

  public String majorDeviceCode(int target) {
    String result = "unknown";

    switch(target) {
      case BluetoothClass.Device.Major.AUDIO_VIDEO:
        result = "audio/video";
        break;
      case BluetoothClass.Device.Major.COMPUTER:
        result = "computer";
        break;
      case BluetoothClass.Device.Major.HEALTH:
        result = "health";
        break;
      case BluetoothClass.Device.Major.IMAGING:
        result = "imaging";
        break;
      case BluetoothClass.Device.Major.MISC:
        result = "misc";
        break;
      case BluetoothClass.Device.Major.NETWORKING:
        result = "networking";
        break;
      case BluetoothClass.Device.Major.PERIPHERAL:
        result = "peripheral";
        break;
      case BluetoothClass.Device.Major.PHONE:
        result = "phone";
        break;
      case BluetoothClass.Device.Major.TOY:
        result = "toy";
        break;
      case BluetoothClass.Device.Major.UNCATEGORIZED:
        result = "uncategorized";
        break;
      case BluetoothClass.Device.Major.WEARABLE:
        result = "wearable";
        break;
    }

    return(Integer.toHexString(target) + ":major device " + result);
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */