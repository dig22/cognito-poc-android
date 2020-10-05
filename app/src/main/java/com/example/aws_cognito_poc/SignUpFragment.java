package com.example.aws_cognito_poc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.aws_cognito_poc.utils.AuthUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, AuthUtil.AuthObserver {

    private Button signUpButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText otpEditText;
    private AuthUtil authUtil;

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
        view.setOnClickListener(this);
        signUpButton = view.findViewById(R.id.bt_SignUp);
        emailEditText = view.findViewById(R.id.et_email);
        passwordEditText = view.findViewById(R.id.et_password);
        otpEditText = view.findViewById(R.id.et_otp);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_SignUp) {
            authUtil.SignUpWithAWSCognito(emailEditText.getText().toString(),
                    passwordEditText.getText().toString());
        }
    }
}