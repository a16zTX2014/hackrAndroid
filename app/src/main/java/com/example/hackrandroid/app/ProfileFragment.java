package com.example.hackrandroid.app;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackrandroid.app.utils.DisplayUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileFragment extends Fragment {

  private TextView nameTextView;
  private TextView schoolTextView;
  private Bitmap bitmap;
  private ImageView profileImageView;

  public ProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

    nameTextView = (TextView) rootView.findViewById(R.id.profile_user_name);
    schoolTextView = (TextView) rootView.findViewById(R.id.profile_user_school);
    profileImageView = (ImageView) rootView.findViewById(R.id.profile_user_image);

    DisplayUtils.sourcesSansRegularifyTextView(nameTextView);
    DisplayUtils.sourcesSansLightifyTextView(schoolTextView);

    String currentUser = ParseUser.getCurrentUser().getUsername();

    ParseQuery<ParseUser> query = ParseUser.getQuery();
    query.fromLocalDatastore().whereEqualTo("username", currentUser).findInBackground(new FindCallback<ParseUser>() {
        @Override
        public void done(List<ParseUser> parseUsers, ParseException e) {
            if (e == null) {
                Log.e("size", String.valueOf(parseUsers.size()));
                ParseUser parseUser = parseUsers.get(0);
                String schoolName = parseUser.getString("school");
                schoolTextView.setText(schoolName);

                String userName = parseUser.getString("name");
                nameTextView.setText(userName);

                byte[] byteArray = parseUser.getBytes("image");
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                //bitmap.setHeight(200);
                //bitmap.setWidth(200);
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);

                profileImageView.setImageBitmap(bitmap);
                profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                Log.e("error", "error");
            }
        }
    });

    return rootView;
  }
}
