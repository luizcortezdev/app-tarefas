package com.example.task_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import androidx.fragment.app.Fragment

class CadastroFragment : Fragment() {

    private lateinit var taskNameInput: EditText
    private lateinit var taskDescriptionInput: EditText
    private lateinit var taskDateInput: EditText
    private lateinit var taskCategoryInput: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cadastro, container, false)

        // Vincula os campos do layout aos atributos da classe
        taskNameInput = view.findViewById(R.id.taskNameInput)
        taskDescriptionInput = view.findViewById(R.id.taskDescriptionInput)
        taskDateInput = view.findViewById(R.id.taskDateInput)
        taskCategoryInput = view.findViewById(R.id.taskCategoryInput)
        saveButton = view.findViewById(R.id.saveButton)

        // Ao clicar em "Salvar"
        saveButton.setOnClickListener {
            val nome = taskNameInput.text.toString().trim()
            val descricao = taskDescriptionInput.text.toString().trim()
            val data = taskDateInput.text.toString().trim()
            val categoria = taskCategoryInput.text.toString().trim()

            // Valida se os campos foram preenchidos
            if (nome.isEmpty() || descricao.isEmpty() || data.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cria uma nova task
            val novaTask = Task(nome, descricao, data, categoria)

            // Salva na lista da MainActivity
            (activity as? MainActivity)?.taskList?.add(novaTask)

            // Limpa os campos
            taskNameInput.text.clear()
            taskDescriptionInput.text.clear()
            taskDateInput.text.clear()
            taskCategoryInput.text.clear()

            Toast.makeText(requireContext(), "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
