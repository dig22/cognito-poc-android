package com.example.aws_cognito_poc;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    Fragment[] array = {new SignInFragment(), new SignInFragment(), new ForgotPasswordFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Log.i(LOG_TAG, "Clicked! with " + menuItem.getItemId());

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_item_signup:
                    fragmentTransaction.replace(R.id.nav_host_fragment, new SignUpFragment());
                    break;
                case R.id.menu_item_signin:
                    fragmentTransaction.replace(R.id.nav_host_fragment, new SignInFragment());
                    break;
                case R.id.menu_item_req_new_password_otp:
                    fragmentTransaction.replace(R.id.nav_host_fragment, new ForgotPasswordFragment());
                    break;
            }
            fragmentTransaction.commit();
            return true;
        });
    }
}