package com.hamid.learn.softwaremanagement;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivitySoftwareList extends ActivityEnhanced {

  ArrayList<SoftwareInfo> softs = new ArrayList<>();
  ListViewAdapter<SoftwareInfo> adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_software_list);

    ListView lst_soft = (ListView) findViewById(R.id.lst_soft);

    adapter = new SoftwareListViewAdapter(this, softs);
    lst_soft.setAdapter(adapter);


    //fromServer();
    //fromDatabase();
    fakeData();
  }

  public void fromServer(){
    String tag_json_array = "tag_json_array";
    String url = "https://ware.uncox.com/api/profile/generate";

    final ProgressDialog pDialog = new ProgressDialog(this);
    pDialog.setMessage("Loading... ");
    pDialog.show();


    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
      @Override
      public void onResponse(JSONArray jsonArray) {
        Log.e("LOG", "log 1: " + jsonArray.toString());
        try {
          softs.clear();
          for (int i = 0 ; i < jsonArray.length() ; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            SoftwareInfo soft = new SoftwareInfo();
            Log.e("LOG", "object : " + jsonObject.getString("soft_name"));

            soft.name = jsonObject.getString("soft_name");
            soft.beneficiary = jsonObject.getString("soft_beneficiary");
            soft.logoUrl = jsonObject.getString("soft_logoUrl");

            soft.author = jsonObject.getString("soft_author");
            soft.startDate = jsonObject.getString("soft_startDate");
            soft.endDate = jsonObject.getString("soft_endDate");
            soft.startupDate = jsonObject.getString("soft_endDate");
            soft.designeLanguage = jsonObject.getString("soft_designeLanguage");
            soft.dbLanguage = jsonObject.getString("soft_dbLanguage");
            soft.locationServer = jsonObject.getString("soft_locationServer");
            soft.ip = jsonObject.getString("soft_ip");
            soft.phoneNumber = jsonObject.getInt("soft_phoneNumber");
            soft.email = jsonObject.getString("soft_email");
            soft.description = jsonObject.getString("soft_description");

            softs.add(soft);
          }

          adapter.notifyDataSetChanged();
        } catch (JSONException e) {
          e.printStackTrace();
        }

        pDialog.hide();
      }
    },
      new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
          VolleyLog.e("LOG", volleyError.getMessage());
          pDialog.hide();
        }
      }){
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "Androidhive");
        params.put("email", "abc@androidhive.info");
        params.put("password", "password123");
        return params;
      }
    };
    G.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_array);

    Cache cache = G.getInstance().getRequestQueue().getCache();
    Cache.Entry entry = cache.get(url);
    if (entry != null){
      try {
        String data = new String(entry.data, "UTF-8");
        // txtName.setText(data);
        pDialog.hide();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    } else {

    }
  }

  private void fromDatabase(){
    Cursor cursor = G.database.rawQuery("SELECT * FROM soft", null);
    while (cursor.moveToNext()){

      SoftwareInfo softwareInfo = new SoftwareInfo();

      softwareInfo.name =  cursor.getString(cursor.getColumnIndex("soft_name"));
      softwareInfo.beneficiary = cursor.getString(cursor.getColumnIndex("soft_beneficiary"));
      softwareInfo.logoUrl = cursor.getString(cursor.getColumnIndex("soft_logoUrl"));

      softwareInfo.author = cursor.getString(cursor.getColumnIndex("soft_author"));
      softwareInfo.startDate = cursor.getString(cursor.getColumnIndex("soft_startDate"));
      softwareInfo.endDate = cursor.getString(cursor.getColumnIndex("soft_endDate"));
      softwareInfo.startupDate = cursor.getString(cursor.getColumnIndex("soft_startupDate"));
      softwareInfo.designeLanguage = cursor.getString(cursor.getColumnIndex("soft_designeLanguage"));
      softwareInfo.dbLanguage = cursor.getString(cursor.getColumnIndex("soft_dbLanguage"));
      softwareInfo.locationServer = cursor.getString(cursor.getColumnIndex("soft_locationServer"));
      softwareInfo.ip = cursor.getString(cursor.getColumnIndex("soft_ip"));
      softwareInfo.phoneNumber = cursor.getInt(cursor.getColumnIndex("soft_phoneNumber"));
      softwareInfo.email = cursor.getString(cursor.getColumnIndex("soft_email"));
      softwareInfo.description = cursor.getString(cursor.getColumnIndex("soft_description"));

      softs.add(softwareInfo);
    }
    cursor.close();

    adapter.notifyDataSetChanged();
  }

  public void fakeData(){
    softs.clear();
    for(int i=0; i<50; i++){
      SoftwareInfo soft = new SoftwareInfo();
      soft.name = "software " + i;
      soft.beneficiary = " moavenat " + i;

      soft.author = "tarrahi & tolid";
      soft.startDate = "1395/XX/XX";
      soft.endDate = "1395/XX/XX";
      soft.startupDate = "1395/XX/XX";
      soft.designeLanguage = "C#";
      soft.dbLanguage = "MySQL";
      soft.ip = "10.35.110.110";
      soft.locationServer = "data senter";
      soft.phoneNumber = 511119;
      soft.email = "khanmohammadi.hamid@gmail.com";
      soft.description = "this is e sample text..." +
        "this is e sample text..." +
        "this is e sample text..." +
        "this is e sample text..." +
        "this is e sample text..." +
        "this is e sample text..." +
        "this is e sample text..." +
        "this is e sample text..."
      ;

      softs.add(soft);
    }
    adapter.notifyDataSetChanged();
  }
}

