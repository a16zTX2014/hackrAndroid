package com.example.hackrandroid.app;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

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
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new DiscoverFragment())
                    .commit();
            break;
          case R.id.matches:
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MatchesFragment())
                    .commit();
            break;
          case R.id.profile:
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new ProfileFragment())
                    .commit();
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
