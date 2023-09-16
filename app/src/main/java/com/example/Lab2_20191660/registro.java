package com.example.Lab2_20191660;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registro extends AppCompatActivity {
    private Button buttonCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registro.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}