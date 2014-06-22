package com.example.hackrandroid.app;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MatchesFragment extends Fragment {

  public MatchesFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

    getListOfMatches();

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
                      } else {
                          Log.e("matcher", matchee);
                      }

                  }
              }
          }
      });
  }
}
