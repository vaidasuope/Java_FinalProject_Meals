package com.meals.foodb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    /*EditText registerUsername, registerPassword, registerEmail;
    Button registerButton2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton2 = (Button) findViewById(R.id.registerButton2);

        final EditText registerUsername = (EditText) findViewById(R.id.registerUsername);
        final EditText registerPassword = (EditText) findViewById(R.id.registerPassword);
        final EditText registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Validation.isValidUsername(registerUsername.getText().toString())) {
                    registerUsername.setError(getResources().getString(R.string.login_invalid_username));
                    registerUsername.requestFocus();
                }
                else if (!Validation.isValidPassword(registerPassword.getText().toString())) {
                    registerPassword.setError(getResources().getString(R.string.login_invalid_username));
                    registerPassword.requestFocus();
                }
                else if (!Validation.isValidEmail(registerEmail.getText().toString())) {
                    registerEmail.setError(getResources().getString(R.string.invalid_email));
                    registerEmail.requestFocus();
                }
                else {
                    Intent goToLoginActivity = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(goToLoginActivity);

                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.new_user),
                            Toast.LENGTH_SHORT).show();
                    //konstruojamas objektas
                    //public User(String username, String password, String email)
                    User user=new User(registerUsername.getText().toString(),registerPassword.getText().toString(),registerEmail.getText().toString());
                    Toast.makeText(RegisterActivity.this,
                            "Username:"+ user.getUsernameForRegistration()+"\n"+
                                    "Password:"+user.getPasswordForRegistration()+"\n"+
                                    "Email:"+user.getEmailForRegistration(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}