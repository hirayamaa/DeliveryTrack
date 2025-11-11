package com.example.delivertrack.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordInputScreen(
    duration: Long,
    onSave: (Double, String?) -> Unit,
    onCancel: () -> Unit
) {
    var earnings by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    val minutes = (duration / 1000 / 60).toInt()

    Scaffold(topBar = { TopAppBar(title = { Text("配達記録") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("所要時間：$minutes 分", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = earnings,
                onValueChange = { earnings = it },
                label = { Text("売上 (円)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("備考（任意）") }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) {
                    Text("キャンセル")
                }
                Button(onClick = {
                    val e = earnings.toDoubleOrNull()
                    if (e != null) onSave(e, if (note.isBlank()) null else note)
                }, modifier = Modifier.weight(1f)) {
                    Text("保存")
                }
            }
        }
    }
}