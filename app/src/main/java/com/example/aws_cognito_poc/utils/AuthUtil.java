package com.example.aws_cognito_poc.utils;

import android.content.Context;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

public class AuthUtil {

    private final String LOG_TAG = "AuthUtil";

    public interface AuthObserver {

    }

    private AuthObserver mAuthObserver;

    public AuthUtil(AuthObserver mAuthObserver) {
        this.mAuthObserver = mAuthObserver;
    }

    public void SignUpWithAWSCognito(Context context, String email, String password) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        AWSConfiguration awsConfiguration = new AWSConfiguration(context);

        CognitoUserPool userPool = new CognitoUserPool(context, awsConfiguration);


        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
//        userAttributes.addAttribute("email", email);
        //  userAttributes.addAttribute("password", password);

        SignUpHandler signupCallback = new SignUpHandler() {

            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                Log.i(LOG_TAG, "Success : " + user.toString());
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(LOG_TAG, "Failure " + exception.getMessage());
            }
        };

        userPool.signUpInBackground(email, password, userAttributes, null, signupCallback);
    }
}
