package com.example.yogatrackercop4656;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class setGoals extends AppCompatActivity implements View.OnClickListener{
    private TextView btnAdd;
    private int naval;
    private CalendarView mCalanderView;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAdd = (Button)findViewById(R.id.addBtn);
        btnAdd.setOnClickListener(this);
        mCalanderView = (CalendarView)findViewById(R.id.calendarView);
        mCalanderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (month+1) + "-" + dayOfMonth + "-" + year;
            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(this, statSelection.class);
        startActivity(intent);
    }

    public void numAdd(View view) {
        TextView timerVal = (TextView)findViewById(R.id.timerVal);

        naval = Integer.parseInt(timerVal.getText().toString());

        naval = naval + 5;
        timerVal.setText( Integer.toString(naval));


    }

    public void numSub(View view) {
        TextView timerVal = (TextView)findViewById(R.id.timerVal);

        naval = Integer.parseInt(timerVal.getText().toString());
        if(naval !=0 )
            naval = naval - 5;
        timerVal.setText( Integer.toString(naval));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addBtn:
                Add(naval);
                break;
        }
    }

    private void Add( int num) {
        HashMap<String , Object> map = new HashMap<>();
        map.put(date, num);

        String keyId = FirebaseAuth.getInstance().getUid();

        Intent intent = getIntent();
        int act = intent.getIntExtra("intVariableName", 0);

        switch (act) {
            case 1:
                ImageView acOn = (ImageView) findViewById(R.id.setGoal);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise1").child("goal").updateChildren(map);
                this.onBackPressed();
                break;
            case 2:
                ImageView acTw = (ImageView) findViewById(R.id.setGoal2);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise2").child("goal").updateChildren(map);
                this.onBackPressed();
                break;
            case 3:
                ImageView acTh = (ImageView) findViewById(R.id.setGoal3);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise3").child("goal").updateChildren(map);
                this.onBackPressed();
                break;
            default:
                break;
        }
    }
}