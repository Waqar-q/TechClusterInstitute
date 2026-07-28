package com.techclusterdesignwala.institute.ui.notifications

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.ui.components.EmptyState
import com.techclusterdesignwala.institute.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBack: () -> Unit = {},
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } },
                actions = {
                    TextButton(onClick = { viewModel.markAllAsRead() }) {
                        Text("Mark all read", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.notifications.isEmpty()) {
            EmptyState(title = "No Notifications", message = "You're all caught up!")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.notifications) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClick = { viewModel.markAsRead(notification.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(
    notification: com.techclusterdesignwala.institute.data.local.entity.NotificationEntity,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (notification.type) {
                    "ATTENDANCE" -> Icons.Default.TaskAlt
                    "ASSIGNMENT" -> Icons.Default.Assignment
                    "EVENT" -> Icons.Default.Event
                    "NOTICE" -> Icons.Default.Campaign
                    "RESULT" -> Icons.Default.Grade
                    else -> Icons.Default.Notifications
                },
                contentDescription = null,
                tint = when (notification.type) {
                    "ATTENDANCE" -> StatusPresent
                    "ASSIGNMENT" -> StatusPending
                    "EVENT" -> Teal500
                    "NOTICE" -> Gold700
                    "RESULT" -> Navy500
                    else -> MaterialTheme.colorScheme.primary
                },
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = notification.title, fontWeight = FontWeight.Medium, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = notification.message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(notification.timestamp)), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (!notification.isRead) {
                Badge(modifier = Modifier.size(8.dp))
            }
        }
    }
}
