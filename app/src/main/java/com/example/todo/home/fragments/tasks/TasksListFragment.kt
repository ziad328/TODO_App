package com.example.todo.home.fragments.tasks

import android.content.Intent
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
import com.example.todo.tasksDetails.TaskDetails
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar

class TasksListFragment : Fragment() {
    private lateinit var dao: TasksDao
    lateinit var viewBinding: FragmentTasksListBinding
    private val tasksAdapter = TasksAdapter()
    private var selectedDate: Long = 0

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
        initCalenderView()
        initSelectedDate()
    }

    override fun onStart() {
        super.onStart()
        dao = MyDatabase.getInstance(requireContext().applicationContext)
            .tasksDao()
        loadAllTasksByDate(selectedDate)
        tasksAdapter.setColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    fun loadAllTasksByDate(date: Long) {
        val tasks = dao.getTaskByDate(date).toMutableList()
        tasksAdapter.updateTasks(tasks)
    }

    fun loadAllTasks() {
        val tasks = dao.getAllTasks().toMutableList()
        tasksAdapter.updateTasks(tasks)
    }

    private fun initSelectedDate() {
        val calendar = Calendar.getInstance()
        val today = CalendarDay.today()
        calendar.set(Calendar.YEAR, today.year)
        calendar.set(Calendar.MONTH, today.month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, today.day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        selectedDate = calendar.timeInMillis
    }

    private fun initCalenderView() {
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        viewBinding.calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, date.year)
            calendar.set(Calendar.MONTH, date.month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, date.day)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            if (selected) {
                val tasks = dao.getTaskByDate(calendar.timeInMillis).toMutableList()
                tasksAdapter.updateTasks(tasks)
            }
        })
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
        tasksAdapter.onItemClickedListener =
            TasksAdapter.OnItemClickedListener { task ->
                val intent = Intent(activity, TaskDetails::class.java)
                intent.putExtra("", task)
                startActivity(intent)

            }

    }

}