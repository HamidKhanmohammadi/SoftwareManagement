package com.hamid.learn.softwaremanagement;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySoftwareDetail extends ActivityEnhanced {

  private TextView txt_name;
  private TextView txt_author;
  private TextView txt_start_date;
  private TextView txt_end_date;
  private TextView txt_startup_date;
  private TextView txt_designe_language;
  private TextView txt_db_language;
  private TextView txt_location_server;
  private TextView txt_ip;
  private TextView txt_phone;
  private TextView txt_email;
  private TextView txt_desc;
  private ImageView img_logo;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_software_detail);

    txt_name = (TextView) findViewById(R.id.txt_name);
    txt_author = (TextView) findViewById(R.id.txt_author);
    txt_start_date = (TextView) findViewById(R.id.txt_start_date);
    txt_end_date = ( TextView) findViewById(R.id.txt_end_date);
    txt_startup_date = (TextView) findViewById(R.id.txt_startup_date);
    txt_designe_language = (TextView) findViewById(R.id.txt_design_language);
    txt_db_language = (TextView) findViewById(R.id.txt_db_language);
    txt_location_server = (TextView) findViewById(R.id.txt_location_server);
    txt_ip = (TextView) findViewById(R.id.txt_ip);
    txt_phone = (TextView) findViewById(R.id.txt_phone);
    txt_email = (TextView) findViewById(R.id.txt_email);
    txt_desc = (TextView) findViewById(R.id.txt_desc);
    img_logo = (ImageView) findViewById(R.id.img_logo);

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        G.HANDLER.post(new Runnable() {
          @Override
          public void run() {
            txt_name.setText(G.context.getResources().getString(R.string.app_name)  + " " + G.softInfo.name);
            txt_author.setText(G.context.getResources().getString(R.string.soft_author) + " " + G.softInfo.author);
            txt_start_date.setText("" + G.context.getResources().getString(R.string.soft_startDate) + " " + G.softInfo.startDate);
            txt_end_date.setText("" + G.context.getResources().getString(R.string.soft_endDate) + " " + G.softInfo.endDate);
            txt_startup_date.setText("" + G.context.getResources().getString(R.string.soft_startupDate) + " " + G.softInfo.startupDate);
            txt_designe_language.setText(G.context.getResources().getString(R.string.soft_designeLanguage) + " " + G.softInfo.designeLanguage);
            txt_db_language.setText(G.context.getResources().getString(R.string.soft_dbLanguage) + " " + G.softInfo.dbLanguage);
            txt_location_server.setText(G.context.getResources().getString(R.string.soft_locationServer) + " " + G.softInfo.locationServer);
            txt_ip.setText(G.context.getResources().getString(R.string.soft_ip) + " " + G.softInfo.ip);
            txt_phone.setText(G.context.getResources().getString(R.string.soft_phoneNumber) + " " + G.softInfo.phoneNumber);
            txt_email.setText(G.context.getResources().getString(R.string.soft_email) + " " + G.softInfo.email);
            txt_desc.setText(G.softInfo.description);


            /*String filename = HelperString.getFileName(G.softInfo.logoUrl);
            File imageFile = new File(G.DIR_FINAL + "/" + filename);
            if (!imageFile.exists()){
              img_logo.setImageBitmap(null);
              DownloadManager.addToDownloadList(G.softInfo.logoUrl, img_logo);
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 8;

            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
            img_logo.setImageBitmap(bitmap);*/

          }
        });
      }
    });
    thread.start();
  }
}
