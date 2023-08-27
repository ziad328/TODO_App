package com.example.todo.myDatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.myDatabase.model.Task

@Dao
interface TasksDao {
    @Insert
    fun addNewTask(task: Task)

    @Delete
    fun removeTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("UPDATE tasks SET isDone=:isDone WHERE id=:id")
    fun updateTasksStatus(id: Int, isDone: Boolean)

    @Query("select *  from tasks where id=:id")
    fun getTask(id: Int): Task

    @Query("select * from tasks")
    fun getAllTasks(): List<Task>
}