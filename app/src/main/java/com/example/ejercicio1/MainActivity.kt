package com.example.ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Task(
    val id: Int,
    val title: String,
    var isCompleted: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskScreen()
        }
    }
}


@Composable
fun TaskScreen() {

    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Hacer la compra", false),
                Task(2, "Lavar el coche", false),
                Task(3, "Tender la ropa", false),
                Task(4, "Recoger paquete", false),
                Task(5, "Reservar cita medica", false),
                Task(6, "Abrir la aplicación", true)
            )
        )
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "Tareas",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )


        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onComplete = { completedTask ->
                        tasks = tasks.map {
                            if (it.id == completedTask.id) it.copy(isCompleted = true) else it
                        }
                    },
                    onUndoComplete = { undoneTask ->
                        tasks = tasks.map {
                            if (it.id == undoneTask.id) it.copy(isCompleted = false) else it
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun TaskItem(task: Task, onComplete: (Task) -> Unit, onUndoComplete: (Task) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) Color(0xFFE0E0E0) else Color.White
        ),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(24.dp)
        ) {

            val iconRes = if (task.isCompleted) R.drawable.check else R.drawable.x

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = if (task.isCompleted) "Completada" else "Pendiente",
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (task.isCompleted) Color.Gray else Color.Black
                ),
                modifier = Modifier.weight(1f),
            )
            if (!task.isCompleted) {
                Button(
                    onClick = { onComplete(task) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Completar")
                }
            } else {

                Button(
                    onClick = { onUndoComplete(task) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Decompletar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}
