package com.example.yogatrackercop4656;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class statSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_selection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void statView(View view) {
        Intent intent = new Intent(this, statView.class);
        intent.putExtra("intVariableName", 1);
        startActivity(intent);
    }


    public void statView2(View view) {
        Intent intent = new Intent(this, statView.class);
        intent.putExtra("intVariableName", 2);
        startActivity(intent);
    }


    public void statView3(View view) {
        Intent intent = new Intent(this, statView.class);
        intent.putExtra("intVariableName", 3);
        startActivity(intent);
    }

    public void setGoals(View view) {
        Intent intent = new Intent(this, setGoals.class);
        intent.putExtra("intVariableName", 1);
        startActivity(intent);
    }

    public void setGoals2(View view) {
        Intent intent = new Intent(this, setGoals.class);
        intent.putExtra("intVariableName", 2);
        startActivity(intent);
    }

    public void setGoals3(View view) {
        Intent intent = new Intent(this, setGoals.class);
        intent.putExtra("intVariableName", 3);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}