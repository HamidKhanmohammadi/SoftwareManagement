package com.hamid.learn.softwaremanagement;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadRequest {

  private String downloadPath;
  private String filePath;
  private OnDownloadCompleteListener listener;
  private boolean simulate;

  private int downloadedSize;
  private int totalSize;
  private int percent;

  public DownloadRequest downloadPath(String value){
    downloadPath = value;
    return this;
  }

  public DownloadRequest filePath(String value){
    filePath = value;
    return this;
  }

  public DownloadRequest listener(OnDownloadCompleteListener value){
    listener = value;
    return this;
  }
  public DownloadRequest simulate(boolean value){
    simulate = value;
    return this;
  }

  public DownloadRequest download(){
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          final URL url = new URL(downloadPath);
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();

          connection.setRequestMethod("GET");
          connection.setDoOutput(true);
          connection.connect();

          totalSize = connection.getContentLength();

          File file = new File(filePath);
          if (file.exists()){
            file.delete();
          }

          FileOutputStream outputStream = new FileOutputStream(filePath);

          InputStream inputStream = connection.getInputStream();
          byte[] buffer = new byte[G.DOWNLOAD_BUFFER_SIZE];
          int len;
          while ((len = inputStream.read(buffer))>0) {
            outputStream.write(buffer, 0, len);
            downloadedSize += len;

            percent = (int) (100.0f * (float) downloadedSize / totalSize);
            if (percent == 100 && listener != null) {
              G.HANDLER.post(new Runnable() {
                @Override
                public void run() {

                  listener.onDownloadComplete(downloadPath, filePath);
                }
              });
            }

            if (simulate) {
              try {
                Thread.sleep(100);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }/***/
          }
          outputStream.close();

        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    });
    thread.start();

    return this;
    }
  public int getDownloadedSize(){
    return downloadedSize;
  }
  public int getTotalSize(){
    return totalSize;
  }
  public int getPercent(){
    return percent;
  }
}
