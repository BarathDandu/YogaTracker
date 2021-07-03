package com.example.yogatrackercop4656;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button logoutUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        logoutUser = (Button)findViewById(R.id.btnLogout);
        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

    public void exeSelection(View view) {
        Intent intent = new Intent(this, exeSelection.class);
        startActivity(intent);
    }

    public void statSelection(View view) {
        Intent intent = new Intent(this, statSelection.class);
        startActivity(intent);
    }
}