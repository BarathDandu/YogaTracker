package com.example.yogatrackercop4656;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.icu.number.IntegerWidth;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class statView extends AppCompatActivity {
    LineChartView lineChartView;
    PieChartView pieChartView;
    DatabaseReference ref;
    ArrayList<String> sensorDate;
    ArrayList<String> sensorAccuracy;
    ArrayList<String> pastDate;
    ArrayList<String> pastSec;
    ArrayList<String> goalDate;
    ArrayList<String> goalSec;
    ArrayList<String> pastDate2;
    ArrayList<String> pastSec2;
    ArrayList<String> pastDate3;
    ArrayList<String> pastSec3;
    int ex1, ex2, ex3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorDate = new ArrayList<>();
        sensorAccuracy = new ArrayList<>();
        pastDate = new ArrayList<>();
        pastSec = new ArrayList<>();
        goalDate = new ArrayList<>();
        goalSec = new ArrayList<>();
        pastDate2 = new ArrayList<>();
        pastSec2 = new ArrayList<>();
        pastDate3 = new ArrayList<>();
        pastSec3 = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());

        TextView goalTime = (TextView)findViewById(R.id.currentGoal);
        goalTime.setText("");

        Intent intent = getIntent();
        int zed = intent.getIntExtra("intVariableName", 0);

        String[] axisData = {"0", "0", "0", "0", "0", "0", "0", "0",
                "0", "0", "0", "0"};
        int[] yAxisData = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        lineChartView = findViewById(R.id.chart);
        pieChartView = findViewById(R.id.chart2);


        switch (zed) {
            case 1:
                String[] cse1 = {"1", "2", "3", "4", "5", "6", "7", "8",
                        "9", "10", "11", "12"};
                System.arraycopy(cse1, 0, axisData, 0, axisData.length);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pastDate.clear();
                        pastSec.clear();
                        goalDate.clear();
                        goalSec.clear();
                        pastDate2.clear();
                        pastSec2.clear();
                        pastDate3.clear();
                        pastSec3.clear();
                        sensorDate.clear();
                        sensorAccuracy.clear();
                        float accavg = 0;

                        DataSnapshot sens = snapshot.child("exercise1").child("sensorStability");
                        Iterable<DataSnapshot> sensChildren = sens.getChildren();
                        for(DataSnapshot i: sensChildren)
                        {
                            sensorDate.add(i.getKey());
                            sensorAccuracy.add(i.getValue().toString());
                            accavg = accavg + (float) Integer.parseInt(i.getValue().toString());
                            Log.d("sensordate",  i.getKey());
                            Log.d("accuracy", i.getValue().toString());
                        }

                        if(sensorAccuracy.size() == 0){
                            accavg = 0;
                        }
                        else
                            accavg = accavg / (float) sensorAccuracy.size();

                        DataSnapshot pastTime = snapshot.child("exercise1").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren = pastTime.getChildren();
                        for(DataSnapshot i: pastTimeChildren)
                        {
                            pastDate.add(i.getKey());
                            pastSec.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot pastTime2 = snapshot.child("exercise2").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren2 = pastTime2.getChildren();
                        for(DataSnapshot i: pastTimeChildren2)
                        {
                            pastDate2.add(i.getKey());
                            pastSec2.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot pastTime3 = snapshot.child("exercise3").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren3 = pastTime3.getChildren();
                        for(DataSnapshot i: pastTimeChildren3)
                        {
                            pastDate3.add(i.getKey());
                            pastSec3.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        if(pastSec.size() == 0){
                            pastSec.add("0");
                            ex1 = 0;
                        }
                        else
                            ex1 = pastSec.size();

                        if(pastSec2.size() == 0){
                            pastSec2.add("0");
                            ex2 = 0;
                        }
                        else
                            ex2 = pastSec2.size();

                        if(pastSec3.size() == 0){
                            pastSec3.add("0");
                            ex3 = 0;
                        }
                        else
                            ex3 = pastSec3.size();



                        List pieData = new ArrayList<>();
                        pieData.add(new SliceValue(ex1, Color.BLUE).setLabel("Right"));
                        pieData.add(new SliceValue(ex2, Color.GRAY).setLabel("Left"));
                        pieData.add(new SliceValue(ex3, Color.RED).setLabel("Chest"));

                        TextView totTime = (TextView)findViewById(R.id.totaltimes);
                        TextView perce = (TextView)findViewById(R.id.percenT);
                        TextView goalTime = (TextView)findViewById(R.id.currentGoal);
                        TextView accLat = (TextView)findViewById(R.id.latest);
                        TextView accAvg = (TextView)findViewById(R.id.average);

                        accAvg.setText(String.format("%.0f",(accavg)) + " %");
                        if(sensorAccuracy.size() == 0){
                            accLat.setText("0");
                        }
                        else
                            accLat.setText(sensorAccuracy.get(sensorAccuracy.size()-1) + " %");
                        totTime.setText( Integer.toString(ex1));
                        perce.setText( Float.toString(Math.round (100 * (float)ex1/((ex1+ex2+ex3)))) + " %");


                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(12);
                        pieChartView.setPieChartData(pieChartData);


                        DataSnapshot goal = snapshot.child("exercise1").child("goal");
                        Iterable<DataSnapshot> goalChildren = goal.getChildren();
                        for(DataSnapshot i: goalChildren)
                        {
                            goalDate.add(i.getKey());
                            goalSec.add(i.getValue().toString());
                            Log.d("goaldate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }


                        if(goalSec.size() == 0){
                            goalSec.add("0");
                        }
                        else
                            goalTime.setText(goalSec.get(0) + " (s)");

                        //goalTime.setText(goalSec.get(0) + " (s)");
                        lineChartView = findViewById(R.id.chart);

                        List yAxisValues =  new ArrayList();
                        List axisValues = new ArrayList();


                        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

                        for (int i = 0; i < axisData.length; i++) {
                            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                        }

                        if(sensorAccuracy.size() > 0){
                            for (int i = 0; i < sensorAccuracy.size(); i++) {
                                yAxisValues.add(new PointValue(i,Integer.parseInt(sensorAccuracy.get(i)) ));
                            }}


                        List lines = new ArrayList();
                        lines.add(line);

                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        Axis axis = new Axis();
                        axis.setValues(axisValues);
                        axis.setTextSize(16);
                        axis.setTextColor(Color.parseColor("#03A9F4"));
                        data.setAxisXBottom(axis);

                        Axis yAxis = new Axis();
                        yAxis.setName("Sensor Accuracy in %");
                        yAxis.setTextColor(Color.parseColor("#03A9F4"));
                        yAxis.setTextSize(15);
                        data.setAxisYLeft(yAxis);

                        lineChartView.setLineChartData(data);
                        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                        viewport.top = 100;
                        lineChartView.setMaximumViewport(viewport);
                        lineChartView.setCurrentViewport(viewport);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;
            case 2:
                String[] cse2 = {"1", "2", "3", "4", "5", "6", "7", "8",
                        "9", "10", "11", "12"};
                System.arraycopy(cse2, 0, axisData, 0, axisData.length);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pastDate.clear();
                        pastSec.clear();
                        goalDate.clear();
                        goalSec.clear();
                        pastDate2.clear();
                        pastSec2.clear();
                        pastDate3.clear();
                        pastSec3.clear();
                        sensorDate.clear();
                        sensorAccuracy.clear();
                        float accavg = 0;

                        DataSnapshot sens = snapshot.child("exercise2").child("sensorStability");
                        Iterable<DataSnapshot> sensChildren = sens.getChildren();
                        for(DataSnapshot i: sensChildren)
                        {
                            sensorDate.add(i.getKey());
                            sensorAccuracy.add(i.getValue().toString());
                            accavg = accavg + (float) Integer.parseInt(i.getValue().toString());
                            Log.d("sensordate",  i.getKey());
                            Log.d("accuracy", i.getValue().toString());
                        }

                        if(sensorAccuracy.size() == 0){
                            accavg = 0;
                        }
                        else
                            accavg = accavg / (float) sensorAccuracy.size();

                        DataSnapshot pastTime = snapshot.child("exercise1").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren = pastTime.getChildren();
                        for(DataSnapshot i: pastTimeChildren)
                        {
                            pastDate.add(i.getKey());
                            pastSec.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot pastTime2 = snapshot.child("exercise2").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren2 = pastTime2.getChildren();
                        for(DataSnapshot i: pastTimeChildren2)
                        {
                            pastDate2.add(i.getKey());
                            pastSec2.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot pastTime3 = snapshot.child("exercise3").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren3 = pastTime3.getChildren();
                        for(DataSnapshot i: pastTimeChildren3)
                        {
                            pastDate3.add(i.getKey());
                            pastSec3.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }


                        if(pastSec.size() == 0){
                            pastSec.add("0");
                            ex1 = 0;
                        }
                        else
                            ex1 = pastSec.size();

                        if(pastSec2.size() == 0){
                            pastSec2.add("0");
                            ex2 = 0;
                        }
                        else
                            ex2 = pastSec2.size();

                        if(pastSec3.size() == 0){
                            pastSec3.add("0");
                            ex3 = 0;
                        }
                        else
                            ex3 = pastSec3.size();


                        List pieData = new ArrayList<>();
                        pieData.add(new SliceValue(ex1, Color.BLUE).setLabel("Chest"));
                        pieData.add(new SliceValue(ex2, Color.GRAY).setLabel("Left"));
                        pieData.add(new SliceValue(ex3, Color.RED).setLabel("Right"));

                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(12);
                        pieChartView.setPieChartData(pieChartData);

                        TextView totTime = (TextView)findViewById(R.id.totaltimes);
                        TextView perce = (TextView)findViewById(R.id.percenT);
                        TextView goalTime = (TextView)findViewById(R.id.currentGoal);
                        TextView accLat = (TextView)findViewById(R.id.latest);
                        TextView accAvg = (TextView)findViewById(R.id.average);

                        accAvg.setText(String.format("%.0f",(accavg)) + " %");
                        if(sensorAccuracy.size() == 0){
                            accLat.setText("0");
                        }
                        else
                            accLat.setText(sensorAccuracy.get(sensorAccuracy.size()-1) + " %");
                        totTime.setText( Integer.toString(ex2));
                        perce.setText( Float.toString(Math.round (100 * (float)ex2/((ex1+ex2+ex3)))) + " %");


                        DataSnapshot goal = snapshot.child("exercise2").child("goal");
                        Iterable<DataSnapshot> goalChildren = goal.getChildren();
                        for(DataSnapshot i: goalChildren)
                        {
                            goalDate.add(i.getKey());
                            goalSec.add(i.getValue().toString());
                            Log.d("goaldate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        if(goalSec.size() == 0){
                            goalSec.add("0");
                        }
                        else
                            goalTime.setText(goalSec.get(0) + " (s)");

                        //goalTime.setText(goalSec.get(0) + " (s)");
                        lineChartView = findViewById(R.id.chart);

                        List yAxisValues =  new ArrayList();
                        List axisValues = new ArrayList();


                        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

                        for (int i = 0; i < axisData.length; i++) {
                            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                        }

                        if(sensorAccuracy.size() > 0){
                            for (int i = 0; i < sensorAccuracy.size(); i++) {
                                yAxisValues.add(new PointValue(i,Integer.parseInt(sensorAccuracy.get(i)) ));
                            }}


                        List lines = new ArrayList();
                        lines.add(line);

                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        Axis axis = new Axis();
                        axis.setValues(axisValues);
                        axis.setTextSize(16);
                        axis.setTextColor(Color.parseColor("#03A9F4"));
                        data.setAxisXBottom(axis);

                        Axis yAxis = new Axis();
                        yAxis.setName("Sensor Accuracy in %");
                        yAxis.setTextColor(Color.parseColor("#03A9F4"));
                        yAxis.setTextSize(15);
                        data.setAxisYLeft(yAxis);

                        lineChartView.setLineChartData(data);
                        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                        viewport.top = 100;
                        lineChartView.setMaximumViewport(viewport);
                        lineChartView.setCurrentViewport(viewport);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;
            case 3:
                String[] cse3 = {"1", "2", "3", "4", "5", "6", "7", "8",
                        "9", "10", "11", "12"};
                System.arraycopy(cse3, 0, axisData, 0, axisData.length);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pastDate.clear();
                        pastSec.clear();
                        goalDate.clear();
                        goalSec.clear();
                        pastDate2.clear();
                        pastSec2.clear();
                        pastDate3.clear();
                        pastSec3.clear();
                        sensorDate.clear();
                        sensorAccuracy.clear();
                        float accavg = 0;

                        DataSnapshot sens = snapshot.child("exercise3").child("sensorStability");
                        Iterable<DataSnapshot> sensChildren = sens.getChildren();
                        for(DataSnapshot i: sensChildren)
                        {
                            sensorDate.add(i.getKey());
                            sensorAccuracy.add(i.getValue().toString());
                            accavg = accavg + (float) Integer.parseInt(i.getValue().toString());
                            Log.d("sensordate",  i.getKey());
                            Log.d("accuracy", i.getValue().toString());
                        }

                        if(sensorAccuracy.size() == 0){
                            accavg = 0;
                        }
                        else
                            accavg = accavg / (float) sensorAccuracy.size();

                        DataSnapshot pastTime = snapshot.child("exercise1").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren = pastTime.getChildren();
                        for(DataSnapshot i: pastTimeChildren)
                        {
                            pastDate.add(i.getKey());
                            pastSec.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot pastTime2 = snapshot.child("exercise3").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren2 = pastTime2.getChildren();
                        for(DataSnapshot i: pastTimeChildren2)
                        {
                            pastDate2.add(i.getKey());
                            pastSec2.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }
                        DataSnapshot pastTime3 = snapshot.child("exercise3").child("pastTimes");
                        Iterable<DataSnapshot> pastTimeChildren3 = pastTime3.getChildren();
                        for(DataSnapshot i: pastTimeChildren3)
                        {
                            pastDate3.add(i.getKey());
                            pastSec3.add(i.getValue().toString());
                            Log.d("pastdate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        if(pastSec.size() == 0){
                            pastSec.add("0");
                            ex1 = 0;
                        }
                        else
                            ex1 = pastSec.size();

                        if(pastSec2.size() == 0){
                            pastSec2.add("0");
                            ex2 = 0;
                        }
                        else
                            ex2 = pastSec2.size();

                        if(pastSec3.size() == 0){
                            pastSec3.add("0");
                            ex3 = 0;
                        }
                        else
                            ex3 = pastSec3.size();



                        List pieData = new ArrayList<>();
                        pieData.add(new SliceValue(ex1, Color.BLUE).setLabel("Chest"));
                        pieData.add(new SliceValue(ex2, Color.GRAY).setLabel("Left"));
                        pieData.add(new SliceValue(ex3, Color.RED).setLabel("Right"));

                        TextView totTime = (TextView)findViewById(R.id.totaltimes);
                        TextView perce = (TextView)findViewById(R.id.percenT);
                        TextView goalTime = (TextView)findViewById(R.id.currentGoal);
                        TextView accLat = (TextView)findViewById(R.id.latest);
                        TextView accAvg = (TextView)findViewById(R.id.average);

                        accAvg.setText(String.format("%.0f",(accavg)) + " %");
                        if(sensorAccuracy.size() == 0){
                            accLat.setText("0");
                        }
                        else
                            accLat.setText(sensorAccuracy.get(sensorAccuracy.size()-1) + " %");
                        totTime.setText( Integer.toString(ex3));
                        perce.setText( Float.toString(Math.round (100 * ((float)ex3/(float)(ex1+ex2+ex3)))) + " %");

                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(12);
                        pieChartView.setPieChartData(pieChartData);


                        DataSnapshot goal = snapshot.child("exercise3").child("goal");
                        Iterable<DataSnapshot> goalChildren = goal.getChildren();
                        for(DataSnapshot i: goalChildren)
                        {
                            goalDate.add(i.getKey());
                            goalSec.add(i.getValue().toString());
                            Log.d("goaldate",  i.getKey());
                            Log.d("sec", i.getValue().toString());
                        }

                        if(goalSec.size() == 0){
                            goalSec.add("0");
                        }
                        else
                            goalTime.setText(goalSec.get(0) + " (s)");


                        lineChartView = findViewById(R.id.chart);

                        List yAxisValues =  new ArrayList();
                        List axisValues = new ArrayList();


                        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

                        for (int i = 0; i < axisData.length; i++) {
                            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                        }

                        if(sensorAccuracy.size() > 0){
                            for (int i = 0; i < sensorAccuracy.size(); i++) {
                                yAxisValues.add(new PointValue(i,Integer.parseInt(sensorAccuracy.get(i)) ));
                            }}


                        List lines = new ArrayList();
                        lines.add(line);

                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        Axis axis = new Axis();
                        axis.setValues(axisValues);
                        axis.setTextSize(16);
                        axis.setTextColor(Color.parseColor("#03A9F4"));
                        data.setAxisXBottom(axis);

                        Axis yAxis = new Axis();
                        yAxis.setName("Sensor Accuracy in %");
                        yAxis.setTextColor(Color.parseColor("#03A9F4"));
                        yAxis.setTextSize(15);
                        data.setAxisYLeft(yAxis);

                        lineChartView.setLineChartData(data);
                        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                        viewport.top = 100;
                        lineChartView.setMaximumViewport(viewport);
                        lineChartView.setCurrentViewport(viewport);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;
            default:
                break;

        }





    }

    public void back(View view) {
        Intent intent = new Intent(this, statSelection.class);
        startActivity(intent);
    }

}