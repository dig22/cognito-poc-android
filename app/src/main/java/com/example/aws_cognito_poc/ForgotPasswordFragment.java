package com.example.aws_cognito_poc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.example.aws_cognito_poc.utils.AuthUtil;

public class ForgotPasswordFragment extends Fragment implements AuthUtil.AuthObserver, AuthUtil.ForgotPasswordContinueCallback {

    Button requestPasswordButton;
    Button confirmButton;
    EditText emailEditText;
    EditText newPasswordEditText;
    EditText otpEditText;

    private AuthUtil authUtil;

    ForgotPasswordContinuation forgotPasswordContinuation;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authUtil = new AuthUtil(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        requestPasswordButton = view.findViewById(R.id.bt_request_password);
        emailEditText = view.findViewById(R.id.et_email);
        newPasswordEditText = view.findViewById(R.id.et_new_password);
        confirmButton = view.findViewById(R.id.bt_confirm_otp);
        otpEditText = view.findViewById(R.id.et_otp);

        requestPasswordButton.setOnClickListener(v -> authUtil.RequestUserAWSCognitoPasswordReset(getContext(), emailEditText.getText().toString(), this));

        confirmButton.setOnClickListener(v -> {
            if (forgotPasswordContinuation != null) {
                forgotPasswordContinuation.setPassword(newPasswordEditText.getText().toString());
                forgotPasswordContinuation.setVerificationCode(otpEditText.getText().toString());
                forgotPasswordContinuation.continueTask();
            }
        });


        return view;
    }

    @Override
    public void getContinuation(ForgotPasswordContinuation forgotPasswordContinuation) {
        this.forgotPasswordContinuation = forgotPasswordContinuation;
    }
}