package com.fserapian;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

//    Go to bonus activity
    public void goToBonus(View view) {
        Intent intent = new Intent(getApplicationContext(), BonusActivity.class);
        startActivity(intent);
    }

//    Go to setup activity
    public void goToSetup(View view) {
        Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
        startActivity(intent);
    }

//    Go to about activity
    public void goToAbout(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

//    Quit the app
    public void quit(View view) {
        finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
