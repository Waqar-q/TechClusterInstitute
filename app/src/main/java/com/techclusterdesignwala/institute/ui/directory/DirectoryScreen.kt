package com.techclusterdesignwala.institute.ui.directory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import com.techclusterdesignwala.institute.ui.components.EmptyState
import com.techclusterdesignwala.institute.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(
    viewModel: DirectoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Directory") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.search(it) },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Search people...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.faculty.isEmpty()) {
                EmptyState(title = "No Contacts", message = "No people found")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.faculty) { member ->
                        DirectoryCard(member = member)
                    }
                }
            }
        }
    }
}

@Composable
private fun DirectoryCard(member: UserEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = when (member.role) {
                    "TEACHER" -> Teal500.copy(alpha = 0.1f)
                    "STUDENT" -> Navy500.copy(alpha = 0.1f)
                    else -> Gold500.copy(alpha = 0.1f)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = member.name.take(2).uppercase(), fontWeight = FontWeight.Bold, color = when (member.role) {
                        "TEACHER" -> Teal700
                        "STUDENT" -> Navy700
                        else -> Gold700
                    })
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = member.name, fontWeight = FontWeight.Medium)
                Text(text = member.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = member.role, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Phone, contentDescription = "Call", tint = StatusPresent)
                }
            }
        }
    }
}