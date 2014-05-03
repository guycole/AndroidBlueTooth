package com.digiburo.example.btdemo.app;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digiburo.example.btdemo.R;

/**
 * custom array adapter example
 *
 * @author gsc
 */
public class DiscoveryArrayAdapter extends ArrayAdapter<BluetoothDevice> {

  private final Context context;

  public static final String LOG_TAG = DiscoveryArrayAdapter.class.getName();

  public DiscoveryArrayAdapter(Context context, List<BluetoothDevice> list) {
    super(context, R.layout.row_discovery, list);
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    BluetoothDevice item = getItem(position);

    View rowView;
    ViewHolder holder;

    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      rowView = inflater.inflate(R.layout.row_discovery, null);
      holder = new ViewHolder(rowView);
      rowView.setTag(holder);
    } else {
      rowView = convertView;
      holder = (ViewHolder) rowView.getTag();
    }

    holder.deviceAddress.setText(item.getAddress());
    holder.deviceName.setText(item.getName());

    return(rowView);
  }

  class ViewHolder {
    private TextView deviceAddress;
    private TextView deviceName;

    ViewHolder(View view) {
      deviceAddress = (TextView) view.findViewById(R.id.deviceAddress);
      deviceName = (TextView) view.findViewById(R.id.deviceName);
    }
  }
}

/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */