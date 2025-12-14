package com.example.mobileproject.data


import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    fun getTasks(): Flow<List<Task>> = dao.getAllTasks()

    suspend fun addTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}
