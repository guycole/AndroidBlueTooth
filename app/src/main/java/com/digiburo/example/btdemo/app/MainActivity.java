package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.digiburo.example.btdemo.R;
import com.digiburo.example.btdemo.app.chat.ChatService;
import com.digiburo.example.btdemo.app.time.TimeService;

public class MainActivity extends ActionBarActivity implements FragmentListener {

  public static final int ENABLE_BLUETOOTH_REQUEST = 2718;

  private BluetoothDevice currentDevice;

  private TabHelper tabHelper;

  private final ChatService chatService = new ChatService();
  private final TimeService timeService = new TimeService();

  private final String LOG_TAG = getClass().getName();

  /**
   * FragmentListener
   * request the platform bluetooth enable dialog
   */
  public void requestBlueToothDialog() {
    Log.d(LOG_TAG, "request BT dialog");
    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(intent, ENABLE_BLUETOOTH_REQUEST);
  }

  /**
   * FragmentListener
   * make this platform discoverable
   */
  public void makeDiscoverable() {
    Log.d(LOG_TAG, "make discoverable");
    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
    startActivity(intent);
  }

  /**
   * send a chat message to remote device
   * @param payload
   */
  public void sendChatMessage(String payload) {
    Log.d(LOG_TAG, "send chat message:" + payload);
    chatService.write(payload);
  }

  /**
   *
   * @param device
   */
  public void startChatClient(BluetoothDevice device) {
    Log.d(LOG_TAG, "start chat client");
    tabHelper.selectChat();
    currentDevice = device;
    chatService.connect(device, false);
  }

  /**
   * start chat server
   */
  public void startChatServer() {
    Log.d(LOG_TAG, "start chat server");
    chatService.start();
  }

  /**
   * stop chat server
   */
  public void stopChatServer() {
    Log.d(LOG_TAG, "stop chat server");
    chatService.stop();
  }

  /**
   * start time server
   */
  public void startTimeServer() {
    Log.d(LOG_TAG, "start time server");
    timeService.start();
  }

  /**
   * stop time server
   */
  public void stopTimeServer() {
    Log.d(LOG_TAG, "stop time server");
    timeService.stop();
  }

  /**
   *
   * @param requestCode
   * @param resultCode
   * @param intent
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);

    Log.d(LOG_TAG, "on activity result:" + requestCode + ":" + resultCode + ":" + intent);

    if (resultCode == RESULT_CANCELED) {
      Log.d(LOG_TAG, "cancel noted");
      return;
    }

    BlueToothHelper bth = new BlueToothHelper();
    bth.setup(bth.getAdapter());

    //TODO test for result code

    if (intent != null) {
      Log.d(LOG_TAG, "getAction:" + intent.getAction());
    }
  }

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    tabHelper = new TabHelper(this);
    tabHelper.initialize();
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */