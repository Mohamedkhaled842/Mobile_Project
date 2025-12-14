package com.example.mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.room.Room
import com.example.mobileproject.data.AppDatabase
import com.example.mobileproject.data.TaskRepository
import com.example.mobileproject.data.AppPreferences
import com.example.mobileproject.viewmodel.SettingsViewModelFactory
import com.example.mobileproject.viewmodel.TaskViewModel
import com.example.mobileproject.viewmodel.TaskViewModelFactory
import com.example.mobileproject.ui.theme.MobileProjectTheme
import com.example.mobileproject.ui.theme.navigation.AppNavigation
import com.example.mobileproject.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tasks_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(repository)
    }

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(AppPreferences(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsViewModel: SettingsViewModel by viewModels {
            SettingsViewModelFactory(AppPreferences(applicationContext))
        }

        setContent {
            val darkMode by settingsViewModel.darkMode.collectAsState()

            MobileProjectTheme(darkTheme = darkMode) {
                AppNavigation(viewModel)
            }
        }
    }

}