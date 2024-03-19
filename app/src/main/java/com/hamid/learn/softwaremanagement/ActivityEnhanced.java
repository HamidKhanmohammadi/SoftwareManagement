package com.hamid.learn.softwaremanagement;

import android.support.v7.app.AppCompatActivity;

public class ActivityEnhanced extends AppCompatActivity {
  @Override
  protected void onResume() {
    G.currentActivity = this;
    super.onResume();
  }
}
