package com.example.todo.myDatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var dateTime: Long? = null,
    var isDone: Boolean = false
) : Parcelable
