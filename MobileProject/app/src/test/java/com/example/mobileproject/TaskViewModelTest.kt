package com.example.mobileproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobileproject.data.Task
import com.example.mobileproject.data.TaskRepository
import com.example.mobileproject.fake.FakeTaskDao
import com.example.mobileproject.fake.MainDispatcherRule
import com.example.mobileproject.viewmodel.TaskViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TaskRepository
    private lateinit var viewModel: TaskViewModel

    @Before
    fun setup() {
        val fakeDao = FakeTaskDao()
        repository = TaskRepository(fakeDao)
        viewModel = TaskViewModel(repository)
    }


    @Test
    fun addTask_updatesTaskList() = runTest {
        val task = Task(id = 1,title = "Test",completed = false,description = "JUnit",timestamp = System.currentTimeMillis())


        viewModel.addTask(task)

        assertEquals(1, viewModel.tasks.value.size)
        assertEquals("Test", viewModel.tasks.value[0].title)
    }

    @Test
    fun updateTask_changesCompletionStatus() = runTest {
        val task = Task(id = 1,title = "Test",completed = false,description = "JUnit",timestamp = System.currentTimeMillis())
        repository.addTask(task)

        val updatedTask = task.copy(completed = true)
        viewModel.updateTask(updatedTask)

        assertEquals(true, viewModel.tasks.value[0].completed)
    }

    @Test
    fun deleteTask_removesTask() = runTest {
        val task = Task(id = 1,title = "Test",completed = false,description = "JUnit",timestamp = System.currentTimeMillis())
        repository.addTask(task)

        viewModel.deleteTask(task)

        assertEquals(0, viewModel.tasks.value.size)
    }
}
