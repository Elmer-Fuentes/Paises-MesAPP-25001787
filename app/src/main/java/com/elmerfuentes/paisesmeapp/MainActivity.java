/**
 * Proyecto: PaisesMeApp
 * Autor: Elmer Fuentes
 * Universidad Galileo
 */
package com.elmerfuentes.paisesmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowInsetsHelper.apply(this);

        btnCargar = findViewById(R.id.btnCargar);

        btnCargar.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    PaisesActivity.class
            );

            startActivity(intent);
        });
    }
}
