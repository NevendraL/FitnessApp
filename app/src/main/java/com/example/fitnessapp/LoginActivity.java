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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmailEditText;
    private EditText userPasswordEditext;
    private UserModel userModel;
    private Button loginButton;
    private TextView createAccount;
    private FirebaseAuth mAuth;
    private TextView userFeedBackTextView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailEditText = findViewById(R.id.userEmailEditText);
        userPasswordEditext = findViewById(R.id.userPasswordEditText);
        loginButton = findViewById(R.id.loginButton);
        userModel = new UserModel();
        createAccount = findViewById(R.id.createAccountTextView);
        userFeedBackTextView = findViewById(R.id.userFeedBack);
        mAuth = FirebaseAuth.getInstance();







        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

    }

    //check to see if the user input information is valid
    public void checkCredentials(){
        if(userEmailEditText.getText().toString().equals("") || userPasswordEditext.getText().toString().equals("")){
            userFeedBackTextView.setText(getString(R.string.input_information));
        }else{
            progressDialog = ProgressDialog.show(LoginActivity.this, "",
                    "Signing in, please wait...", true);
            loginUser();
        }
    }

    public void loginUser(){
        userModel.setUserEmail(userEmailEditText.getText().toString());
        userModel.setUserPassword(userPasswordEditext.getText().toString());
        //populate userModel with the user's email and password..

        //Sign user in with email and password stored in user mode..
        mAuth.signInWithEmailAndPassword(userModel.getUserEmail(), userModel.getUserPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else {
                            //If user sign in fails.
                            progressDialog.dismiss();
                            userFeedBackTextView.setText(getString(R.string.incorrect_details));
                        }

                        // ...
                    }
                });

    }





    }

