package com.example.Lab2_20191660;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class CronometroActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private Button startButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button resetButton;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resumeButton = findViewById(R.id.resumeButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    isRunning = true;
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    chronometer.stop();
                    isRunning = false;
                }
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    chronometer.start();
                    isRunning = true;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                isRunning = false;
            }
        });
    }
}
