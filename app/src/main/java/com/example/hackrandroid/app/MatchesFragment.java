package com.example.hackrandroid.app;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class MatchesFragment extends Fragment {

  ListView matchesListView;

  static LinkedHashMap<String, ParseUser> matchesData;
  static ArrayList<ParseUser> matchedUsersData;

  static MatchesAdapter adapter;

  public MatchesFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

    matchesListView = (ListView) rootView.findViewById(R.id.matches_list);

    matchesData = new LinkedHashMap<String, ParseUser>();
    matchedUsersData = new ArrayList<ParseUser>();

    adapter = new MatchesAdapter(getActivity(), matchedUsersData);

    matchesListView.setAdapter(adapter);

    getListOfMatches();

    adapter.notifyDataSetChanged();
    return rootView;
  }

  public void getListOfMatches() {

    ArrayList<ParseQuery<ParseObject>> parseQueryArrayList = new ArrayList<ParseQuery<ParseObject>>();
    ParseUser user = ParseUser.getCurrentUser();
    final String currentUser = user.getUsername();
    Log.e("current user", currentUser);

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
    query.whereEqualTo("matchee", user).whereEqualTo("status", 2);
    parseQueryArrayList.add(query);

    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Match");
    query2.whereEqualTo("matcher", user).whereEqualTo("status", 2);
    parseQueryArrayList.add(query2);

    ParseQuery.or(parseQueryArrayList).findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> parseObjects, ParseException e) {
        if (e == null) {
          Log.e("size", String.valueOf(parseObjects.size()));
          for (int i = 0; i < parseObjects.size(); i++) {
            String matchee = parseObjects.get(0).getParseUser("matchee").getUsername();
            String matcher = parseObjects.get(0).getParseUser("matcher").getUsername();

            if (matchee.equals(currentUser)) {
              Log.e("matchee", matcher);
              matchesData.put(matcher, parseObjects.get(0).getParseUser("matcher"));
            } else {
              Log.e("matcher", matchee);
              matchesData.put(matchee, parseObjects.get(0).getParseUser("matchee"));
            }
          }
          matchedUsersData = new ArrayList<ParseUser>(matchesData.values());
          Log.e("user Data size", matchedUsersData.size() + "");
          adapter.notifyDataSetChanged();
        }
      }
    });
  }

  public class MatchesAdapter extends ArrayAdapter<ParseUser> {

    ArrayList<ParseUser> objects;


    public MatchesAdapter(Context context, ArrayList<ParseUser> objects) {
      super(context, 0, objects);
      this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

      View v = convertView;
      ParseUser item = objects.get(position);

      if (v == null) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.matches_item, null);
      }
      Log.e("Adapter", "Reached Here");
      ImageView image = (ImageView) v.findViewById(R.id.matches_profile_pic);
      TextView name = (TextView) v.findViewById(R.id.matches_item_name);
      TextView school = (TextView) v.findViewById(R.id.matches_item_subtitle);

      DisplayUtils.sansSerifLightifyTextView(name);
      DisplayUtils.sansSerifLightifyTextView(school);
      DisplayUtils.sansSerifLightifyTextView((TextView)v.findViewById(R.id.matches_item_Text));
      DisplayUtils.sansSerifLightifyTextView((TextView)v.findViewById(R.id.matches_item_call));

      name.setText((String)item.get("name"));
      school.setText((String)item.get("school"));

      byte[] byteArray = item.getBytes("image");
      Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
              byteArray.length);

      bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, false);

      image.setImageBitmap(DisplayUtils.getCroppedBitmap(bitmap));
      image.setScaleType(ImageView.ScaleType.CENTER_CROP);

      return v;

    }

  }
}
