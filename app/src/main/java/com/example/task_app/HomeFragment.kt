package com.example.task_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: TaskAdapter
    private var fullList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        searchView = view.findViewById(R.id.search_view)
        searchView.isIconified = false
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val mainActivity = activity as? MainActivity
        val taskList = mainActivity?.taskList ?: mutableListOf()

        adapter = TaskAdapter(taskList) {
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = adapter

        fullList = taskList.toMutableList()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = if (newText.isNullOrEmpty()) {
                    fullList
                } else {
                    fullList.filter {
                        it.titulo.contains(newText, true) ||
                                it.descricao.contains(newText, true) ||
                                it.categoria.contains(newText, true)
                    }
                }
                adapter.filterList(filtered.toMutableList())
                return true
            }
        })

        return view
    }
}
