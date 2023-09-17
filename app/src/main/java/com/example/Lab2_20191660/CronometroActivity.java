package com.example.Lab2_20191660;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CronometroActivity extends AppCompatActivity {

    private TextView textViewCronometro;
    private Button startButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button resetButton;

    private boolean isRunning = false;
    private long startTime = 0;
    private long elapsedTime = 0;
    private Thread chronometerThread;

    private static final String PREFS_NAME = "CronometroPrefs";
    private static final String KEY_IS_RUNNING = "isRunning";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_ELAPSED_TIME = "elapsedTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        textViewCronometro = findViewById(R.id.textViewCronometro);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resumeButton = findViewById(R.id.resumeButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startTime = SystemClock.elapsedRealtime() - elapsedTime;
                    chronometerThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isRunning) {
                                long currentTime = SystemClock.elapsedRealtime();
                                elapsedTime = currentTime - startTime;
                                updateTimerText(elapsedTime);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    chronometerThread.start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    try {
                        chronometerThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    saveToSharedPreferences();
                }
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startTime = SystemClock.elapsedRealtime() - elapsedTime;
                    chronometerThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isRunning) {
                                long currentTime = SystemClock.elapsedRealtime();
                                elapsedTime = currentTime - startTime;
                                updateTimerText(elapsedTime);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    chronometerThread.start();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                elapsedTime = 0;
                textViewCronometro.setText("00:00.0");
                saveToSharedPreferences();
            }
        });

        // Cargar el estado del cronómetro desde las preferencias compartidas
        loadFromSharedPreferences();
    }

    private void updateTimerText(final long elapsedTime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int tenths = (int) (elapsedTime / 100) % 10;
                int seconds = (int) (elapsedTime / 1000) % 60;
                int minutes = (int) ((elapsedTime / 1000) / 60);

                String time = String.format("%02d:%02d.%d", minutes, seconds, tenths);
                textViewCronometro.setText(time);
            }
        });
    }

    private void saveToSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_RUNNING, isRunning);
        editor.putLong(KEY_START_TIME, startTime);
        editor.putLong(KEY_ELAPSED_TIME, elapsedTime);
        editor.apply();
    }

    private void loadFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isRunning = prefs.getBoolean(KEY_IS_RUNNING, false);
        startTime = prefs.getLong(KEY_START_TIME, 0);
        elapsedTime = prefs.getLong(KEY_ELAPSED_TIME, 0);
    }
}
