package com.example.brandonholderman.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.user_email) EditText mEmailAddress;
    @BindView(R.id.user_password) EditText mPassword;
    @BindView(R.id.register_user) Button mRegisterButton;
    @BindView(R.id.sign_in) TextView mSignIn;

    private FirebaseAuth mFirebaseAuth;

    private TextView mLoadingText;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        mLoadingText = (TextView) findViewById(R.id.loading_complete);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mRegisterButton.setOnClickListener(this);
        mSignIn.setOnClickListener(this);

    }

//    @OnClick(R.id.register_user)
//    public void registerUser() {
//
//    }

    private void registerUser() {
        String email = mEmailAddress.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT);
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (mFirebaseAuth.getCurrentUser() != null) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                    Toast.makeText(MainActivity.this, "Registration was Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Registration Was Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == mRegisterButton) {
            registerUser();
        }

        if (view == mSignIn) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

//    public void loadBar() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (mProgressStatus < 100) {
//                    mProgressStatus++;
//                    android.os.SystemClock.sleep(50);
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mProgressBar.setProgress(mProgressStatus);
//                        }
//                    });
//                }
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLoadingText.setVisibility(View.VISIBLE);
//                    }
//                });
//            }
//        }).start();
//    }
}
