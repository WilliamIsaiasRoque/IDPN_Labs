package com.example.lab3propuesto2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {

    private TextView batteryStatusText;

    public BatteryReceiver(TextView textView) {
        this.batteryStatusText = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float) scale;

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        String message = "Nivel de batería: " + batteryPct + "%\n";
        message += isCharging ? "Estado: Cargando" : "Estado: No cargando";
        batteryStatusText.setText(message);

        Log.d("BatteryReceiver", "Intent recibido mediante PendingIntent. Nivel: " + batteryPct + "%");
    }
}
