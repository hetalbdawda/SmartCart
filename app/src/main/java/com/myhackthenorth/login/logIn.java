package com.myhackthenorth.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logIn extends AppCompatActivity {
    public EditText emailId, password;
    Button buttonSignIn;
    TextView tvSignUp;
    FirebaseAuth myFirebaseAuth;
    private FirebaseAuth.AuthStateListener myAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        myFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        buttonSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        myAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser myFirebaseUser = myFirebaseAuth.getCurrentUser();
                if (myFirebaseUser != null) {
                    Toast.makeText(logIn.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(logIn.this, home_Screen.class);
                    startActivity(i);

                }
                else {
                    Toast.makeText(logIn.this, "Please Login.", Toast.LENGTH_SHORT).show();

                }
            }
        };
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String PWD = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter your email.");
                    emailId.requestFocus();
                } else if (PWD.isEmpty()) {
                    password.setError("Please enter a password.");
                    password.requestFocus();
                } else if (email.isEmpty() && PWD.isEmpty()) {
                    Toast.makeText(logIn.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && PWD.isEmpty())) {
                    myFirebaseAuth.signInWithEmailAndPassword(email, PWD).addOnCompleteListener(logIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {                    Toast.makeText(logIn.this, "Error Occurred!.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(logIn.this, "Login error occured, please try again.!.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intToHome = new Intent(logIn.this, home_Screen.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(logIn.this, "Error Occurred!.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(logIn.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myFirebaseAuth.addAuthStateListener(myAuthStateListener);
    }
}
