package com.example.delivertrack.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivertrack.viewmodel.DeliveryViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: DeliveryViewModel, onBack: () -> Unit) {
    val records by viewModel.records.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("履歴") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                }
            }
        )
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(records) { record ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = MaterialTheme.shapes.medium) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(record.dateIso, style = MaterialTheme.typography.labelLarge)
                        Spacer(Modifier.height(4.dp))
                        Text("時間：${record.durationMillis/1000/60}分")
                        Text("売上：¥${record.earnings}")
                        record.note?.let {
                            Spacer(Modifier.height(4.dp))
                            Text("備考：$it")
                        }
                    }
                }
            }
        }
    }
}