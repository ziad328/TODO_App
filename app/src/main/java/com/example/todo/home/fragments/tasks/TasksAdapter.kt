package com.example.todo.home.fragments.tasks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.myDatabase.model.Task
import java.text.SimpleDateFormat

class TasksAdapter(var color: Int? = null, var tasks: MutableList<Task>? = null) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, color)
    }

    fun setColor(color: Int) {
        this.color = color
    }

    override fun getItemCount(): Int = tasks?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks?.get(position)!!
        holder.bind(task)

        onButtonClickedListener?.let { onButtonClickedListener ->
            holder.itemBinding.btnTaskDone.setOnClickListener {
                onButtonClickedListener.onButtonClicked(position, task)

            }
        }
    }

    fun updateTasks(tasks: MutableList<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun updateTask(task: Task, position: Int) {
        this.tasks?.set(position, task)
        notifyItemChanged(position)
    }

    var onButtonClickedListener: OnButtonClickedListener? = null

    fun interface OnButtonClickedListener {
        fun onButtonClicked(position: Int, task: Task)
    }


    class ViewHolder(val itemBinding: ItemTaskBinding, val color: Int?) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: Task) {
            itemBinding.taskItemTitle.text = task.title.toString()
            var date = task.dateTime
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            var netDate = sdf.format(date)
            itemBinding.taskItemDate.text = netDate


            changeTaskStatus(task.isDone, color)
        }

        fun changeTaskStatus(isDone: Boolean, color: Int? = null) {
            if (isDone) {
                itemBinding.dragableRect.setImageResource(R.drawable.dragable_rect_task_done)
                itemBinding.taskItemTitle.setTextColor(Color.GREEN)
                itemBinding.btnTaskDone.setBackgroundResource(R.drawable.done)


            } else {
                itemBinding.dragableRect.setImageResource(R.drawable.dragable_rect_task)
                color?.let { itemBinding.taskItemTitle.setTextColor(it) }
                itemBinding.btnTaskDone.setBackgroundResource(R.drawable.check_mark)
            }
        }
    }
}