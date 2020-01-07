package com.fserapian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class SetupActivity extends AppCompatActivity {
    private TextInputEditText txtPlayerX, txtPlayerO;

    String playerXName, playerOName;

    public void goToMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void goToGame(View view) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);

        playerXName = txtPlayerX.getText().toString();
        playerOName = txtPlayerO.getText().toString();

        intent.putExtra("playerXName", playerXName);
        intent.putExtra("playerOName", playerOName);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        txtPlayerX = findViewById(R.id.txtPlayerX);
        txtPlayerO = findViewById(R.id.txtPlayerO);
    }
}
