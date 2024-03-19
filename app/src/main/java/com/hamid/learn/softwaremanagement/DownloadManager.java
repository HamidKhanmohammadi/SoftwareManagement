package com.hamid.learn.softwaremanagement;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class DownloadManager {
  public static ArrayList<String> urls = new ArrayList<>();

  public static OnDownloadCompleteListener downloadCompleteListener;

  public static void copyFile(String sourceFile, String destinationFile){
    FileInputStream inputStream = null;
    FileOutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(sourceFile);
      outputStream = new FileOutputStream(destinationFile);

      byte[] buffer = new byte[G.DOWNLOAD_BUFFER_SIZE];
      int len;
      while ((len = inputStream.read(buffer)) > 0) {
        outputStream.write(buffer, 0, len);
      }
    }
      catch (IOException e) {
        e.printStackTrace();
      }
     finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

     if (outputStream != null){
        try {
          outputStream.flush();
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void initialize() {
    downloadCompleteListener = new OnDownloadCompleteListener() {

      @Override
      public void onDownloadComplete(String url, String localPath) {

        Log.i("TEST", "Image downloaded complete...Original URL: " + url + ", save Path: " + localPath);
        String newPath =  localPath.replace("/temp/", "/final/");
        copyFile(localPath,newPath);
        String filename = HelperString.getFileName(localPath);
        new File(localPath).delete();

        Set<ImageView> imageViews = SoftwareListViewAdapter.imageMap.keySet();
        for(ImageView imageView: imageViews) {
          if(SoftwareListViewAdapter.imageMap.get(imageView).equals(filename)){
            if(imageView != null){
              BitmapFactory.Options options = new BitmapFactory.Options();
              //options.inSampleSize = 8;

              Bitmap bitmap = BitmapFactory.decodeFile(newPath, options);
              imageView.setImageBitmap(bitmap);
            }
          }
        }

      }
    };
  }
  public static void addToDownloadList (String url, ImageView imgLogo){
    String filename = HelperString.getFileName(url);
    SoftwareListViewAdapter.imageMap.put(imgLogo, filename);

    if (urls.contains(url)){
      return;
    }


    if (new File(G.DIR_FINAL + "/" + filename).exists()){
      return;
    }

    urls.add(url);

    DownloadRequest downloadRequest = new DownloadRequest()
      .downloadPath("https://ware.uncox.com/api/profile/generate" + url)
      .filePath(G.DIR_TEMP + "/" + filename)
      .listener(downloadCompleteListener)
      .download();
  }
}
