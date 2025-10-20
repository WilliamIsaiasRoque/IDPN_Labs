package com.example.lab6;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6.ui.fragment.BuildingFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main, new BuildingFragment())
                    .commit();
        }
    }
}
