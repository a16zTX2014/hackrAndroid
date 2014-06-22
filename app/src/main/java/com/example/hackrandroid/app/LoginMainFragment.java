package com.example.hackrandroid.app;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginMainFragment extends Fragment {

  private EditText usernameView;
  private EditText passwordView;
  private Button loginButton;
  private Button signUpButton;

  public LoginMainFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_login, container, false);

    if (ParseUser.getCurrentUser() != null) {
      Intent i = new Intent(getActivity(), MainActivity.class);
      startActivity(i);
      getActivity().finish();
    }

    usernameView = (EditText) rootView.findViewById(R.id.login_username);
    passwordView = (EditText) rootView.findViewById(R.id.login_password);

    signUpButton = (Button) rootView.findViewById(R.id.login_signup);
    loginButton = (Button) rootView.findViewById(R.id.login_submit);
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
                /*Intent i = new Intent(getActivity(), MainActivity.class);
                Log.d("Login Fragment", "Logged in!");
                startActivity(i);
                getActivity().finish();*/

        loginUser();
      }
    });

    signUpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment frag = new LoginProfileFragment();
        String tag = frag.getTag();
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
      }
    });

    return rootView;
  }

  public void loginUser() {
    //Validate the login data
    boolean validationError = false;
    StringBuilder validationErrorMessage = new StringBuilder("Error loggin in");

    //Check the usernameView
    if (isEmpty(usernameView)) {
      validationError = true;
      validationErrorMessage.append("Username field is blank. You call yourself a hacker?");
    }

    //Check the passwordView
    if (isEmpty(passwordView)) {
      if (validationError) {
        validationErrorMessage.append("Password field is blank too. You're so dumb");
      } else {
        validationError = true;
        validationErrorMessage.append("Password field is blank. Lame...");
      }
    }

    //Check if the validation error is true
    if (validationError) {
      Toast.makeText(getActivity(), validationErrorMessage.toString(),
              Toast.LENGTH_LONG).show();
      return;
    }

    //Logging in waiting dialog
    final ProgressDialog dlg = new ProgressDialog(getActivity());
    dlg.setTitle("Please wait...");
    dlg.setMessage("Logging in...");
    dlg.show();

    //Attempt to login using Parse
    ParseUser.logInInBackground(usernameView.getText().toString(),
            passwordView.getText().toString(),
            new LogInCallback() {
              @Override
              public void done(ParseUser parseUser, ParseException e) {
                dlg.dismiss(); //get rid of the dialog
                if (e != null) {
                  Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                  MainActivity.saveUserInstallationInfo();
                  Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_LONG).show();
                  Intent i = new Intent(getActivity(), MainActivity.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(i);
                  getActivity().finish();
                }
              }
            }
    );

  }

  private boolean isEmpty(EditText editText) {
    if (editText.getText().toString().trim().length() > 0) {
      return false;
    } else {
      return true;
    }
  }
}