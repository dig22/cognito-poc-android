package com.example.aws_cognito_poc.utils;

import android.util.Log;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;

public class AuthUtil {

    private final String LOG_TAG = "AuthUtil";

    public interface AuthObserver{

    }
    private AuthObserver mAuthObserver;

    public AuthUtil(AuthObserver mAuthObserver) {
        this.mAuthObserver = mAuthObserver;
    }

    public void SignUpWithAWSCognito(String email, String password){
        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(),email));

        Amplify.Auth.signUp(
                email,
                password,
                AuthSignUpOptions.builder().userAttributes(attributes).build(),
                result -> Log.i(LOG_TAG, result.toString()),
                error -> Log.e(LOG_TAG, error.toString())
        );
    }
}
