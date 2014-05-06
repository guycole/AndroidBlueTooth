package com.digiburo.example.btdemo.app;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.digiburo.example.btdemo.R;

public class MainActivity extends ActionBarActivity implements FragmentListener {

  public static final int ENABLE_BLUETOOTH_REQUEST = 2718;

  private TabHelper tabHelper;

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

    BlueToothHelper bth = new BlueToothHelper();
    bth.setup(bth.getAdapter());
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
   * start time server
   */
  public void startTimeServer() {
    Log.d(LOG_TAG, "start time server");
    timeService.start();
  }

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