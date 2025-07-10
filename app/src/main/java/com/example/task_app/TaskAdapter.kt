package com.example.task_app

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val onTaskRemoved: () -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.taskTitle)
        val descricao: TextView = view.findViewById(R.id.taskDescription)
        val categoria: TextView = view.findViewById(R.id.taskCategory)
        val statusIcon: ImageView = view.findViewById(R.id.taskStatusIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.titulo.text = task.titulo
        holder.descricao.text = task.descricao
        holder.categoria.text = "Categoria: ${task.categoria}"

        // Verifica se está atrasada
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val hoje = Calendar.getInstance().time
        val dataTarefa = try {
            sdf.parse(task.data)
        } catch (e: Exception) {
            null
        }

        val atrasado = dataTarefa != null && dataTarefa.before(hoje)
        holder.statusIcon.setImageResource(
            if (atrasado) R.drawable.ic_atrasado else R.drawable.ic_ok
        )

        // Clique para excluir
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir tarefa?")
                .setMessage("Tem certeza que deseja excluir esta tarefa?")
                .setPositiveButton("Sim") { _, _ ->
                    val position = holder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        taskList.removeAt(position)
                        notifyItemRemoved(position)
                        onTaskRemoved()
                    }
                    onTaskRemoved()
                }
                .setNegativeButton("Não", null)
                .show()
        }
    }

    override fun getItemCount(): Int = taskList.size

    fun filterList(filtered: List<Task>) {
        taskList.clear()
        taskList.addAll(filtered)
        notifyDataSetChanged()
    }

}
