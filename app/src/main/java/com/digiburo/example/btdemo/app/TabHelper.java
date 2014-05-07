package com.digiburo.example.btdemo.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.digiburo.example.btdemo.R;

/**
 * React to ActionBar tab events
 */
public class TabHelper implements ActionBar.TabListener {

  //
  private final String LOG_TAG = getClass().getName();

  //
  private final MainActivity mainActivity;

  //
  private String currentFragmentTag = ConfigureFragment.FRAGMENT_TAG;

  //
  private ActionBar.Tab chatTab;
  private ActionBar.Tab configureTab;
  private ActionBar.Tab discoveryTab;
  private ActionBar.Tab timeTab;

  //
  private final ChatFragment chatFragment;
  private final ConfigureFragment configureFragment;
  private final DiscoveryFragment discoveryFragment;
  private final TimeFragment timeFragment;

  /**
   * ctor
   * @param activity
   */
  public TabHelper(MainActivity activity) {
    mainActivity = activity;

    chatFragment = (ChatFragment) Fragment.instantiate(mainActivity, ChatFragment.class.getName());
    configureFragment = (ConfigureFragment) Fragment.instantiate(mainActivity, ConfigureFragment.class.getName());
    discoveryFragment = (DiscoveryFragment) Fragment.instantiate(mainActivity, DiscoveryFragment.class.getName());
    timeFragment = (TimeFragment) Fragment.instantiate(mainActivity, TimeFragment.class.getName());
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    Log.d(LOG_TAG, "tabSelected:" + tab.getTag());

    if (tab.getTag().equals(ChatFragment.FRAGMENT_TAG)) {
      fragmentTransaction.add(R.id.layoutFragment01, chatFragment, ChatFragment.FRAGMENT_TAG);
    } else if (tab.getTag().equals(ConfigureFragment.FRAGMENT_TAG)) {
      fragmentTransaction.add(R.id.layoutFragment01, configureFragment, ConfigureFragment.FRAGMENT_TAG);
    } else if (tab.getTag().equals(DiscoveryFragment.FRAGMENT_TAG)) {
      fragmentTransaction.add(R.id.layoutFragment01, discoveryFragment, DiscoveryFragment.FRAGMENT_TAG);
    } else if (tab.getTag().equals(TimeFragment.FRAGMENT_TAG)) {
      fragmentTransaction.add(R.id.layoutFragment01, timeFragment, TimeFragment.FRAGMENT_TAG);
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }

    currentFragmentTag = tab.getTag().toString();
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    Log.d(LOG_TAG, "tabUnSelected:" + tab.getTag());

    if (tab.getTag().equals(ChatFragment.FRAGMENT_TAG)) {
      fragmentTransaction.remove(chatFragment);
    } else if (tab.getTag().equals(ConfigureFragment.FRAGMENT_TAG)) {
      fragmentTransaction.remove(configureFragment);
    } else if (tab.getTag().equals(DiscoveryFragment.FRAGMENT_TAG)) {
      fragmentTransaction.remove(discoveryFragment);
    } else if (tab.getTag().equals(TimeFragment.FRAGMENT_TAG)) {
      fragmentTransaction.remove(timeFragment);
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    if (tab.getTag().equals(ChatFragment.FRAGMENT_TAG)) {
      //empty
    } else if (tab.getTag().equals(ConfigureFragment.FRAGMENT_TAG)) {
      //empty
    } else if (tab.getTag().equals(DiscoveryFragment.FRAGMENT_TAG)) {
      //empty
    } else if (tab.getTag().equals(TimeFragment.FRAGMENT_TAG)) {
      //empty
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }
  }

  /**
   * populate ActionBar
   */
  public void initialize() {
    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

    ActionBar actionBar = mainActivity.getSupportActionBar();

    chatTab = actionBar.newTab();
    configureTab = actionBar.newTab();
    discoveryTab = actionBar.newTab();
    timeTab = actionBar.newTab();

    chatTab.setTabListener(this);
    configureTab.setTabListener(this);
    discoveryTab.setTabListener(this);
    timeTab.setTabListener(this);

    chatTab.setTag(ChatFragment.FRAGMENT_TAG);
    configureTab.setTag(ConfigureFragment.FRAGMENT_TAG);
    discoveryTab.setTag(DiscoveryFragment.FRAGMENT_TAG);
    timeTab.setTag(TimeFragment.FRAGMENT_TAG);

    chatTab.setText(R.string.action_bar_tab_chat);
    configureTab.setText(R.string.action_bar_tab_configuration);
    discoveryTab.setText(R.string.action_bar_tab_discovery);
    timeTab.setText(R.string.action_bar_tab_time);

    actionBar.addTab(configureTab);
    actionBar.addTab(discoveryTab);
    actionBar.addTab(timeTab);
    actionBar.addTab(chatTab);
  }

  /**
   * Currently selected tab fragment
   * @return currently selected tab fragment
   */
  public String getCurrentTabFragmentTag() {
    return currentFragmentTag;
  }
}

/*
 * Copyright 2014 Digital Burro, INC
 * Created on April 29, 2014 by gsc
 */
