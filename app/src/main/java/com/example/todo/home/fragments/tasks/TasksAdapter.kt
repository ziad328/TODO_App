package com.example.todo.home.fragments.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.myDatabase.model.Task

class TasksAdapter(var tasks : List<Task>?) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = tasks?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks!![position])
    }

    fun updateTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(task: Task){
            itemBinding.taskItemTitle.text = task.title.toString()
            itemBinding.taskItemDate.text = task.dateTime.toString()
        }
    }
}