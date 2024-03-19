package com.hamid.learn.softwaremanagement;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SoftwareListViewAdapter extends ListViewAdapter<SoftwareInfo> {

  public static HashMap<ImageView, String> imageMap = new HashMap<>();

  public SoftwareListViewAdapter(Context context, ArrayList<SoftwareInfo> list) {
    super(context, list);
  }

  public static class SoftwareViewHolder extends ListViewAdapter.ViewHolder {
    ViewGroup root;
    TextView txt_name;
    TextView txt_beneficiary;
    ImageView img_logo;
  }

  @Override
  public ListViewAdapter.ViewHolder assign(View convertView) {
    SoftwareViewHolder viewHolder = new SoftwareViewHolder();
    viewHolder.root = (ViewGroup) convertView;
    viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
    viewHolder.txt_beneficiary = (TextView) convertView.findViewById(R.id.txt_beneficiary);
    viewHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
    return viewHolder;
  }

  @Override
  public void fill(ListViewAdapter.ViewHolder upcastedViewHolder, final SoftwareInfo item) {
    SoftwareViewHolder viewHolder = (SoftwareViewHolder) upcastedViewHolder;
    viewHolder.txt_name.setText(item.name);
    viewHolder.txt_beneficiary.setText(G.context.getResources().getString(R.string.soft_beneficiary)  + " " + item.beneficiary);
    viewHolder.root.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        G.softInfo = item;
        Intent intent = new Intent(G.currentActivity, ActivitySoftwareDetail.class);
        G.currentActivity.startActivity(intent);
      }
    });

   /* String filename = HelperString.getFileName(item.logoUrl);
    File imageFile = new File(G.DIR_FINAL + "/" + filename);
    if (!imageFile.exists()){
      viewHolder.img_logo.setImageBitmap(null);
      DownloadManager.addToDownloadList(item.logoUrl, viewHolder.img_logo);
    }

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inSampleSize = 8;

    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    viewHolder.img_logo.setImageBitmap(bitmap);*/
  }

}
