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

    }
}