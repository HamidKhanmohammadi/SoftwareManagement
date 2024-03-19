package com.hamid.learn.softwaremanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ActivityStartup extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_startup);

    Toast.makeText(ActivityStartup.this, getString(R.string.soft_welcom), Toast.LENGTH_SHORT).show();

    G.HANDLER.postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(ActivityStartup.this, ActivitySoftwareList.class);
        ActivityStartup.this.startActivity(intent);
        ActivityStartup.this.finish();
      }
    },3000);
  }
}
