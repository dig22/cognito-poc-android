package com.example.aws_cognito_poc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.aws_cognito_poc.utils.AuthUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, AuthUtil.AuthObserver {

    private static final String LOG_TAG = "SignUpFragment";

    private Button signUpButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText otpEditText;
    private AuthUtil authUtil;

    Button verifyCodeButton;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //can use DI for this later
        authUtil = new AuthUtil(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpButton = view.findViewById(R.id.bt_SignUp);
        signUpButton.setOnClickListener(this);

        emailEditText = view.findViewById(R.id.et_email);
        passwordEditText = view.findViewById(R.id.et_password);
        otpEditText = view.findViewById(R.id.et_otp);

        verifyCodeButton = view.findViewById(R.id.bt_verify);

        verifyCodeButton.setOnClickListener(v -> {
            authUtil.ConfirmEmail(getContext(),
                    emailEditText.getText().toString(),
                    otpEditText.getText().toString());
        });

        return view;
    }


    @Override
    public void onClick(View v) {
        Log.i(LOG_TAG, "Clicked Somewhere!");
        if (v.getId() == R.id.bt_SignUp) {
            Log.i(LOG_TAG, "Clicked!");
            authUtil.SignUpWithAWSCognito(getContext(), emailEditText.getText().toString(),
                    passwordEditText.getText().toString());

            signUpButton.setVisibility(View.INVISIBLE);
            passwordEditText.setVisibility(View.INVISIBLE);

            otpEditText.setVisibility(View.VISIBLE);
            verifyCodeButton.setVisibility(View.VISIBLE);
            emailEditText.setEnabled(false);
        }
    }
}