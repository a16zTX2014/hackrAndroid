package com.example.hackrandroid.app;


public class Skill {

  private String name;
  private boolean isSelected;

  public Skill(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    this.isSelected = false;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  public void toggleSelected(){
    isSelected = !isSelected;
  }
}
