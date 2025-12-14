package com.example.mobileproject

import com.example.mobileproject.viewmodel.TaskViewModel

import com.example.mobileproject.data.Task
import com.example.mobileproject.data.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobileproject.fake.MainDispatcherRule

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelMockitoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TaskRepository
    private lateinit var viewModel: TaskViewModel

    @Before
    fun setup() {
        repository = mock()

        whenever(repository.getTasks()).thenReturn(flowOf(emptyList()))

        viewModel = TaskViewModel(repository)
    }

    @Test
    fun addTask_callsRepositoryAddTask() = runTest {
        val task = Task(1, "Test",  false,"Mockito", System.currentTimeMillis())
        viewModel.addTask(task)

        verify(repository).addTask(task)
    }

    @Test
    fun updateTask_callsRepositoryUpdateTask() = runTest {
        val task = Task(1, "Test", false,"Mockito",  System.currentTimeMillis())
        viewModel.updateTask(task)

        verify(repository).updateTask(task)
    }

    @Test
    fun deleteTask_callsRepositoryDeleteTask() = runTest {
        val task = Task(1, "Test",false, "Mockito", System.currentTimeMillis())
        viewModel.deleteTask(task)

        verify(repository).deleteTask(task)
    }
}
