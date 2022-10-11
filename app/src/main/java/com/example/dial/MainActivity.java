package com.example.dial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    boolean pressing = false;
    int angle = 0;
    int animationSpeed = 5;
    Dial dial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dial = findViewById(R.id.dial);

        Button b = findViewById(R.id.button);

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressing = true;
                    Log.d("Info", String.valueOf(pressing));
                    animate();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressing = false;
                    Log.d("Info", String.valueOf(pressing));
                }
                return true;
            }
        });
    }

    public void animate() {
        final Handler handler = new Handler();
        Runnable task = new Runnable() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {
                if (pressing && angle < 360) {
                    angle = angle < 360 ? angle + animationSpeed : 360;

                    dial.setAngle(angle);
                    dial.invalidate();

                    handler.postDelayed(this, 10);
                    Log.d("Pressed", String.valueOf(angle));
                } else if (!pressing && angle > 0){
                    angle = angle > 0 ? angle - animationSpeed : 0;

                    dial.setAngle(angle);
                    dial.invalidate();

                    handler.postDelayed(this, 10);
                    Log.d("Not Pressed", String.valueOf(angle));
                } else if (pressing && angle == 360) {
                    //Do something once the circle has been filled
                    angle = 0;
                    pressing = false;
                    dial.setAngle(angle);
                    dial.invalidate();
                }
            }
        };
        handler.post(task);
    }
}
