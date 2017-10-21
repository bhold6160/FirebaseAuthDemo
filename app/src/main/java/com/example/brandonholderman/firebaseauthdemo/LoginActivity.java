package com.example.brandonholderman.firebaseauthdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.user_email) EditText mEmail;
    @BindView(R.id.user_password) EditText mPassword;
    @BindView(R.id.returning_user) Button mReturningUserButton;
    @BindView(R.id.sign_up) TextView mSignUp;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

//        if (mFirebaseAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//        }

        mReturningUserButton.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
    }

    private void userLogin() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (mFirebaseAuth.getCurrentUser() != null) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mReturningUserButton) {
            userLogin();
        }

        if (view == mSignUp) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
