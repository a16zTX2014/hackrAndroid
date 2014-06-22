package com.example.hackrandroid.app;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginProfileFragment extends Fragment {

  public LoginProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_login_profile, container, false);

    Button next = (Button) rootView.findViewById(R.id.login_profile_next);
    next.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment frag = new LoginSkillsFragment();
        String tag = frag.getTag();
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
      }
    });
    return rootView;
  }
}
