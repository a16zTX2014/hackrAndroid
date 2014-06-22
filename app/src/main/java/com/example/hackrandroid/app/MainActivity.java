package com.example.hackrandroid.app;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class MainActivity extends Activity {

    private int current_frag_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      if (savedInstanceState == null) {
        getFragmentManager().beginTransaction()
                .add(R.id.main_container, new DiscoverFragment())
                .commit();
      }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
          case R.id.action_settings:
            return true;
          case R.id.discover:
            slideFragment(current_frag_index, 0);
            break;
          case R.id.matches:
            slideFragment(current_frag_index, 1);
            break;
          case R.id.profile:
            slideFragment(current_frag_index, 2);
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void saveUserInstallationInfo() {
        // Associate the device with a user
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());
        installation.saveInBackground();
    }

    private void slideFragment(int beginPos, int finishPos){
      if (beginPos != finishPos){
        Fragment frag = getFragmentAtPos(finishPos);
        String tag = frag.getTag();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (beginPos < finishPos) {
          fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
        } else{
          fragmentTransaction.setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right);
        }
        fragmentTransaction.replace(R.id.main_container, frag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
        current_frag_index = finishPos;
      }
    }

    private Fragment getFragmentAtPos(int index){
      switch(index){
        case 0:
          return new DiscoverFragment();
        case 1:
          return new MatchesFragment();
        case 2:
          return new ProfileFragment();
      }
      return null;
    }
}
