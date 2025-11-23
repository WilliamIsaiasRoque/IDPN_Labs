package com.example.laboratorio7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EdificacionAdapter(
    private var lista: MutableList<Edificacion>
) : RecyclerView.Adapter<EdificacionAdapter.ViewHolder>(), Filterable {

    private val listaOriginal = ArrayList(lista)

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val titulo: TextView = item.findViewById(R.id.txtTitulo)
        val categoria: TextView = item.findViewById(R.id.txtCategoria)
        val descripcion: TextView = item.findViewById(R.id.txtDescripcion)
        val imagen: ImageView = item.findViewById(R.id.imgFoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_edificacion, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val e = lista[position]

        holder.titulo.text = e.titulo
        holder.categoria.text = e.categoria
        holder.descripcion.text = e.descripcion

        val idImg = holder.itemView.context.resources.getIdentifier(
            e.imagen,
            "drawable",
            holder.itemView.context.packageName
        )
        holder.imagen.setImageResource(idImg)
    }

    override fun getItemCount(): Int = lista.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(text: CharSequence?): FilterResults {
                val filtro = text.toString().lowercase()
                val results = FilterResults()

                results.values = if (filtro.isEmpty()) {
                    listaOriginal
                } else {
                    listaOriginal.filter {
                        it.titulo.lowercase().contains(filtro)
                                || it.categoria.lowercase().contains(filtro)
                    }
                }

                return results
            }

            override fun publishResults(text: CharSequence?, results: FilterResults?) {
                lista = (results?.values as List<Edificacion>).toMutableList()
                notifyDataSetChanged()
            }
        }
    }
}