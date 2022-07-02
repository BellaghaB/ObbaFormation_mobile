package com.example.courseactivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt, passwordEdt;
    private Button loginBtn;
    private TextView newUserTV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        newUserTV = findViewById(R.id.idTVNewUser);
        mAuth = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);
        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open regisration activity
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        // event for login button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiding the progress bar.
                loadingPB.setVisibility(View.VISIBLE);
                // recuparing data email and password
                String email = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                // check if the email and password are not empty
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "fields are empty..", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // check if the task is ok or not.
                        if (task.isSuccessful()) {
                            // hide the progress bar.
                            loadingPB.setVisibility(View.GONE);
                            // message for successfully login
                            Toast.makeText(LoginActivity.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                            // open main activity
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // hide the progress bar
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check if the user is already sign in or not
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // open a main activity
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}
