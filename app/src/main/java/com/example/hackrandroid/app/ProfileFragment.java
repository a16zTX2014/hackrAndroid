package com.example.hackrandroid.app;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hackrandroid.app.utils.DisplayUtils;


public class ProfileFragment extends Fragment {

  public ProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

    TextView name = (TextView) rootView.findViewById(R.id.profile_user_name);
    TextView school = (TextView) rootView.findViewById(R.id.profile_user_school);

    DisplayUtils.sourcesSansRegularifyTextView(name);
    DisplayUtils.sourcesSansLightifyTextView(school);
    return rootView;
  }
}
