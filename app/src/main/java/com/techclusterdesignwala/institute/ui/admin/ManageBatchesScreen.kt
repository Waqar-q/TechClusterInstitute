package com.techclusterdesignwala.institute.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBatchesScreen(viewModel: ManageBatchesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Manage Batches") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddDialog() }, containerColor = Navy800) {
                Icon(Icons.Default.Add, contentDescription = "Add Batch", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.batches) { batch ->
                val softName = state.softwareList.find { it.id == batch.softwareId }?.name ?: "Unknown"
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = RoundedCornerShape(8.dp), color = Teal500.copy(alpha = 0.1f), modifier = Modifier.size(48.dp)) {
                            Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Groups, contentDescription = null, tint = Teal700) }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(batch.name, fontWeight = FontWeight.Medium)
                            Text("$softName | ${batch.startTime}-${batch.endTime} | ${batch.daysOfWeek}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Room: ${batch.roomNo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text("Add Batch") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(value = state.name, onValueChange = { viewModel.updateName(it) }, label = { Text("Batch Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.startTime, onValueChange = { viewModel.updateStart(it) }, label = { Text("Start Time (HH:MM)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.endTime, onValueChange = { viewModel.updateEnd(it) }, label = { Text("End Time (HH:MM)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.daysOfWeek, onValueChange = { viewModel.updateDays(it) }, label = { Text("Days (MON,WED,FRI)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.roomNo, onValueChange = { viewModel.updateRoom(it) }, label = { Text("Room No") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.maxStudents, onValueChange = { viewModel.updateMax(it) }, label = { Text("Max Students") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = { TextButton(onClick = { viewModel.saveBatch() }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { viewModel.hideDialog() }) { Text("Cancel") } }
        )
    }
}
