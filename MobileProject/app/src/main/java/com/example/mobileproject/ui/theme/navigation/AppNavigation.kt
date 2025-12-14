package com.example.mobileproject.ui.theme.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileproject.data.AppPreferences
import com.example.mobileproject.viewmodel.SettingsViewModelFactory
import com.example.mobileproject.ui.theme.screens.SettingsScreen
import com.example.mobileproject.viewmodel.TaskViewModel
import com.example.mobileproject.ui.theme.screens.AddTaskScreen
import com.example.mobileproject.ui.theme.screens.TaskListScreen
import com.example.mobileproject.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(viewModel: TaskViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "task_list"
    ) {
        composable("task_list") {
            TaskListScreen(
                viewModel = viewModel,
                onAddTaskClick = { navController.navigate("add_task") },
                navController = navController
            )
        }


        composable("add_task") {
            AddTaskScreen(
                viewModel = viewModel,
                onTaskSaved = { navController.popBackStack() }
            )
        }
        composable("settings") {
            val context = LocalContext.current
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(AppPreferences(context))
            )
            SettingsScreen(settingsViewModel)
        }

    }
}
