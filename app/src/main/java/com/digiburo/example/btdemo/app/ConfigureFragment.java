package com.digiburo.example.btdemo.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.digiburo.example.btdemo.R;

/**
 * Display current bluetooth configuration
 */
public class ConfigureFragment extends Fragment {

  public static final String FRAGMENT_TAG = "TAG_CONFIGURE";

  private FragmentListener fragmentListener;

  private TextView address;
  private TextView bondedDevice;
  private TextView enabled;
  private TextView name;
  private TextView scan;
  private TextView state;

  private final String LOG_TAG = getClass().getName();

  /**
   * mandatory empty ctor
   */
  public ConfigureFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    fragmentListener = (FragmentListener) activity;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_configure, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Button discoverableButton = (Button) getActivity().findViewById(R.id.buttonDiscoverable);
    discoverableButton.setOnClickListener(new View.OnClickListener() {

      /**
       * invoke scan function
       * @param view
       */
      @Override
      public void onClick(View view) {
      fragmentListener.makeDiscoverable();
      }
    });

    Button enableButton = (Button) getActivity().findViewById(R.id.buttonEnable);
    enableButton.setOnClickListener(new View.OnClickListener() {

      /**
       * invoke scan function
       * @param view
       */
      @Override
      public void onClick(View view) {
      fragmentListener.requestBlueToothDialog();
      }
    });

    address = (TextView) getActivity().findViewById(R.id.textAddress01);
    bondedDevice = (TextView) getActivity().findViewById(R.id.textBondedDevice01);
    enabled = (TextView) getActivity().findViewById(R.id.textEnabled01);
    name = (TextView) getActivity().findViewById(R.id.textName01);
    scan = (TextView) getActivity().findViewById(R.id.textScan01);
    state = (TextView) getActivity().findViewById(R.id.textState01);
  }

  @Override
  public void onResume() {
    super.onResume();
    loadLayout();
  }

  /**
   * populate display
   */
  private void loadLayout() {
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    if (adapter == null) {
      address.setText(R.string.unknown);
      bondedDevice.setText(R.string.unknown);
      enabled.setText(R.string.unknown);
      name.setText(R.string.unknown);
      scan.setText(R.string.unknown);
      state.setText(R.string.unknown);
    } else {
      address.setText(adapter.getAddress().toString());
      bondedDevice.setText(Integer.toString(adapter.getBondedDevices().size()));

      if (adapter.isEnabled()) {
        enabled.setText(R.string.truex);
      } else {
        enabled.setText(R.string.falsex);
      }

      name.setText(adapter.getName().toString());

      switch(adapter.getScanMode()) {
        case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
          scan.setText(R.string.connectable);
          break;
        case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
          scan.setText(R.string.discoverable);
          break;
        case BluetoothAdapter.SCAN_MODE_NONE:
          scan.setText(R.string.none);
          break;
        default:
          Log.e(LOG_TAG, "unknown scan:" + adapter.getScanMode());
          scan.setText(R.string.unknown);
      }

      switch(adapter.getState()) {
        case BluetoothAdapter.STATE_OFF:
          state.setText(R.string.on);
          break;
        case BluetoothAdapter.STATE_ON:
          state.setText(R.string.off);
          break;
        case BluetoothAdapter.STATE_TURNING_ON:
          state.setText(R.string.turning_on);
          break;
        case BluetoothAdapter.STATE_TURNING_OFF:
          state.setText(R.string.turning_off);
          break;
        default:
          Log.e(LOG_TAG, "unknown state:" + adapter.getState());
          state.setText(R.string.unknown);
      }
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */