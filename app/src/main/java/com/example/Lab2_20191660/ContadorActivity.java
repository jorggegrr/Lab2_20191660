package com.example.Lab2_20191660;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ContadorActivity extends AppCompatActivity {

    private TextView textViewContador;
    private Button buttonIniciar;

    private int contador = 104;
    private boolean isRunning = false;

    private static final int LIMITE_CONTADOR = 226;
    private static final long INTERVALO = 10000;

    private Handler handler;
    private Vibrator vibrator;

    private Thread contadorThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        textViewContador = findViewById(R.id.textViewContador);
        buttonIniciar = findViewById(R.id.buttonIniciar);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
                    contador += 10;
                    actualizarContador();
                    if (contador >= LIMITE_CONTADOR) {
                        detenerContador();
                        contador = LIMITE_CONTADOR;
                        buttonIniciar.setEnabled(false);
                        startVibrationService();
                    }
                }
            }
        };

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        actualizarContador();

        buttonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning && contador < LIMITE_CONTADOR) {
                    isRunning = true;
                    iniciarContadorThread();
                } else {
                    if (contador < LIMITE_CONTADOR) {
                        contador += 10;
                        actualizarContador();
                    }
                }
                if (contador >= LIMITE_CONTADOR) {
                    contador = LIMITE_CONTADOR;
                    actualizarContador();
                    buttonIniciar.setEnabled(false);
                    startVibrationService();
                }
            }
        });
    }

    private void iniciarContadorThread() {
        contadorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning && contador < LIMITE_CONTADOR) {
                    try {
                        Thread.sleep(INTERVALO);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isRunning = false;
                if (contador >= LIMITE_CONTADOR) {
                    contador = LIMITE_CONTADOR;
                    actualizarContador();
                    buttonIniciar.setEnabled(false);
                    startVibratorService();
                }
            }
        });
        contadorThread.start();
    }

    private void startVibratorService() {
        Intent intent = new Intent(this, VibrationService.class);
        startService(intent);
    }
    private void detenerContador() {
        if (contadorThread != null) {
            contadorThread.interrupt();
        }
    }

    private void actualizarContador() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewContador.setText("Contador: " + contador);
            }
        });
    }

    private void startVibrationService() {
        if (vibrator.hasVibrator()) {
            long[] pattern = {0, 1000};
            VibrationEffect effect = VibrationEffect.createWaveform(pattern, -1);

            vibrator.vibrate(effect);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contador", contador);
        outState.putBoolean("isRunning", isRunning);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contador = savedInstanceState.getInt("contador");
        isRunning = savedInstanceState.getBoolean("isRunning");
        actualizarContador();
        if (isRunning) {
            iniciarContadorThread();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detenerContador();
    }
}
