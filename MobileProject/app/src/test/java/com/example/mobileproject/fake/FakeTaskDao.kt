package com.example.mobileproject.fake

import com.example.mobileproject.data.TaskDao
import com.example.mobileproject.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTaskDao : TaskDao {

    private val tasksFlow = MutableStateFlow<List<Task>>(emptyList())

    override fun getAllTasks(): Flow<List<Task>> = tasksFlow

    override suspend fun insertTask(task: Task) {
        tasksFlow.value = tasksFlow.value + task
    }

    override suspend fun updateTask(task: Task) {
        tasksFlow.value = tasksFlow.value.map {
            if (it.id == task.id) task else it
        }
    }

    override suspend fun deleteTask(task: Task) {
        tasksFlow.value = tasksFlow.value.filter {
            it.id != task.id
        }
    }
}
