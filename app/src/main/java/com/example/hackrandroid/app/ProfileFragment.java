package com.example.hackrandroid.app;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hackrandroid.app.utils.DisplayUtils;

import java.util.ArrayList;


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

    View header = inflater.inflate(R.layout.profile_header, null, false);

    ImageView profilePic = (ImageView) header.findViewById(R.id.profile_header_image);
    Bitmap bmp = ((BitmapDrawable)profilePic.getDrawable()).getBitmap();
    profilePic.setImageBitmap(DisplayUtils.getCroppedBitmap(bmp));

    ListView profileListView = (ListView) rootView.findViewById(R.id.profile_list);

    profileListView.addHeaderView(header);

//    DisplayUtils.sourcesSansRegularifyTextView(nameTextView);
//    DisplayUtils.sourcesSansLightifyTextView(schoolTextView);
//
//    String currentUser = ParseUser.getCurrentUser().getUsername();
//
//    ParseQuery<ParseUser> query = ParseUser.getQuery();
//    query.fromLocalDatastore().whereEqualTo("username", currentUser).findInBackground(new FindCallback<ParseUser>() {
//        @Override
//        public void done(List<ParseUser> parseUsers, ParseException e) {
//            if (e == null) {
//                Log.e("size", String.valueOf(parseUsers.size()));
//                ParseUser parseUser = parseUsers.get(0);
//                String schoolName = parseUser.getString("school");
//                schoolTextView.setText(schoolName);
//
//                String userName = parseUser.getString("name");
//                nameTextView.setText(userName);
//
//                byte[] byteArray = parseUser.getBytes("image");
//                bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
//                        byteArray.length);
//
//                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
//
//                profileImageView.setImageBitmap(bitmap);
//                profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            } else {
//                Log.e("error", "error");
//            }
//        }
//    });

    return rootView;
  }

  private class ProfileAdapter extends ArrayAdapter<ProfileItem>{

    ArrayList<ProfileItem> objects;


    public ProfileAdapter(Context context, ArrayList<ProfileItem> objects) {
      super(context,0, objects);
      this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

      View v = convertView;
      ProfileItem item = objects.get(position);

      if (v == null) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(item.isHeader())
          v = inflater.inflate(R.layout.profile_item_header, null);
        else
          v = inflater.inflate(R.layout.profile_item, null);
      }

      if (item.isHeader()){

      }

      return v;

    }


  }
}
