package com.example.hackrandroid.app;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LoginSkillsFragment extends Fragment {

  public LoginSkillsFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_login_skills, container, false);
    return rootView;
  }
}
