package com.example.delivertrack.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivertrack.viewmodel.DeliveryViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: DeliveryViewModel,
    onNavigateToRecord: (Long) -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val elapsed by viewModel.elapsedMillis.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()

    val formatted = remember(elapsed) {
        val totalSeconds = elapsed / 1000
        val h = totalSeconds / 3600
        val m = (totalSeconds % 3600) / 60
        val s = totalSeconds % 60
        String.format("%02d:%02d:%02d", h, m, s)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("DeliverTrack") }, actions = {
                IconButton(onClick = onNavigateToHistory) {
                    Icon(Icons.Default.List, contentDescription = "履歴")
                }
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(formatted, style = MaterialTheme.typography.displaySmall)

            Spacer(modifier = Modifier.height(32.dp))

            if (isRunning) {
                Button(
                    onClick = {
                        val duration = viewModel.stopTimer()
                        onNavigateToRecord(duration)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Stop, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("ストップ")
                }
            } else {
                Button(
                    onClick = { viewModel.startTimer() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("スタート")
                }
            }
        }
    }
}