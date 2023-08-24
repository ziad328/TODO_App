package com.example.todo.home.fragments.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.databinding.FragmentTasksListBinding
import com.example.todo.myDatabase.MyDatabase

class TasksListFragment :Fragment(){
    lateinit var viewBinding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTasksListBinding.inflate(
            inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }

    fun loadTasks() {
        context?.let {
            val tasks = MyDatabase.getInstance(it)
                .tasksDao()
                .getAllTasks()
            tasksAdapter.updateTasks(tasks)
        }

    }

    private val tasksAdapter = TasksAdapter(null)
    private fun initViews() {
        viewBinding.rvTasks.adapter = tasksAdapter

    }
}