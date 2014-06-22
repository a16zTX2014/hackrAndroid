package com.example.hackrandroid.app;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class LoginSkillsFragment extends Fragment {

  ListView skillsListView;
  ArrayList<Skill> skillsList;
  SkillsAdapter adapter;
  Set<String> skillSet;

  public LoginSkillsFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_login_skills, container, false);

    Button next = (Button) rootView.findViewById(R.id.login_skills_next);

    View header = inflater.inflate(R.layout.skills_header,null,false);

    skillSet = new HashSet<String>();

    next.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finishSignUp();
      }
    });

    skillsListView = (ListView) rootView.findViewById(R.id.login_skills_list);
    skillsListView.addHeaderView(header);

    skillsList = new ArrayList<Skill>();
    skillsList.add(new Skill("Web Dev"));
    skillsList.add(new Skill("iOS Dev"));
    skillsList.add(new Skill("Android Dev"));
    skillsList.add(new Skill("Frontend Wizard"));
    skillsList.add(new Skill("Idea Machine"));
    skillsList.add(new Skill("Backend Ninja"));

    adapter = new SkillsAdapter(getActivity(), R.layout.skill_item, skillsList);
    skillsListView.setAdapter(adapter);
    skillsListView.setHeaderDividersEnabled(false);

    return rootView;
  }

  private void finishSignUp(){
    //TODO: Push To Parse and Log in with Parse
    Bundle arguments = getArguments();
    String name = arguments.getString("name");
    String school = arguments.getString("school");
    String username = arguments.getString("username");
    String password = arguments.getString("password");
    Toast.makeText(getActivity(), username + " " + password + " " + name + " " + school, Toast.LENGTH_LONG).show();

    ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.put("name", name);
        user.put("school", school);
        user.put("image", arguments.getByteArray("profileImage"));
        //String[] skillsArray = skillSet.toArray(new String[skillSet.size()]);
        ArrayList<String> skillArray = new ArrayList<String>();
        skillArray.addAll(skillSet);
        Log.e("skill size", String.valueOf(skillArray.size()));
        user.addAll("skills", skillArray);


// other fields can be set just like with ParseObject
        user.put("phone", "650-253-0000");

        /*user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    //Toast.makeText(getActivity(), "Succeeded", Toast.LENGTH_SHORT).show();
                    Log.e("done", "DONE BITCH");
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });*/

      final ProgressDialog dlg = new ProgressDialog(getActivity());
      dlg.setTitle("Please wait.");
      dlg.setMessage("Attempting to create a new user.");
      dlg.show();
      // Try to sign up the user
      user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
              dlg.dismiss();

              if (e != null) {        // Error signing up, display the error msg
                  Toast.makeText(getActivity(),
                          e.getMessage(),
                          Toast.LENGTH_LONG).show();
              } else {
                  // Login successful, go to the MainScreenActivity
                  Toast.makeText(getActivity(),
                          "Sign up successful. Presenting the good stuff.",
                          Toast.LENGTH_LONG).show();

                  MainActivity.saveUserInstallationInfo();
                  Intent i = new Intent(getActivity(), MainActivity.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                          Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(i);
              }
          }
      });

    /*  Intent i = new Intent(getActivity(), MainActivity.class);
    i.putExtra("tab_index",2);
    startActivity(i);
    getActivity().finish();*/
  }

  private class SkillsAdapter extends ArrayAdapter<Skill> {

    private ArrayList<Skill> objects;
    private int layout;

    public SkillsAdapter(Context context, int layout, ArrayList<Skill> objects) {
      super(context, layout, objects);
      this.objects = objects;
      this.layout = layout;
    }

    public View getView(int position, View convertView, ViewGroup parent){

      View v = convertView;

      if (v == null) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(layout, null);
      }
      final Skill item = objects.get(position);
      TextView text = (TextView) v.findViewById(R.id.login_skills_item_text);
      ImageView icon = (ImageView) v.findViewById(R.id.login_skills_item_icon);

      text.setText(objects.get(position).getName());

      if(item.isSelected()){
        v.setBackgroundColor(getResources().getColor(R.color.cloud));
        icon.setVisibility(View.VISIBLE);
      }
      else{
        v.setBackgroundColor(getResources().getColor(R.color.white));
        icon.setVisibility(View.INVISIBLE);
      }

      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          item.toggleSelected();

          if (item.isSelected()) {
              skillSet.add(item.getName());
              String[] sarr = skillSet.toArray(new String[skillSet.size()]);
              Log.e("aray size", String.valueOf(sarr.length));
              Log.e("ahaha", sarr[0]);
          } else {
              skillSet.remove(item.getName());
          }

          adapter.notifyDataSetInvalidated();
        }
      });

      return v;

    }

  }
}
