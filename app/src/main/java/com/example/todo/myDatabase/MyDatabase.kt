package com.example.todo.myDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.myDatabase.dao.TasksDao
import com.example.todo.myDatabase.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class MyDatabase:RoomDatabase() {
    abstract fun tasksDao():TasksDao

    companion object{
        private var instance:MyDatabase?=null
        fun getInstance(context: Context):MyDatabase{
            if (instance==null){
                instance = Room.databaseBuilder(context.applicationContext,MyDatabase::class.java,"TasksDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}