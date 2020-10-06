package com.example.aws_cognito_poc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.aws_cognito_poc.utils.AuthUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment implements AuthUtil.AuthObserver {

    private Button signInButton;

    private EditText emailEditText;
    private EditText passwordEditText;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        emailEditText = view.findViewById(R.id.et_email);
        passwordEditText = view.findViewById(R.id.et_password);
        signInButton = view.findViewById(R.id.bt_SignIn);

        signInButton.setOnClickListener(v -> {
            new AuthUtil(this).SignIn(getContext(), emailEditText.getText().toString(), passwordEditText.getText().toString());
        });

        return view;
    }
}