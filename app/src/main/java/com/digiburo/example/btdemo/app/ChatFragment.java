package com.digiburo.example.btdemo.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digiburo.example.btdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * bluetooth chat
 */
public class ChatFragment extends ListFragment {
  public static final String FRAGMENT_TAG = "TAG_CHAT";

  private ChatArrayAdapter chatArrayAdapter;

  private List<ChatContainer> chatList = new ArrayList<ChatContainer>();

  private FragmentListener fragmentListener;

  private boolean runFlag = false;
  private Button serverButton;
  private Button sendButton;
  private EditText sendEdit;

  private final String LOG_TAG = getClass().getName();

  private final Handler chatHandler = new Handler() {
    @Override
    public void handleMessage(Message message) {
      Log.d(LOG_TAG, "fresh message in handler");

      Bundle bundle = message.getData();
      String author = bundle.getString(Constant.AUTHOR_KEY);
      String content = bundle.getString(Constant.MESSAGE_KEY);

      ChatContainer chatContainer = new ChatContainer();
      chatContainer.setAuthor(author);
      chatContainer.setMessage(content);

      chatArrayAdapter.add(chatContainer);
    }
  };

  private void localChat() {
    String temp = sendEdit.getText().toString().trim();

    Log.d(LOG_TAG, "transmit:" + temp);

    if (temp.isEmpty()) {
      Toast.makeText(getActivity(), R.string.toast_empty_string, Toast.LENGTH_LONG).show();
      //TODO ensure connected to remote device
//    } else if (!runFlag) {
//      Toast.makeText(getActivity(), R.string.toast_server_not_running, Toast.LENGTH_LONG).show();
    } else {
      sendEdit.setText("");

      ChatContainer chatContainer = new ChatContainer();
      chatContainer.setAuthor(getActivity().getString(R.string.me));
      chatContainer.setMessage(temp);

      chatArrayAdapter.add(chatContainer);

      fragmentListener.sendChatMessage(temp);
    }
  }

  /**
   * mandatory empty ctor
   */
  public ChatFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    fragmentListener = (FragmentListener) activity;
    Personality.chatHandler = chatHandler;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_chat, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    sendEdit = (EditText) getActivity().findViewById(R.id.editSend);

    sendButton = (Button) getActivity().findViewById(R.id.buttonChatSend);
    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
      localChat();
      }
    });

    serverButton = (Button) getActivity().findViewById(R.id.buttonChatServer);
    serverButton.setOnClickListener(new View.OnClickListener() {

      /**
       * invoke scan function
       * @param view
       */
      @Override
      public void onClick(View view) {
      runFlag = !runFlag;
      if (runFlag) {
        serverButton.setText(R.string.button_chat_server_stop);
        fragmentListener.startChatServer();
      } else {
        serverButton.setText(R.string.button_chat_server_start);
        fragmentListener.stopChatServer();
      }
      }
    });

    chatArrayAdapter = new ChatArrayAdapter(getActivity(), chatList);
    setListAdapter(chatArrayAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */