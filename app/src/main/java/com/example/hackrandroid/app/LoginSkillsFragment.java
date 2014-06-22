package com.example.hackrandroid.app;


import android.app.Fragment;
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

import java.util.ArrayList;


public class LoginSkillsFragment extends Fragment {

  ListView skillsListView;
  ArrayList<Skill> skillsList;
  SkillsAdapter adapter;

  public LoginSkillsFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_login_skills, container, false);

    Button next = (Button) rootView.findViewById(R.id.login_skills_next);

    View header = inflater.inflate(R.layout.skills_header,null,false);

    next.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finishSignUp();
      }
    });

    skillsListView = (ListView) rootView.findViewById(R.id.login_skills_list);
    skillsListView.addHeaderView(header);

    skillsList = new ArrayList<Skill>();
    skillsList.add(new Skill("Web Development"));
    skillsList.add(new Skill("iOS Development"));
    skillsList.add(new Skill("Android Development"));
    skillsList.add(new Skill("Frontend Wizard"));
    skillsList.add(new Skill("Idea Machine"));
    skillsList.add(new Skill("Backend Ninja"));

    adapter = new SkillsAdapter(getActivity(), R.layout.skill_item, skillsList);
    skillsListView.setAdapter(adapter);


    return rootView;
  }

  private void finishSignUp(){
    //TODO: Push To Parse and Log in with Parse
    Intent i = new Intent(getActivity(), MainActivity.class);
    i.putExtra("tab_index",2);
    startActivity(i);
    getActivity().finish();
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
          Log.d("Click", "CLCIK" + item.isSelected());
          item.toggleSelected();
          adapter.notifyDataSetInvalidated();
        }
      });

      return v;

    }

  }
}
