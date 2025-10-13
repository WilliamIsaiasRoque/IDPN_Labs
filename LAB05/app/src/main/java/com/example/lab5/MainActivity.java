package com.example.lab5;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    View cardCathedral, cardClaustro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // incluye los items (incluidos con include)
        cardCathedral = findViewById(R.id.card_catedral);
        cardClaustro = findViewById(R.id.card_claustro);

        // configurar contenido de cada include (imagen + título)
        ImageView img1 = cardCathedral.findViewById(R.id.imgThumb);
        TextView title1 = cardCathedral.findViewById(R.id.tvTitle);
        img1.setImageResource(R.drawable.catedral);
        title1.setText("Catedral de Arequipa");

        ImageView img2 = cardClaustro.findViewById(R.id.imgThumb);
        TextView title2 = cardClaustro.findViewById(R.id.tvTitle);
        img2.setImageResource(R.drawable.claustro);
        title2.setText("Claustro de la Compañía");

        // listener: abrir fragment correspondiente
        cardCathedral.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CatedralFragment())
                    .addToBackStack(null)
                    .commit();
        });

        cardClaustro.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ClaustroFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}