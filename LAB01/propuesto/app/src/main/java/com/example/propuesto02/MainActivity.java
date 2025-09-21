package com.example.propuesto02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.propuesto02.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String FILE_NAME = "asistentes.txt";
    private static final String TAG = "ConferenciaPropuesto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String registro = "Nombres: " + binding.etFirstName.getText().toString() + "\n" +
                        "Apellidos: " + binding.etLastName.getText().toString() + "\n" +
                        "Correo: " + binding.etEmail.getText().toString() + "\n" +
                        "Teléfono: " + binding.etPhone.getText().toString() + "\n" +
                        "Grupo sanguíneo: " + binding.etBloodGroup.getText().toString() + "\n\n";

                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
                    fos.write(registro.getBytes());
                    fos.close();
                    Toast.makeText(MainActivity.this, "Registro guardado", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error al guardar registro", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error guardando archivo", e);
                }
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FileInputStream fis = openFileInput(FILE_NAME);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    StringBuilder sb = new StringBuilder();
                    String linea;

                    while ((linea = reader.readLine()) != null) {
                        sb.append(linea).append("\n");
                    }

                    reader.close();
                    fis.close();

                    Log.d(TAG, "Registros guardados:\n" + sb.toString());
                    Toast.makeText(MainActivity.this, "Ver Logcat para registros", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error al leer registros", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error leyendo archivo", e);
                }
            }
        });
    }
}
