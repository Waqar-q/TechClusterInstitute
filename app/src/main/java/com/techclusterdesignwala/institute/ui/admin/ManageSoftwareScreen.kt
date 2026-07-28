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
fun ManageSoftwareScreen(viewModel: ManageSoftwareViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Manage Software") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddDialog() }, containerColor = Navy800) {
                Icon(Icons.Default.Add, contentDescription = "Add Software", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.software) { sw ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = RoundedCornerShape(8.dp), color = Navy500.copy(alpha = 0.1f), modifier = Modifier.size(48.dp)) {
                            Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Code, contentDescription = null, tint = Navy700) }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(sw.name, fontWeight = FontWeight.Medium)
                            Text("${sw.durationHours} hrs | ${sw.description.take(50)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text("Add Software") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = state.name, onValueChange = { viewModel.updateName(it) }, label = { Text("Software Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.description, onValueChange = { viewModel.updateDesc(it) }, label = { Text("Description") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.duration, onValueChange = { viewModel.updateDuration(it) }, label = { Text("Duration (hours)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = { TextButton(onClick = { viewModel.saveSoftware() }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { viewModel.hideDialog() }) { Text("Cancel") } }
        )
    }
}
