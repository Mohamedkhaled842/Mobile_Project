package com.example.mobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val completed: Boolean = false,
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
