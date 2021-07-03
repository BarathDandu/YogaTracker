package com.example.yogatrackercop4656;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.CountDownTimer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class activityPlay extends AppCompatActivity implements SensorEventListener {

    //Timer variables
    private int counter;
    CountDownTimer timer;
    private int TIME;
    TextView timerText;
    private int counterStore;

    //sensor storing variables
    private float sensorCount = 0;
    private float sensorGood = 0;
    private float sensorStability;

    DatabaseReference ref;
    ArrayList<String> pastDate;
    ArrayList<String> pastSec;
    ArrayList<String> goalDate;
    ArrayList<String> goalSec;
    ArrayList<String> sensorDate;
    ArrayList<String> sensorAccuracy;

    // System sensor manager instance.
    private SensorManager mSensorManager;

    // Accelerometer and magnetometer as retrieved from the sensor manager.
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    private float[] mAccelerometerData = new float[3];
    private float[] mMagnetometerData = new float[3];

    // Very small values for the accelerometer (on all three axes) should
    // be interpreted as 0. This value is the amount of acceptable
    // non-zero drift.
    private static final float VALUE_DRIFT = 0.05f;
    private int select, go = 0;

    //Sets activity background color
    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    //Goes to exercise selection activity
    public void exeSelection(Intent view) {
        Intent intent = new Intent(this, exeSelection.class);
        startActivity(intent);
    }

    //Exit button goes to exercise selection activity
    public void exitActivity(View view) {
        Intent intent = new Intent(this, exeSelection.class);
        startActivity(intent);
    }

    //endExercise signifies what happens after the exercise is officially completed
    //by time instead of by pause, it should take the sensor stability and timer variables
    //and store them in firebase since no more sensor readings will occur
    public void endExercise() {
        timer.cancel();
        mSensorManager.unregisterListener(this);
        setActivityBackgroundColor(Color.GREEN);

        //Counter and stability variables to store
        //sensorStability is *100 so that it is a percentage
        counterStore = counter;
        sensorStability = Math.round(sensorGood / sensorCount * 100);

        //Reset activity variables
        go = 0;
        select = 0;
        counter = 0;
        sensorGood = 0;
        sensorCount = 0;

        String date = new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss", Locale.getDefault()).format(new Date());
        String date2 = new SimpleDateFormat("MM-dd-yyyy-HH:mm", Locale.getDefault()).format(new Date());

        HashMap<String, Object> map = new HashMap<>();
        map.put(date, counter);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(date2, sensorStability);

        String keyId = FirebaseAuth.getInstance().getUid();

        Intent intent = getIntent();
        int act = intent.getIntExtra("intVariableName", 0);

        switch (act) {
            case 1:
                ImageView acOn = (ImageView) findViewById(R.id.actOne);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise1").child("pastTimes").updateChildren(map);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise1").child("sensorStability").updateChildren(map2);
                break;
            case 2:
                ImageView acTw = (ImageView) findViewById(R.id.actTwo);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise2").child("pastTimes").updateChildren(map);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise2").child("sensorStability").updateChildren(map2);
                break;
            case 3:
                ImageView acTh = (ImageView) findViewById(R.id.actThree);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise3").child("pastTimes").updateChildren(map);
                FirebaseDatabase.getInstance().getReference().child(keyId).child("exercise3").child("sensorStability").updateChildren(map2);
                break;
            default:
                break;
        }

        //reset visibilities for exit
        ImageView v = findViewById(R.id.actOne);
        TextView t = findViewById(R.id.timerText);
        ImageView exit = findViewById(R.id.exitActivity);
        exit.setVisibility(View.VISIBLE);
        v.setVisibility(View.GONE);
        t.setVisibility(View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        pastDate = new ArrayList<>();
        pastSec = new ArrayList<>();
        goalDate = new ArrayList<>();
        goalSec = new ArrayList<>();
        sensorDate = new ArrayList<>();
        sensorAccuracy = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());

        Intent intent = getIntent();
        int act = intent.getIntExtra("intVariableName", 0);

        switch (act) {
            case 1:
                ImageView acOn = findViewById(R.id.actOne);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pastDate.clear();
                        pastSec.clear();
                        goalDate.clear();
                        goalSec.clear();
                        sensorDate.clear();
                        sensorAccuracy.clear();
                        DataSnapshot pastTime = snapshot.child("exercise1").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren = pastTime.getChildren();
                        for (DataSnapshot i : pastTimeChildren) {
                            pastDate.add(i.getKey());
                            pastSec.add(i.getValue().toString());
                            Log.d("pastdate", i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        DataSnapshot goal = snapshot.child("exercise1").child("goal");
                        Iterable<DataSnapshot> goalChildren = goal.getChildren();
                        for (DataSnapshot i : goalChildren) {
                            goalDate.add(i.getKey());
                            goalSec.add(i.getValue().toString());
                            Log.d("goaldate", i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        //If no time goal is set, the TIME value is 10 seconds by default
                        goalSec.add("10");
                        Log.d("time", String.valueOf(TIME));

                        TIME = Integer.parseInt(goalSec.get(0));
                        TIME = TIME * 1000;
                        if (TIME == 0) {
                            TIME = 10000;
                        }

                        //Creates a timer that shows on activity screen and updates itself accordingly
                        //using the counter variable
                        TextView t = findViewById(R.id.timerText);
                        timer = new CountDownTimer(TIME, 1000) {
                            public void onTick(long millisUntilFinished) {
                                t.setText(String.valueOf(counter));
                                counter++;
                            }

                            @SuppressLint("SetTextI18n")
                            public void onFinish() {
                                t.setText("FINISH!!");
                                endExercise();
                            }
                        };

                        DataSnapshot sens = snapshot.child("exercise1").child("sensorStability");
                        Iterable<DataSnapshot> sensChildren = sens.getChildren();
                        for (DataSnapshot i : sensChildren) {
                            sensorDate.add(i.getKey());
                            sensorAccuracy.add(i.getValue().toString());
                            Log.d("sensordate", i.getKey());
                            Log.d("accuracy", i.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;
            case 2:
                ImageView acTw = findViewById(R.id.actTwo);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pastDate.clear();
                        pastSec.clear();
                        goalDate.clear();
                        goalSec.clear();
                        sensorDate.clear();
                        sensorAccuracy.clear();
                        DataSnapshot pastTime = snapshot.child("exercise2").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren = pastTime.getChildren();
                        for (DataSnapshot i : pastTimeChildren) {
                            pastDate.add(i.getKey());
                            pastSec.add(i.getValue().toString());
                            Log.d("pastdate", i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot goal = snapshot.child("exercise2").child("goal");
                        Iterable<DataSnapshot> goalChildren = goal.getChildren();
                        for (DataSnapshot i : goalChildren) {
                            goalDate.add(i.getKey());
                            goalSec.add(i.getValue().toString());
                            Log.d("goaldate", i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        goalSec.add("10");
                        TIME = Integer.parseInt(goalSec.get(0));
                        TIME = TIME * 1000;
                        if (TIME == 0) {
                            TIME = 10000;
                        }

                        //Creates a timer that shows on activity screen and updates itself accordingly
                        //using the counter variable
                        TextView t = findViewById(R.id.timerText);
                        timer = new CountDownTimer(TIME, 1000) {
                            public void onTick(long millisUntilFinished) {
                                t.setText(String.valueOf(counter));
                                counter++;
                            }

                            @SuppressLint("SetTextI18n")
                            public void onFinish() {
                                t.setText("FINISH!!");
                                endExercise();
                            }
                        };

                        DataSnapshot sens = snapshot.child("exercise2").child("sensorStability");
                        Iterable<DataSnapshot> sensChildren = sens.getChildren();
                        for (DataSnapshot i : sensChildren) {
                            sensorDate.add(i.getKey());
                            sensorAccuracy.add(i.getValue().toString());
                            Log.d("sensordate", i.getKey());
                            Log.d("accuracy", i.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;
            case 3:
                ImageView acTh = findViewById(R.id.actThree);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pastDate.clear();
                        pastSec.clear();
                        goalDate.clear();
                        goalSec.clear();
                        sensorDate.clear();
                        sensorAccuracy.clear();
                        DataSnapshot pastTime = snapshot.child("exercise3").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren = pastTime.getChildren();
                        for (DataSnapshot i : pastTimeChildren) {
                            pastDate.add(i.getKey());
                            pastSec.add(i.getValue().toString());
                            Log.d("pastdate", i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot goal = snapshot.child("exercise3").child("goal");
                        Iterable<DataSnapshot> goalChildren = goal.getChildren();
                        for (DataSnapshot i : goalChildren) {
                            goalDate.add(i.getKey());
                            goalSec.add(i.getValue().toString());
                            Log.d("goaldate", i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        goalSec.add("10");
                        TIME = Integer.parseInt((goalSec.get(0)));
                        TIME = TIME * 1000;
                        if (TIME == 0) {
                            TIME = 10000;
                        }

                        //Creates a timer that shows on activity screen and updates itself accordingly
                        //using the counter variable
                        TextView t = findViewById(R.id.timerText);
                        timer = new CountDownTimer(TIME, 1000) {
                            public void onTick(long millisUntilFinished) {
                                t.setText(String.valueOf(counter));
                                counter++;
                            }

                            @SuppressLint("SetTextI18n")
                            public void onFinish() {
                                t.setText("FINISH!!");
                                endExercise();
                            }
                        };


                        DataSnapshot sens = snapshot.child("exercise3").child("sensorStability");
                        Iterable<DataSnapshot> sensChildren = sens.getChildren();
                        for (DataSnapshot i : sensChildren) {
                            sensorDate.add(i.getKey());
                            sensorAccuracy.add(i.getValue().toString());
                            Log.d("sensordate", i.getKey());
                            Log.d("accuracy", i.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;
            default:
                break;
        }


        ImageView v = findViewById(R.id.actOne);
        ImageView exit = findViewById(R.id.exitActivity);

        exit.setVisibility(View.GONE);
        v.setVisibility(View.GONE);

        // Lock the orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setActivityBackgroundColor(Color.WHITE);

        // Get accelerometer and magnetometer sensors from the sensor manager.
        // The getDefaultSensor() method returns null if the sensor
        // is not available on the device.
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);
    }


    public void playThis(View view) {
        Intent intent = getIntent();
        int act = intent.getIntExtra("intVariableName", 0);
        TextView fun = findViewById(R.id.pressPlay);

        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL).
        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            mSensorManager.registerListener(this, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        TextView t = findViewById(R.id.timerText);
        t.setVisibility(View.VISIBLE);


        switch (act) {
            case 1:
                ImageView acOn = findViewById(R.id.actOne);
                acOn.setVisibility(View.VISIBLE);
                fun.setVisibility(View.GONE);
                select = 1;
                break;
            case 2:
                ImageView acTw = findViewById(R.id.actTwo);
                acTw.setVisibility(View.VISIBLE);
                fun.setVisibility(View.GONE);
                select = 2;
                break;
            case 3:
                ImageView acTh = findViewById(R.id.actThree);
                acTh.setVisibility(View.VISIBLE);
                fun.setVisibility(View.GONE);
                select = 3;
                timer.start();
                break;
            default:
                break;
        }
    }

    public void stopThis(View view) {
        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is stopped.
        mSensorManager.unregisterListener(this);
        setActivityBackgroundColor(Color.WHITE);
        go = 0;
        select = 0;
        counter = 0;
        sensorGood = 0;
        sensorCount = 0;
        timer.cancel();

        Intent intent = getIntent();
        exeSelection(intent);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();

        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerometerData = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetometerData = sensorEvent.values.clone();
                break;
            default:
                return;
        }


        //Gets accelerometer and magnetometer data to transform into orienation matrix
        float[] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,
                null, mAccelerometerData, mMagnetometerData);

        float[] orientationValues = new float[3];
        if (rotationOK) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);
        }

        //float azimuth = orientationValues[0];
        float pitch = orientationValues[1];
        float roll = orientationValues[2];

        //Use a high-pass filter to exclude the force of gravity on acceleration
        final float alpha = (float) 0.8;
        float[] gravity = new float[3];
        float[] linear_acceleration = new float[3];

        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        if (Math.abs(pitch) < VALUE_DRIFT) {
            pitch = 0;
        }
        if (Math.abs(roll) < VALUE_DRIFT) {
            roll = 0;
        }
        if (Math.abs(linear_acceleration[1]) < VALUE_DRIFT) {
            linear_acceleration[1] = 0;
        }

        //Arm movement validation
        //Skips orientation readings until accelerometer senses certain threshold
        if (go == 0 && select != 3) {
            if (linear_acceleration[1] > 7) {
                go = 1;
                timer.start();
            }
        }

        if (go == 1 || select == 3) {
            if (select == 1) {
                if (pitch <= -0.1 && pitch >= -0.6 &&
                        ((roll <= -2.50 && roll >= -3.14) || (roll <= 3.14 && roll >= 2.50))) {
                    //Instance of a good sensor reading,
                    //this will stop all poor sensor processes
                    setActivityBackgroundColor(Color.WHITE);
                    sensorGood++;
                } else {
                    //Instance of a bad sensor reading,
                    //this will trigger all poor sensor processes
                    setActivityBackgroundColor(Color.RED);
                }
                sensorCount++;
            } else if (select == 2) {
                if (pitch <= -0.1 && pitch >= -0.6 &&
                        ((roll <= -2.51 && roll >= -3.14) || (roll <= 3.14 && roll >= 2.50))) {
                    setActivityBackgroundColor(Color.WHITE);
                    sensorGood++;
                } else {
                    setActivityBackgroundColor(Color.RED);
                }
                sensorCount++;
            } else if (select == 3) {
                if (pitch <= -1.40 && pitch >= -1.55) {
                    setActivityBackgroundColor(Color.WHITE);
                    sensorGood++;
                } else {
                    setActivityBackgroundColor(Color.RED);
                }
                sensorCount++;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
