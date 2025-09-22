package com.example.loginsample;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import com.example.loginsample.databinding.ActivityMainBinding;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private AccountEntity accountEntity;
    private String accountEntityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); */

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText edtUsername = binding.edtUsername;
        EditText edtPassword = binding.edtPassword;
        Button btnLogin = binding.btnLogin;
        Button btnAddAccount = binding.btnAddAccount;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsername.getText().toString().equals("admin") && edtPassword.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Bienvenido a mi App", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Bienvenido a mi App");

                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("ACCOUNT",accountEntityString);

                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Error en la autenticación", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Error en la autenticación");
                }
            }
        });

        btnAddAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    Integer resultCode = activityResult.getResultCode();
                    if (resultCode==AccountActivity.ACCOUNT_ACEPTAR){
                        Intent data = activityResult.getData();
                        accountEntityString = data.getStringExtra(AccountActivity.ACCOUNT_RECORD);

                        Gson gson = new Gson();
                        accountEntity = gson.fromJson(accountEntityString,AccountEntity.class);

                        String firstname = accountEntity.getFirstname();
                        Toast.makeText(getApplicationContext(),"Nombre:" + firstname, Toast.LENGTH_LONG).show();
                        Log.d("LoginActivity", "Nombre:" + firstname);
                    }
                    else if (resultCode==AccountActivity.ACCOUNT_CANCELAR){
                        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_LONG).show();
                        Log.d("LoginActivity", "Cancelado");
                    }
                }
            }
    );
}