package com.example.aws_cognito_poc;

import android.app.Application;

public class App extends Application {

    private final String LOG_TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        //AWS auth init
//        try {
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.configure(getApplicationContext());
//            Log.i(LOG_TAG, "Initialized Amplify");
//        } catch (AmplifyException e) {
//            e.printStackTrace();
//        }

    }
}
