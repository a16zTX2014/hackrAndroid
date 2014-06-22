package com.example.hackrandroid.app;


import android.animation.Animator;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackrandroid.app.utils.DisplayUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class DiscoverFragment extends Fragment {

  boolean isAnimating = false;

  static List<ParseUser> usersList;

  static ParseUser currentUser;
  static ParseUser nextUser;
  static int index;
  static boolean onStart = true;

  LinearLayout profileContainer;
  ImageView pic;
  TextView name;
  TextView school;

  LinearLayout profileContainer2;
  ImageView pic2;
  TextView name2;
  TextView school2;

  static ImageView inner_heart;
  static ImageView inner_nope;

  TextView skill1;
  TextView skill2;
  TextView skill3;

  View hero_border;
  View heartButton;
  View nopeButton;

  public DiscoverFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

    profileContainer = (LinearLayout) rootView.findViewById(R.id.discover_profile_container);
    pic = (ImageView) rootView.findViewById(R.id.profile_user_image);
    name = (TextView) rootView.findViewById(R.id.profile_user_name);
    school = (TextView) rootView.findViewById(R.id.profile_user_school);

    hero_border = rootView.findViewById(R.id.profile_image_border);

    Bitmap bp = ((BitmapDrawable)pic.getDrawable()).getBitmap();
    pic.setImageBitmap(DisplayUtils.getCroppedBitmap(bp));

    heartButton = rootView.findViewById(R.id.discover_heart_button);
    nopeButton = rootView.findViewById(R.id.discover_nope_button);

    inner_heart = (ImageView) rootView.findViewById(R.id.discover_inner_heart);
    inner_nope = (ImageView) rootView.findViewById(R.id.discover_inner_nope);

    skill1 = (TextView) rootView.findViewById(R.id.profile_user_skill1);
    skill2 = (TextView) rootView.findViewById(R.id.profile_user_skill2);
    skill3 = (TextView) rootView.findViewById(R.id.profile_user_skill3);


    profileContainer2 = (LinearLayout) rootView.findViewById(R.id.discover_profile_container2);
    pic2 = (ImageView) rootView.findViewById(R.id.profile_user2_image);
    name2 = (TextView) rootView.findViewById(R.id.profile_user2_name);
    school2 = (TextView) rootView.findViewById(R.id.profile_user2_school);

    if (onStart){
      getUsers();
    }
    else{
      updateUsers();
    }

    DisplayUtils.openSansRegularifyTextView(name);
    DisplayUtils.openSansRegularifyTextView(name2);
    DisplayUtils.openSansLightifyTextView(school);
    DisplayUtils.openSansLightifyTextView(school2);

      /*NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
      Notification notification = new Notification(R.drawable.ic_launcher, "New Message", System.currentTimeMillis());
      notification.setLatestEventInfo(getActivity(), "haha", "", null);
      notificationManager.notify(9999, notification);
      */

      heartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isAnimating) {
          if (index + 1 >= usersList.size()){
            Toast.makeText(getActivity(),"You have run out of hackthon attendees :(", Toast.LENGTH_SHORT).show();
          }
          else{
            isAnimating = true;

            hero_border.setBackground(getResources().getDrawable(R.drawable.hero_bg_green));
            inner_heart.animate().scaleX(.6f).scaleY(.6f).setDuration(200).setListener(new Animator.AnimatorListener() {
              @Override
              public void onAnimationStart(Animator animation) {

              }

              @Override
              public void onAnimationEnd(Animator animation) {
                inner_heart.animate().scaleX(1f).scaleY(1f).setDuration(200);
//                hero_border.setBackgroundColor(getResources().getColor(R.color.white));
              }

              @Override
              public void onAnimationCancel(Animator animation) {

              }

              @Override
              public void onAnimationRepeat(Animator animation) {

              }
            });

            Log.e("matchee", usersList.get(index).getUsername());
            ParseQuery<ParseObject> queryWantsToBeMatched = ParseQuery.getQuery("Match");
            queryWantsToBeMatched.whereEqualTo("matchee", usersList.get(index));
            queryWantsToBeMatched.whereEqualTo("matcher", ParseUser.getCurrentUser());
            queryWantsToBeMatched.whereEqualTo("status", 1);
            queryWantsToBeMatched.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    if (e == null) {
                        if (parseObjects.size() > 0) {
                            Toast.makeText(getActivity(), "found a match", Toast.LENGTH_SHORT).show();
                            ParseObject parseObject = parseObjects.get(0);
                            parseObject.put("status", 2);
                            parseObject.saveInBackground();
                        } else {
                            //Toast.makeText(getActivity(), "Finding you a match!", Toast.LENGTH_SHORT).show();
                            ParseObject match = new ParseObject("Match");
                            match.put("matchee", ParseUser.getCurrentUser());
                            match.put("matcher", usersList.get(index));
                            match.put("status", 1);
                            match.saveInBackground();
                        }
                    }
                }
            });


            profileContainer2.setTranslationX(-700);
            profileContainer2.setVisibility(View.VISIBLE);
            profileContainer.animate().translationX(700).setDuration(300);
            profileContainer2.animate().translationX(0).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
              @Override
              public void onAnimationStart(Animator animation) {

              }

              @Override
              public void onAnimationEnd(Animator animation) {
                index++;
                currentUser = nextUser;
                nextUser = usersList.get(index);

                hero_border.setBackground(getResources().getDrawable(R.drawable.hero_bg));

                updateUsers();

                profileContainer.setTranslationX(0);
                profileContainer2.setVisibility(View.GONE);
                heartButton.setAlpha(1);
                isAnimating = false;
              }

              @Override
              public void onAnimationCancel(Animator animation) {

              }

              @Override
              public void onAnimationRepeat(Animator animation) {

              }
            });
          }
        }
      }
    });

    nopeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        myView.animate().translationX(400).withLayer();
        if (!isAnimating) {
          if (index + 1 >= usersList.size()){
            Toast.makeText(getActivity(),"You have run out of hackthon attendees :(", Toast.LENGTH_LONG).show();
          }
          else {
            isAnimating = true;

            isAnimating = true;

            hero_border.setBackground(getResources().getDrawable(R.drawable.hero_bg_red));
            inner_nope.animate().scaleX(.6f).scaleY(.6f).setDuration(200).setListener(new Animator.AnimatorListener() {
              @Override
              public void onAnimationStart(Animator animation) {

              }

              @Override
              public void onAnimationEnd(Animator animation) {
                inner_nope.animate().scaleX(1f).scaleY(1f).setDuration(200);
              }

              @Override
              public void onAnimationCancel(Animator animation) {

              }

              @Override
              public void onAnimationRepeat(Animator animation) {

              }
            });

            profileContainer2.setTranslationX(700);
            profileContainer2.setVisibility(View.VISIBLE);
            profileContainer.animate().translationX(-700).setDuration(300);
            profileContainer2.animate().translationX(0).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
              @Override
              public void onAnimationStart(Animator animation) {

              }

              @Override
              public void onAnimationEnd(Animator animation) {
                index++;
                currentUser = nextUser;
                nextUser = usersList.get(index);
                hero_border.setBackground(getResources().getDrawable(R.drawable.hero_bg));

                updateUsers();

                profileContainer.setTranslationX(0);
                profileContainer2.setVisibility(View.GONE);
                isAnimating = false;
              }

              @Override
              public void onAnimationCancel(Animator animation) {

              }

              @Override
              public void onAnimationRepeat(Animator animation) {

              }
            });
          }

        }
      }
    });
    return rootView;
  }

  private void getUsers() {

    String currentUserName = ParseUser.getCurrentUser().getUsername();
    ParseQuery<ParseUser> query = ParseUser.getQuery();
    query.whereNotEqualTo("username", currentUserName).findInBackground(new FindCallback<ParseUser>() {
        @Override
        public void done(List<ParseUser> parseUsers, ParseException e) {
            if (e == null) {

                Log.e("size", String.valueOf(parseUsers.size()));
                currentUser = parseUsers.get(index);
                nextUser = parseUsers.get(index + 1);
                usersList = parseUsers;
                if (onStart) {
                    updateUsers();
                    onStart = false;
                }
            } else {
                Log.e("error", "error");
            }
        }
    });
  }

  private void updateUsers(){
    String userName = currentUser.getString("name");
    name.setText(userName);
    String schoolName = currentUser.getString("school");
    school.setText(schoolName);
    ArrayList<String> skills = (ArrayList<String>)currentUser.get("skills");

    if (skills.size() == 1) {
      skill1.setText(skills.get(0));
      skill1.setVisibility(View.VISIBLE);
    }
    else if (skills.size() == 2) {
      skill1.setText(skills.get(1));
      skill1.setVisibility(View.VISIBLE);
    }
    else if (skills.size() == 3) {
      skill1.setText(skills.get(2));
      skill1.setVisibility(View.VISIBLE);
    }
    if (currentUser.has("image")) {
      byte[] byteArray = currentUser.getBytes("image");
      Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
              byteArray.length);

      bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);

      pic.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap));
      pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
      Log.e("userID", currentUser.getObjectId());
    }
    String userName2 = nextUser.getString("name");
    name2.setText(userName2);
    String schoolName2 = nextUser.getString("school");
    school2.setText(schoolName2);

    if (nextUser.has("image")) {
      byte[] byteArray2 = nextUser.getBytes("image");
      Bitmap bitmap2 = BitmapFactory.decodeByteArray(byteArray2, 0,
              byteArray2.length);
      Log.e("nextUser", nextUser.getObjectId());

      bitmap2 = Bitmap.createScaledBitmap(bitmap2, 400, 400, false);

      pic2.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap2));
      pic2.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
  }
}
