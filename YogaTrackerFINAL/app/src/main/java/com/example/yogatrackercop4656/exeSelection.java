package com.example.yogatrackercop4656;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class exeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe_selection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void playONE(View view) {
        Intent intent = new Intent(this, activityPlay.class);
        intent.putExtra("intVariableName", 1);
        startActivity(intent);
    }

    public void playTWO(View view) {
        Intent intent = new Intent(this, activityPlay.class);
        intent.putExtra("intVariableName", 2);
        startActivity(intent);
    }

    public void playTHREE(View view) {
        Intent intent = new Intent(this, activityPlay.class);
        intent.putExtra("intVariableName", 3);
        startActivity(intent);
    }

    public void questionONE(View view) {
        Intent intent = new Intent(this, help.class);
        intent.putExtra("intVariableName", 1);
        startActivity(intent);
    }

    public void questionTWO(View view) {
        Intent intent = new Intent(this, help.class);
        intent.putExtra("intVariableName", 2);
        startActivity(intent);
    }

    public void questionTHREE(View view) {
        Intent intent = new Intent(this, help.class);
        intent.putExtra("intVariableName", 3);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}