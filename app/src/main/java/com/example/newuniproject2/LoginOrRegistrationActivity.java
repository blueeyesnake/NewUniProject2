package com.example.newuniproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOrRegistrationActivity extends AppCompatActivity {
    private Button mLogin, mRegister;
    @Override
            protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login_or_registration);

                mLogin = (Button) findViewById(R.id.login);
                mRegister = (Button) findViewById(R.id.register);

                mLogin.setOnClickListener(new View.OnClickListener(){
                    @Override
                            public void onClick(View view){
                                Intent intent = new Intent(LoginOrRegistrationActivity.this, LoginToAppActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                }
            });
                mRegister.setOnClickListener(new View.OnClickListener(){
                    @Override
                            public void onClick(View view){
                                Intent intent = new Intent(LoginOrRegistrationActivity.this, RegistrationPageActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                }
            });
        }

}
