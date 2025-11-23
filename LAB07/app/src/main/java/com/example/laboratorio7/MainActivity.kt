package com.example.laboratorio7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EdificacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerEdificaciones)
        val txtBuscar = findViewById<EditText>(R.id.txtBuscar)

        val lista = cargarEdificaciones()
        adapter = EdificacionAdapter(lista)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        txtBuscar.addTextChangedListener {
            adapter.filter.filter(it.toString())
        }
    }

    private fun cargarEdificaciones(): MutableList<Edificacion> {
        val lista = mutableListOf<Edificacion>()
        val input = assets.open("edificaciones.txt")

        input.bufferedReader().forEachLine { linea ->
            val p = linea.split("|")
            lista.add(
                Edificacion(
                    p[0], p[1], p[2], p.getOrElse(3) { "ic_launcher_foreground" }
                )
            )
        }
        return lista
    }
}