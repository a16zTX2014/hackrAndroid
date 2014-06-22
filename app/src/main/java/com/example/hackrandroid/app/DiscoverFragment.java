package com.example.hackrandroid.app;


import android.animation.Animator;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    heartButton = rootView.findViewById(R.id.discover_heart_button);
    nopeButton = rootView.findViewById(R.id.discover_nope_button);

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

    DisplayUtils.sourcesSansRegularifyTextView(name);
    DisplayUtils.sourcesSansRegularifyTextView(name2);
    DisplayUtils.sourcesSansLightifyTextView(school);
    DisplayUtils.sourcesSansLightifyTextView(school2);

    heartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isAnimating) {
          if (index + 1 >= usersList.size()){
            Toast.makeText(getActivity(),"You have run out of hackthon attendees :(", Toast.LENGTH_LONG).show();
          }
          else{
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
                            Toast.makeText(getActivity(), "found a match", Toast.LENGTH_LONG).show();
                            ParseObject parseObject = parseObjects.get(0);
                            parseObject.put("status", 2);
                            parseObject.saveInBackground();
                        } else {
                            Toast.makeText(getActivity(), "creating intiail match", Toast.LENGTH_LONG).show();
                            ParseObject match = new ParseObject("Match");
                            match.put("matchee", ParseUser.getCurrentUser());
                            match.put("matcher", usersList.get(index));
                            match.put("status", 1);
                            match.saveInBackground();
                        }
                    }
                }
            });


            isAnimating = true;
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
          if (onStart){
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


    byte[] byteArray = currentUser.getBytes("image");
    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
            byteArray.length);

    bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);

    pic.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap));
    pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
    Log.e("userID", currentUser.getObjectId());

    String userName2 = nextUser.getString("name");
    name.setText(userName2);
    String schoolName2 = nextUser.getString("school");
    school.setText(schoolName2);


    byte[] byteArray2 = nextUser.getBytes("image");
    Bitmap bitmap2 = BitmapFactory.decodeByteArray(byteArray2, 0,
            byteArray2.length);
    Log.e("nextUser", nextUser.getObjectId());

    bitmap2 = Bitmap.createScaledBitmap(bitmap2, 400, 400, false);

    pic2.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap2));
    pic2.setScaleType(ImageView.ScaleType.CENTER_CROP);
  }
}
