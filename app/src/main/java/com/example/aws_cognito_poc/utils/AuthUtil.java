package com.example.aws_cognito_poc.utils;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

public class AuthUtil {

    private final String LOG_TAG = "AuthUtil";

    public interface AuthObserver {

    }

    public interface ForgotPasswordContinueCallback {
        void getContinuation(ForgotPasswordContinuation forgotPasswordContinuation);
    }

    private AuthObserver mAuthObserver;

    public AuthUtil(AuthObserver mAuthObserver) {
        this.mAuthObserver = mAuthObserver;
    }

    public void SignUpWithAWSCognito(Context context, String email, String password) {

        AWSConfiguration awsConfiguration = new AWSConfiguration(context);
        CognitoUserPool userPool = new CognitoUserPool(context, awsConfiguration);
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        SignUpHandler signupCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                Log.i(LOG_TAG, "Success : " + user.toString());
                Log.i(LOG_TAG, "Was OTP Sent : " + signUpResult.isUserConfirmed());
//                user.getAttributeVerificationCodeInBackground("email", new VerificationHandler() {
//                    @Override
//                    public void onSuccess(CognitoUserCodeDeliveryDetails verificationCodeDeliveryMedium) {
//                       // user.conf
//                        Log.i(LOG_TAG, "onSuccess: " + verificationCodeDeliveryMedium.getDeliveryMedium());
//                    }
//
//                    @Override
//                    public void onFailure(Exception exception) {
//
//                    }
//                });
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(LOG_TAG, "Failure " + exception.getMessage());
            }
        };

        userPool.signUpInBackground(email, password, userAttributes, null, signupCallback);
    }

    public void ConfirmEmail(Context context, String email, String verificationCode) {
        AWSConfiguration awsConfiguration = new AWSConfiguration(context);
        CognitoUserPool userPool = new CognitoUserPool(context, awsConfiguration);

        userPool.getUser(email).confirmSignUpInBackground(verificationCode, true, new GenericHandler() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "Verification Success");
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(LOG_TAG, "Fail" + exception.getMessage());
            }
        });
    }

    public void SignIn(Context context, String email, String password) {
        AWSConfiguration awsConfiguration = new AWSConfiguration(context);
        CognitoUserPool userPool = new CognitoUserPool(context, awsConfiguration);


        AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                Log.i(LOG_TAG, "Sign in Success :" + userSession.getIdToken().getJWTToken());
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(email, password, null);
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
                // NOT NEEDED
            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-in failed, check exception for the cause
                Log.i(LOG_TAG, "Sign in failed :" + exception.getMessage());
            }
        };


        userPool.getUser(email).getSessionInBackground(authenticationHandler);

    }

    public void RequestUserAWSCognitoPasswordReset(Context context, String email, ForgotPasswordContinueCallback callback) {

        AWSConfiguration awsConfiguration = new AWSConfiguration(context);
        CognitoUserPool userPool = new CognitoUserPool(context, awsConfiguration);
        CognitoUser user = userPool.getUser(email);

        user.forgotPasswordInBackground(new ForgotPasswordHandler() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "Password Reset Success");
            }

            @Override
            public void getResetCode(ForgotPasswordContinuation continuation) {
                Log.i(LOG_TAG, "In getResetCode");
                callback.getContinuation(continuation);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(LOG_TAG, "Forgot Password Fail" + exception.getMessage());
            }
        });

    }
}
