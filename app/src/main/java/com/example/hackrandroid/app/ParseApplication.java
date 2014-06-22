package com.example.hackrandroid.app;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Add your initialization here
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "OZZ5mGxl3vDzZwzEIeGJ19u0PTg16NE7E7xqQn7C", "59ix5pPVpQkXAPN3jAsHvaWVkwVpGEXSfN2Xohii");

        /*ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/

        /*ParseUser user = new ParseUser();
        user.setUsername("saiavala");
        user.setPassword("uTexas1!");
        user.setEmail("email@example.com");

// other fields can be set just like with ParseObject
        user.put("phone", "650-253-0000");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });*/

    }
}