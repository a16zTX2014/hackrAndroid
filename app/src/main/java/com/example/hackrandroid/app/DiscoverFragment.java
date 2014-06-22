package com.example.hackrandroid.app;


import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DiscoverFragment extends Fragment {

  LinearLayout foreground;

  public DiscoverFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

    final LinearLayout profileContainer = (LinearLayout) rootView.findViewById(R.id.discover_profile_container);
    final ImageView pic = (ImageView) rootView.findViewById(R.id.profile_user_image);
    final TextView name = (TextView) rootView.findViewById(R.id.profile_user_name);
    final TextView school = (TextView) rootView.findViewById(R.id.profile_user_school);

    View heartButton = rootView.findViewById(R.id.discover_heart_button);
    View nopeButton = rootView.findViewById(R.id.discover_nope_button);

    final LinearLayout profileContainer2 = (LinearLayout) rootView.findViewById(R.id.discover_profile_container2);
    final ImageView pic2 = (ImageView) rootView.findViewById(R.id.profile_user2_image);
    final TextView name2 = (TextView) rootView.findViewById(R.id.profile_user2_name);
    final TextView school2 = (TextView) rootView.findViewById(R.id.profile_user2_school);


    heartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    nopeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        myView.animate().translationX(400).withLayer();

        //TODO: Get next user from parse
        name2.setText("Sai Avala");
        school2.setText("Unversity of Stupid");

        profileContainer2.setTranslationX(700);
        profileContainer2.setVisibility(View.VISIBLE);
        profileContainer.animate().translationX(-700).setDuration(300);
        profileContainer2.animate().translationX(0).setDuration(300).setListener(new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animation) {

          }

          @Override
          public void onAnimationEnd(Animator animation) {
            pic.setImageDrawable(pic2.getDrawable());
            name.setText(name2.getText());
            school.setText(school2.getText());

            profileContainer.setTranslationX(0);
            profileContainer2.setVisibility(View.GONE);
          }

          @Override
          public void onAnimationCancel(Animator animation) {

          }

          @Override
          public void onAnimationRepeat(Animator animation) {

          }
        });




      }
    });
    return rootView;
  }
}
