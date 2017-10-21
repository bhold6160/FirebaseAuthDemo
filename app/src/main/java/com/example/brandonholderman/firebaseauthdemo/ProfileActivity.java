package com.example.brandonholderman.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "Profile Activity";

    @BindView(R.id.profile_textview)
    TextView mProfileTextView;
    @BindView(R.id.logout_button)
    Button mLogoutButton;
    @BindView(R.id.edit_text_name)
    EditText mEditName;
    @BindView(R.id.edit_text_address)
    EditText mEditAddress;
    @BindView(R.id.save_button)
    Button mSaveButton;

    private FirebaseUser mUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mProfileTextView.setText("Welcome " + mUser.getEmail());
    }

    @OnClick(R.id.save_button)
    public void saveUserInformation(View view) {
        String name = mEditName.getText().toString().trim();
        String address = mEditAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, address);

        mDatabaseReference.child(mUser.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.logout_button)
    public void logout(View view) {
        mFirebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
