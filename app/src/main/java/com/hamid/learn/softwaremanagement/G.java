package com.hamid.learn.softwaremanagement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.File;

import utils.LruBitmapCache;

public class G extends Application {
  public static final String DIR_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
  public static final String DIR_APP = DIR_SDCARD +"/software-management";
  public static final String DIR_TEMP = DIR_APP +"/temp";
  public static final String DIR_FINAL = DIR_APP +"/final";
  public static final int DOWNLOAD_BUFFER_SIZE = 8 * 1024;

  public static SQLiteDatabase database;
  public static Context context;
  public static Activity currentActivity;
  public static final Handler HANDLER = new Handler();
  public static SoftwareInfo softInfo;

  public static final String TAG = G.class.getSimpleName();
  private RequestQueue mRequestQueue;
  private ImageLoader mImageLoader;
  private static G mInstance;


  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;
    context = getApplicationContext();

    new File(DIR_APP).mkdirs();
    new File(DIR_TEMP).mkdirs();
    new File(DIR_FINAL).mkdirs();

    DownloadManager.initialize();
    //manageDatabase();
  }
  public void manageDatabase(){
    database = SQLiteDatabase.openOrCreateDatabase(DIR_APP + "/database2.sqlite", null);
    database.execSQL("CREATE  TABLE  IF NOT EXISTS soft " +
      " (soft_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE" +
      " , soft_name TEXT, " +
      "soft_beneficiary TEXT, " +
      "soft_logoUrl TEXT, " +
      "soft_author TEXT, " +
      "soft_startDate DATETIME, " +
      "soft_endDate DATETIME, " +
      "soft_startupDate DATETIME, " +
      "soft_designeLanguage TEXT, " +
      "soft_dbLanguage TEXT, " +
      "soft_locationServer TEXT, " +
      "soft_ip TEXT, " +
      "soft_phoneNumber TEXT, " +
      "soft_email TEXT, " +
      "soft_description TEXT)");

  }


  public static synchronized G getInstance() {
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    return mRequestQueue;
  }

  public ImageLoader getImageLoader() {
    getRequestQueue();
    if (mImageLoader == null) {
      mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
    }
    return this.mImageLoader;
  }

  public <T> void addToRequestQueue(Request<T> req, String tag) {
    // set the default tag if tag is empty
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(req);
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }
}
