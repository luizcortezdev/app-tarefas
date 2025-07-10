package com.example.task_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter(private var categorias: List<CategoriaCount>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.categoriaNome)
        val quantidade: TextView = itemView.findViewById(R.id.categoriaQuantidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        // Aqui formatamos o texto conforme pedido:
        holder.nome.text = "Categoria: ${categoria.nome}"
        holder.quantidade.text = "Total: ${categoria.quantidade}"
    }

    override fun getItemCount(): Int = categorias.size

    // Atualiza a lista e notifica a RecyclerView para refrescar os dados
    fun updateList(newList: List<CategoriaCount>) {
        categorias = newList
        notifyDataSetChanged()
    }
}
