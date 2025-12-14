package com.example.mobileproject.ui.theme.screens


import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileproject.data.Task
import com.example.mobileproject.viewmodel.TaskViewModel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.mobileproject.activity.TaskDetailActivity

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTaskClick: () -> Unit,
    navController: NavHostController
) {
    val tasks by viewModel.tasks.collectAsState()

    Column(Modifier.fillMaxSize().padding(top = 60.dp, start = 16.dp, end = 16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Your Tasks", style = MaterialTheme.typography.titleLarge)
            Row {
                Button(onClick = onAddTaskClick) {
                    Text("Add Task")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { navController.navigate("settings") }) {
                    Text("Settings")
                }
            }

        }

        Spacer(Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Text("No tasks yet!", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task ->
                    TaskCard(task = task, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, viewModel: TaskViewModel) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, TaskDetailActivity::class.java).apply {
                    putExtra("title", task.title)
                    putExtra("description", task.description)
                    putExtra("completed", task.completed)
                    putExtra("timestamp", task.timestamp)
                }
                context.startActivity(intent)

            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    if (task.completed) "Completed" else "Pending",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (task.completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }

            Checkbox(
                checked = task.completed,
                onCheckedChange = { isChecked ->
                    viewModel.updateTask(task.copy(completed = isChecked))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { viewModel.deleteTask(task) }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }
    }
}

@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel,
    onTaskSaved: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Add New Task", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    viewModel.addTask(Task(0, title, false, description))
                    onTaskSaved()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}


