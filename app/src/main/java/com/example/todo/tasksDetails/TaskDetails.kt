package com.example.todo.tasksDetails


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.databinding.ActivityTaskDetailsBinding
import com.example.todo.myDatabase.MyDatabase
import com.example.todo.myDatabase.dao.TasksDao
import com.example.todo.myDatabase.model.Task
import com.example.todo.utils.Constants
import com.example.todo.utils.parcelable
import com.example.todo.utils.showDatePickerDialog
import java.util.Calendar


class TaskDetails : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailsBinding
    lateinit var dao: TasksDao
    private var dateCalendar = Calendar.getInstance()
    private var myTask = Task()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        onSaveClick()
        onSelectDateClick()
        setupToolbar()
        val task = intent.parcelable<Task>(Constants.TASK_KEY) as Task
        myTask.dateTime = task.dateTime

        Log.i("@@@ from intent", myTask.dateTime.toString())
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        val task = intent.parcelable<Task>(Constants.TASK_KEY) as Task
        binding.content.title.setText(task.title)
        binding.content.description.setText(task.description)
        val dateCalendar = java.util.Calendar.getInstance()
        dateCalendar.timeInMillis = task.dateTime!!
        val year = dateCalendar.get(java.util.Calendar.YEAR)
        val month = dateCalendar.get(java.util.Calendar.MONTH)
        val day = dateCalendar.get(java.util.Calendar.DAY_OF_MONTH)

        binding.content.selectDateTv.text = "$day/${month + 1}/$year"
    }


    private fun onSelectDateClick() {
        binding.content.selectDateTil.setOnClickListener {
            showDatePickerDialog(this) { date, calendar ->
                this.dateCalendar.set(
                    java.util.Calendar.YEAR,
                    calendar.get(java.util.Calendar.YEAR)
                )
                this.dateCalendar.set(
                    java.util.Calendar.MONTH,
                    calendar.get(java.util.Calendar.MONTH)
                )
                this.dateCalendar.set(
                    java.util.Calendar.DAY_OF_MONTH,
                    calendar.get(java.util.Calendar.DAY_OF_MONTH)
                )
                this.dateCalendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
                this.dateCalendar.set(java.util.Calendar.MINUTE, 0)
                this.dateCalendar.set(java.util.Calendar.SECOND, 0)
                this.dateCalendar.set(java.util.Calendar.MILLISECOND, 0)
                binding.content.selectDateTv.text = date
                myTask.dateTime = dateCalendar.timeInMillis

            }
        }

    }

    private fun onSaveClick() {
        binding.content.btnSave.setOnClickListener {
            updateTask()
        }
    }

    override fun onStart() {
        super.onStart()
        dao = MyDatabase.getInstance(this).tasksDao()
    }

    private fun updateTask() {
        if (!isValid()) {
            return
        }
        val task = intent.parcelable<Task>(Constants.TASK_KEY) as Task
        task.title = binding.content.title.text.toString()
        task.description = binding.content.description.text.toString()
        task.dateTime = dateCalendar.timeInMillis

        myTask = task.copy(
            id = task.id,
            title = task.title,
            description = task.description,
            dateTime = myTask.dateTime,
            isDone = task.isDone
        )
        dao.updateTask(myTask)
        finish()


    }

    fun isValid(): Boolean {
        var isValid = true
        if (binding.content.title.text.isNullOrBlank()) {
            isValid = false
            binding.content.titleTil.error = getString(R.string.add_task)
        } else {
            binding.content.titleTil.error = null
        }

        if (binding.content.selectDateTv.text.isNullOrBlank()) {
            isValid = false
            binding.content.selectDateTil.error = getString(R.string.select_date)
        } else {
            binding.content.selectDateTil.error = null
        }


        return isValid

    }

}