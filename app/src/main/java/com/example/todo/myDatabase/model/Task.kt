package com.example.todo.myDatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    var title:String?=null,
    var description:String?=null,
    var dateTime:Long?=null,
    var isDone:Boolean=false
)
