package com.example.Lab2_20191660;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.media.MediaPlayer;

public class VibrationService extends Service {

    private Vibrator vibrator;
    private boolean isVibrating = false;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isVibrating) {
            isVibrating = true;
            startVibration();
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isVibrating) {
            stopVibration();
            mediaPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startVibration() {
        long[] pattern = {0, 1000};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(pattern, 0);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(pattern, 0);
        }
    }

    private void stopVibration() {
        vibrator.cancel();
    }
}
