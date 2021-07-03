package com.example.yogatrackercop4656;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER  = "user";
    private static final String TAG = "RegisterActivity";
    private User user;

    private ProgressBar progressBar;
    private EditText editTextEmail, editTextPassword, editTextConfirmPass;
    private TextView alreadyRegistered, registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        alreadyRegistered = (TextView)findViewById(R.id.alreadyregistered);
        alreadyRegistered.setOnClickListener(this);

        registerUser = (Button)findViewById(R.id.btnRegister);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.inputEmail);
        editTextPassword = (EditText)findViewById(R.id.inputPassword);
        editTextConfirmPass = (EditText)findViewById(R.id.inputConfirmPass);

        progressBar = (ProgressBar)findViewById(R.id.progressBarpb);
    }

    public void back(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.alreadyregistered:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnRegister:
                Register();
                break;
        }
    }

    private void Register(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confPass = editTextConfirmPass.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6)
        {
            editTextPassword.setError("Password should be atleast 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(confPass.isEmpty())
        {
            editTextConfirmPass.setError("Retype Password");
            editTextConfirmPass.requestFocus();
            return;
        }

        if(!password.toString().equals(confPass.toString()))
        {
            editTextConfirmPass.setError("Passwords do not match");
            editTextConfirmPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user , email, password);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again later!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }

    private void updateUI(FirebaseUser currentUser, String email, String password) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String keyId = FirebaseAuth.getInstance().getUid();
        User user  = new User(email, password);
        mDatabase.child(keyId).setValue(user);
        Intent newIntent  = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }
}