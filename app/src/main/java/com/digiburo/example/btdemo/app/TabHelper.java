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
  private ActionBar.Tab discoveryTab;
  private ActionBar.Tab scanTab;

  //
  private final ConfigureFragment configureFragment;
  private final DiscoveryFragment discoveryFragment;

  /**
   * ctor
   * @param activity
   */
  public TabHelper(MainActivity activity) {
    mainActivity = activity;

    configureFragment = (ConfigureFragment) Fragment.instantiate(mainActivity, ConfigureFragment.class.getName());
    discoveryFragment = (DiscoveryFragment) Fragment.instantiate(mainActivity, DiscoveryFragment.class.getName());
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    Log.d(LOG_TAG, "tabSelected:" + tab.getTag());

    if (tab.getTag().equals(ConfigureFragment.FRAGMENT_TAG)) {
      fragmentTransaction.add(R.id.layoutFragment01, configureFragment, ConfigureFragment.FRAGMENT_TAG);
    } else if (tab.getTag().equals(DiscoveryFragment.FRAGMENT_TAG)) {
      fragmentTransaction.add(R.id.layoutFragment01, discoveryFragment, DiscoveryFragment.FRAGMENT_TAG);
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

    if (tab.getTag().equals(ConfigureFragment.FRAGMENT_TAG)) {
      fragmentTransaction.remove(configureFragment);
    } else if (tab.getTag().equals(DiscoveryFragment.FRAGMENT_TAG)) {
      fragmentTransaction.remove(discoveryFragment);
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
    if (tab.getTag().equals(ConfigureFragment.FRAGMENT_TAG)) {
      //empty
    } else if (tab.getTag().equals(DiscoveryFragment.FRAGMENT_TAG)) {
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

    discoveryTab = actionBar.newTab();
    scanTab = actionBar.newTab();

    discoveryTab.setTabListener(this);
    scanTab.setTabListener(this);

    discoveryTab.setTag(ConfigureFragment.FRAGMENT_TAG);
    scanTab.setTag(DiscoveryFragment.FRAGMENT_TAG);

    discoveryTab.setText(R.string.action_bar_tab_configuration);
    scanTab.setText(R.string.action_bar_tab_discovery);

    actionBar.addTab(discoveryTab);
    actionBar.addTab(scanTab);
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
