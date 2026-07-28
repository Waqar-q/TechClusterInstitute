package com.techclusterdesignwala.institute.ui.assignments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.data.local.entity.AssignmentEntity
import com.techclusterdesignwala.institute.ui.components.EmptyState
import com.techclusterdesignwala.institute.ui.components.StatusBadge
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentListScreen(
    userId: Long = 0,
    userRole: String = "",
    onAssignmentClick: (Long) -> Unit = {},
    viewModel: AssignmentViewModel = hiltViewModel()
) {
    val state by viewModel.listState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignments") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else if (state.assignments.isEmpty()) {
            EmptyState(title = "No Assignments", message = "No assignments yet. Enjoy!")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(state.assignments) { a ->
                    Card(modifier = Modifier.fillMaxWidth().clickable { onAssignmentClick(a.id) }, elevation = CardDefaults.cardElevation(2.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(48.dp)) {
                                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Assignment, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(a.title, fontWeight = FontWeight.Medium, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(a.subjectName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Due: ${SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(a.dueDate))}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                            }
                            StatusBadge(status = a.status)
                        }
                    }
                }
            }
        }
    }
}