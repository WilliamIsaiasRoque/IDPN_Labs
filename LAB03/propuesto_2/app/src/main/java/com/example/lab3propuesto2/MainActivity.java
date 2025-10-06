package com.example.lab3propuesto2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BatteryReceiver batteryReceiver;
    private TextView batteryStatusText;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryStatusText = findViewById(R.id.batteryStatusText);
        Button btnUpdate = findViewById(R.id.btnUpdate);

        batteryReceiver = new BatteryReceiver(batteryStatusText);

        // Crear el PendingIntent que enviará el broadcast
        Intent intent = new Intent(this, BatteryReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Al presionar el botón, se envía el broadcast
        btnUpdate.setOnClickListener(v -> {
            try {
                pendingIntent.send();
                Log.d("MainActivity", "PendingIntent enviado para actualizar batería");
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
        Log.d("MainActivity", "BroadcastReceiver registrado satisfactoriamente");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);
        Log.d("MainActivity", "BroadcastReceiver desregistrado satisfactoriamente");
    }
}
