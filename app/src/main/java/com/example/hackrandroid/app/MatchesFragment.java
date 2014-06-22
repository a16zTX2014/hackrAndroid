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
      String currentUser = user.getUsername();

      ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
      query.fromLocalDatastore().whereEqualTo("matchee", currentUser).whereEqualTo("status", 2);

      ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Match");
      query.fromLocalDatastore().whereEqualTo("matcher", currentUser).whereEqualTo("status", 2);
      parseQueryArrayList.add(query);
      parseQueryArrayList.add(query2);

      ParseQuery.or(parseQueryArrayList).findInBackground(new FindCallback() {
          @Override
          public void done(List list, ParseException e) {
              if (e == null) {
                  Log.e("no error", "no error");
              } else {
                  Log.e("there was an error", "there was an error");
              }
          }
      });
  }
}
