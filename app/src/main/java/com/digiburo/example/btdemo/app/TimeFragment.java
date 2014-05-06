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
 * start/stop time server
 */
public class TimeFragment extends Fragment {

  public static final String FRAGMENT_TAG = "TAG_TIME";

  private final String LOG_TAG = getClass().getName();

  private FragmentListener fragmentListener;
  private boolean runFlag = false;
  private Button serverButton;

  /**
   * mandatory empty ctor
   */
  public TimeFragment() {
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
    View view = inflater.inflate(R.layout.fragment_time, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    serverButton = (Button) getActivity().findViewById(R.id.buttonTimeServer);
    serverButton.setOnClickListener(new View.OnClickListener() {

      /**
       * invoke scan function
       * @param view
       */
      @Override
      public void onClick(View view) {
        runFlag = !runFlag;
        if (runFlag) {
          serverButton.setText(R.string.button_time_server_stop);
          fragmentListener.startTimeServer();
        } else {
          serverButton.setText(R.string.button_time_server_start);
          fragmentListener.stopTimeServer();
        }
      }
    });
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */