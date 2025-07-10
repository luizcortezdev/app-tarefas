package com.example.task_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuscarFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriaAdapter
    private lateinit var searchView: SearchView

    private var fullList = listOf<CategoriaCount>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buscar, container, false)

        recyclerView = view.findViewById(R.id.categoryRecyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val mainActivity = activity as? MainActivity
        val taskList = mainActivity?.taskList ?: emptyList()

        // Agrupa as tarefas por categoria e conta quantas tem em cada
        fullList = taskList.groupBy { it.categoria }
            .map { CategoriaCount(it.key, it.value.size) }
            .sortedByDescending { it.quantidade } // ordena decrescente pela quantidade

        adapter = CategoriaAdapter(fullList)
        recyclerView.adapter = adapter

        // Configura filtro da SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Não faz nada ao submeter
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = if (newText.isNullOrEmpty()) {
                    fullList
                } else {
                    fullList.filter {
                        it.nome.contains(newText, ignoreCase = true)
                    }
                }
                adapter.updateList(filtered)
                return true
            }
        })

        return view
    }
}
