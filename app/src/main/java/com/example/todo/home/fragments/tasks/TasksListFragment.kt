package com.example.todo.home.fragments.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.FragmentTasksListBinding
import com.example.todo.myDatabase.MyDatabase
import com.example.todo.myDatabase.dao.TasksDao

class TasksListFragment : Fragment() {
    private lateinit var dao: TasksDao
    lateinit var viewBinding: FragmentTasksListBinding
    private val tasksAdapter = TasksAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentTasksListBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        dao = MyDatabase.getInstance(requireContext().applicationContext)
            .tasksDao()
        loadTasks()
        tasksAdapter.setColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    fun loadTasks() {
        context?.let {
            val tasks = dao
                .getAllTasks().toMutableList()
            tasksAdapter.updateTasks(tasks)
        }

    }


    private fun initRecyclerView() {
        viewBinding.rvTasks.adapter = tasksAdapter
        tasksAdapter.onButtonClickedListener =
            TasksAdapter.OnButtonClickedListener { position, task ->
                dao.updateTasksStatus(task.id!!, !task.isDone)
                val updatedTask = dao.getTask(task.id)
                tasksAdapter.updateTask(updatedTask, position)
            }
        tasksAdapter.onDeleteButtonClickedListener =
            TasksAdapter.OnButtonClickedListener { position, task ->
                dao.removeTask(task)
                tasksAdapter.deleteTask(task, position)

            }

    }

}