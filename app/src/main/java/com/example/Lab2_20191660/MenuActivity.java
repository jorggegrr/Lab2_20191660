package com.example.Lab2_20191660;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonCronometro = findViewById(R.id.buttonCronometro);
        Button buttonContador = findViewById(R.id.buttonContador);

        buttonCronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CronometroActivity.class);
                startActivity(intent);
            }
        });

        buttonContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ContadorActivity.class);
                startActivity(intent);
            }
        });
    }
}