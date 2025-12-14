package com.example.mobileproject.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileproject.ui.theme.MobileProjectTheme
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title") ?: "No Title"
        val description = intent.getStringExtra("description") ?: "No Description"
        val completed = intent.getBooleanExtra("completed", false)
        val timestamp = intent.getLongExtra("timestamp", System.currentTimeMillis())

        setContent {
            MobileProjectTheme {
                TaskDetailScreen(title = title, description = description, completed = completed, timestamp = timestamp
                )
            }
        }
    }
}

@Composable
fun TaskDetailScreen(title: String, description: String, completed: Boolean, timestamp: Long) {
    val statusText = if (completed) "Completed" else "Pending"
    val statusColor = if (completed) Color(0xFF4CAF50) else Color(0xFFFF9800)

    Column(modifier = Modifier.fillMaxSize().padding(top = 60.dp, start = 16.dp, end = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(title, style = MaterialTheme.typography.titleLarge)

        Text("Description: $description", style = MaterialTheme.typography.bodyMedium)

        Box(modifier = Modifier.background(
                    color = statusColor.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small
        ).padding(horizontal = 14.dp, vertical = 6.dp)) {
            Text(text = statusText, color = statusColor, style = MaterialTheme.typography.labelMedium)
        }

        Text(text = "Date Added: ${
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    .format(Date(timestamp))
            }", style = MaterialTheme.typography.bodyMedium
        )
    }
}
