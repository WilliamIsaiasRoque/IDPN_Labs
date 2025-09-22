package com.example.propuestolab2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnCrearCuenta;

    private static final String FILE_NAME = "cuentas.txt";
    private static final String TAG = "LoginPropuesto";

    private ActivityResultLauncher<Intent> accountLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        accountLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String newAccountJson = result.getData().getStringExtra("newAccount");
                        try {
                            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
                            fos.write((newAccountJson + "\n").getBytes());
                            fos.close();
                            Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error guardando nueva cuenta", e);
                        }
                    }
                }
        );

        btnLogin.setOnClickListener(v -> loginUser());

        btnCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
            accountLauncher.launch(intent);
        });
    }

    private void loginUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String linea;
            Gson gson = new Gson();
            boolean usuarioEncontrado = false;

            while ((linea = reader.readLine()) != null) {
                AccountEntity account = gson.fromJson(linea, AccountEntity.class);
                if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                    usuarioEncontrado = true;
                    break;
                }
            }

            reader.close();
            fis.close();

            if (usuarioEncontrado) {
                Toast.makeText(this, "Bienvenido " + username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Cuenta no encontrada", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error leyendo cuentas", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error leyendo archivo de cuentas", e);
        }
    }
}
