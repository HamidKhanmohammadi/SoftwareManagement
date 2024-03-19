package com.hamid.learn.softwaremanagement;

public class HelperString {
  public static String getFileName(String filePath){
    String[] strings = filePath.split("/");
    return strings[strings.length-1];
  }
}
