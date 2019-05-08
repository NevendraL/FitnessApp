package com.example.fitnessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAccount;
    private FirebaseAuth mAuth;
    private UserModel userModel;
    private TextView loginTextView;
    private TextView welcomeTextView;
    private TextView feedBackTextView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        createAccount = findViewById(R.id.createAccountButton);
        userModel = new UserModel();
        loginTextView = findViewById(R.id.loginTextView);
        feedBackTextView = findViewById(R.id.feedbackTextView);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserCredentials();

            }
        });
    }


    public void checkUserCredentials() {
        userModel.setUserEmail(emailEditText.getText().toString());
        userModel.setUserPassword(passwordEditText.getText().toString());
        //populate userModel with the user's email and password..

        //checks user info to see if it follows proper guidelines if it does call the create user method
        if (userModel.getUserEmail() == "" || userModel.getUserPassword() == "") {
            feedBackTextView.setText(getString(R.string.input_required_information));
        } else if (userModel.getUserPassword().length() < 7) {
            feedBackTextView.setText(getString(R.string.weak_password));
        } else if (!userModel.getUserEmail().contains("@")) {
            feedBackTextView.setText(getString(R.string.use_valid_email));
        } else {
            progressDialog = ProgressDialog.show(RegistrationActivity.this, "",
                    "Creating account, please wait...", true);
            createUser();
        }
    }

    public void createUser() {
        //gets the users's email and password and creates an account based on that..
        mAuth.createUserWithEmailAndPassword(userModel.getUserEmail(), userModel.getUserPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistrationActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            feedBackTextView.setText(getString(R.string.account_failed));

                        }

                        // ...
                    }
                });
    }

    //takes the user to login screen..
    public void login(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

