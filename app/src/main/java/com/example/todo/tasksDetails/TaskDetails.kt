package com.example.todo.tasksDetails

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityTaskDetailsBinding
import com.example.todo.home.HomeActivity
import com.example.todo.myDatabase.MyDatabase
import com.example.todo.myDatabase.model.Task
import com.example.todo.utils.parcelable
import java.text.SimpleDateFormat
import java.util.Locale


class TaskDetails : AppCompatActivity() {
    private lateinit var viewBinding: ActivityTaskDetailsBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        setupToolbar()

    }


    private fun initViews() {
        var task = intent.parcelable<Task>("")
        viewBinding.title.setText(task?.title)
        viewBinding.description.setText(task?.description)
        viewBinding.date.text = formatDate(task?.dateTime)
        viewBinding.saveBtn.setOnClickListener {
            if (task != null) {
                updateTask(task)
            }
        }
        viewBinding.dateContainer.setOnClickListener {
            val dialog = DatePickerDialog(this)
            dialog.setOnDateSetListener { datePicker, year, month, day ->
                viewBinding.date.text = "$day-${month + 1}-$year"
                calendar.set(year, month, day)

                //to ignore time
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                dialog.show()
            }
        }
    }


    private fun formatDate(dateTime: Long?): String {
        var date = dateTime
        var sdf = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        return sdf.format(date)
    }

    private fun setupToolbar() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    private fun valid(): Boolean {
        var isValid = true
        if (viewBinding.title.text.toString().isNullOrBlank()) {
            viewBinding.titleContainer.error = "please enter a title"
            isValid = false
        } else {
            viewBinding.titleContainer.error = null
        }
        if (viewBinding.date.text.toString().isNullOrBlank()) {
            viewBinding.dateContainer.error = "please choose date"
            isValid = false
        } else {
            viewBinding.dateContainer.error = null
        }
        return isValid
    }

    private fun updateTask(task: Task) {
        if (!valid()) {
            return
        }
        task.title = viewBinding.title.text.toString().trim()
        task.description = viewBinding.description.text.toString().trim()
        task.dateTime = calendar.timeInMillis
        MyDatabase.getInstance(applicationContext)
            .tasksDao()
            .updateTask(task)

        val intent = Intent(baseContext, HomeActivity::class.java)
        startActivity(intent)
    }

}