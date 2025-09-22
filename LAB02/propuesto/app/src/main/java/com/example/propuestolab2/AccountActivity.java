package com.example.propuestolab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class AccountActivity extends AppCompatActivity {

    public static final String ACCOUNT_RECORD = "ACCOUNT_RECORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        EditText edtFirstname = findViewById(R.id.edtFirstname);
        EditText edtLastname = findViewById(R.id.edtLastname);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtUsername2 = findViewById(R.id.edtUsername2);
        EditText edtPassword2 = findViewById(R.id.edtPassword2);

        Button btnAceptar = findViewById(R.id.btnAceptar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccountEntity account = new AccountEntity();
                account.setFirstname(edtFirstname.getText().toString().trim());
                account.setLastname(edtLastname.getText().toString().trim());
                account.setEmail(edtEmail.getText().toString().trim());
                account.setPhone(edtPhone.getText().toString().trim());
                account.setUsername(edtUsername2.getText().toString().trim());
                account.setPassword(edtPassword2.getText().toString().trim());

                Gson gson = new Gson();
                String accountJson = gson.toJson(account);

                Intent data = new Intent();
                data.putExtra(ACCOUNT_RECORD, accountJson);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
