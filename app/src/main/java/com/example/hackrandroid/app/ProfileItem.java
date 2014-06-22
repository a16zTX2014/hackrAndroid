package com.example.hackrandroid.app;


public class ProfileItem {

  private String label;
  private boolean isHeader;

  public ProfileItem(String label, boolean isHeader){
    this.label = label;
    this.isHeader = isHeader;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public boolean isHeader() {
    return isHeader;
  }

  public void setHeader(boolean isHeader) {
    this.isHeader = isHeader;
  }
}
