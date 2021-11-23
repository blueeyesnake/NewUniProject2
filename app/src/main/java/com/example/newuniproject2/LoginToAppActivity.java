package com.example.newuniproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginToAppActivity extends AppCompatActivity {
    private Button mLogin;
    private EditText mEmail, mPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_to_app);
        mAuth = FirebaseAuth.getInstance();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // checks firebase to see if user is authorized, then allows them into the main activity.
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(LoginToAppActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mLogin = (Button) findViewById(R.id.login);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creates a new user when registration button is clicked
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginToAppActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // checks if the user creation was successful, reports error if not
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginToAppActivity.this, "there was an error registering", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }

    // allows program to communicate with firebase, and closes communication when done
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}