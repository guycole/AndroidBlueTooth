package com.digiburo.example.btdemo.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digiburo.example.btdemo.R;

import java.util.List;

/**
 * chat array adapter
 *
 * @author gsc
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatContainer> {

  private final Context context;

  public static final String LOG_TAG = ChatArrayAdapter.class.getName();

  public ChatArrayAdapter(Context context, List<ChatContainer> list) {
    super(context, R.layout.row_chat, list);
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ChatContainer item = getItem(position);

    View rowView;
    ViewHolder holder;

    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      rowView = inflater.inflate(R.layout.row_chat, null);
      holder = new ViewHolder(rowView);
      rowView.setTag(holder);
    } else {
      rowView = convertView;
      holder = (ViewHolder) rowView.getTag();
    }

    holder.author.setText(item.getAuthor());
    holder.message.setText(item.getMessage());

    return(rowView);
  }

  class ViewHolder {
    private TextView author;
    private TextView message;

    ViewHolder(View view) {
      author = (TextView) view.findViewById(R.id.chatAuthor);
      message = (TextView) view.findViewById(R.id.chatMessage);
    }
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */