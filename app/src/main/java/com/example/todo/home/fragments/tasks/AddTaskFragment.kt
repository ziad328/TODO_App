package com.example.todo.home.fragments.tasks

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.myDatabase.MyDatabase
import com.example.todo.myDatabase.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskFragment :BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.addTaskBtn.setOnClickListener{
            createTask()
        }
        viewBinding.dateContainer.setOnClickListener{
            showDatePickerDialog()
        }
    }

    val calendar = Calendar.getInstance()
    private fun showDatePickerDialog() {
        context?.let {
            val dialog = DatePickerDialog(it)
            dialog.setOnDateSetListener{ datePicker,day,month,year->
                viewBinding.taskDate.text = "$day-${month+1}-$year"
                calendar.set(year,month+1,day)
                //to ignore time
                calendar.set(Calendar.HOUR_OF_DAY,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)

            }
            dialog.show()
        }
    }

    private fun valid(): Boolean {
        var isValid = true
        if (viewBinding.title.text.toString().isNullOrBlank()){
            viewBinding.titleContainer.error = "please enter a title"
            isValid = false
        }else{
            viewBinding.titleContainer.error = null
        }
        if (viewBinding.description.text.toString().isNullOrBlank()){
            viewBinding.descriptionContainer.error = "please enter a description"
            isValid = false
        }else{
            viewBinding.descriptionContainer.error = null
        }
        if (viewBinding.taskDate.text.toString().isNullOrBlank()){
            viewBinding.dateContainer.error = "please choose date"
            isValid = false
        }else{
            viewBinding.dateContainer.error = null
        }
        return isValid
    }
    private fun createTask() {
        if (!valid()){
            return
        }
        val task = Task(
            title = viewBinding.title.text.toString().trim(),
            description = viewBinding.description.text.toString().trim(),
            dateTime = calendar.timeInMillis
        )
        MyDatabase.getInstance(requireContext())
            .tasksDao()
            .addNewTask(task)
        onTaskAddedListener?.onTaskAdded()
        dismiss()
    }
    var onTaskAddedListener : OnTaskAddedListener?=null
    fun interface OnTaskAddedListener{
        fun onTaskAdded()
    }

}