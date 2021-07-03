package com.example.yogatrackercop4656;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import android.os.Bundle;

public class help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        int act = intent.getIntExtra("intVariableName", 0);

        TextView textView = (TextView) findViewById(R.id.textView5);
        String para3 = "A. As the series of this sequence are done with the support of a chair in seated position, make sure the choice of chair is comfortable and the base of the chair is flat and not too cushioned. It is also necessary to go for a low height chair as the feet should touch the floor comfortably to induce proper blood flow.\n" +
                "\n" +
                "B. Begin by sitting on the chair bringing the legs parallel to the legs of the chair, and extend the spine upwards till you feel comfortable. Never go beyond your comfort.\n" +
                "\n" +
                "C. Bring the palms in Namaste close to your chest near the Anahata Chakra, and close your eyes.\n" +
                "\n" +
                "D. Begin to feel the flow of the coolness of the breath through the nostril while trying to fill in the abdomen and lungs.\n" +
                "\n" +
                "E. While it may take few rounds of breathing to understand this, make sure the breathing is also slow and smooth.\n" +
                "\n" +
                "F. As we grow old and done with our responsibilities, it is time to focus from deep within and begin this slow connection. While still breathing take this moment to understand to answer questions like, Who, Why and What related to just oneself.\n" +
                "\n" +
                "G. Sit connecting with the breath for about 6 breaths or more and bring that smile on the face to keep the facial muscles calm and relaxed.";

        String para1 = "A. From the Shoulder and Arm rotation, come to relax keeping the hands down and closing your eyes take a few breaths.\n" +
                "\n" +
                "B. Then inhale and raise the right arm upwards taking it towards the left side as far as you can go while slowly tilting the chest and the neck towards the left.\n" +
                "\n" +
                "C. Exhale there completely and take another breath and remain here for few seconds continuing the breathing.\n" +
                "\n" +
                "D. Now inhale and extend the right arm and bring it down back to the chair and exhale completely relaxing the neck and shoulder.\n" +
                "\n" +
                "E. Repeat this arm movement one more time, in Chair Seated Side Stretch Pose.\n" +
                "\n" +
                "F. Make sure the body is well balanced and don't go to much towards the left loosing balance.";

        String para2 = "A. From the Shoulder and Arm rotation, come to relax keeping the hands down and closing your eyes take a few breaths.\n" +
                "\n" +
                "B. Then inhale and raise the left arm upwards taking it towards the left side as far as you can go while slowly tilting the chest and the neck towards the left.\n" +
                "\n" +
                "C. Exhale there completely and take another breath and remain here for few seconds continuing the breathing.\n" +
                "\n" +
                "D. Now inhale and extend the right arm and bring it down back to the chair and exhale completely relaxing the neck and shoulder.\n" +
                "\n" +
                "E. Repeat this arm movement one more time, in Chair Seated Side Stretch Pose.\n" +
                "\n" +
                "F. Make sure the body is well balanced and don't go to much towards the right loosing balance.";

        switch(act) {
            case 1: textView.setText(para1);
                    break;
            case 2: textView.setText(para2);
                    break;
            case 3: textView.setText(para3);
                break;
            default: break;
        }


        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void back(View view) {
        Intent intent = new Intent(this, exeSelection.class);
        startActivity(intent);
    }
}