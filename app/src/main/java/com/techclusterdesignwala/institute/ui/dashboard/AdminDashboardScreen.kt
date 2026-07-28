package com.techclusterdesignwala.institute.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    userId: Long,
    onLogout: () -> Unit,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listOf(
                    StatItem("Students", state.studentCount.toString(), Icons.Default.People, Navy500),
                    StatItem("Teachers", state.teacherCount.toString(), Icons.Default.Person, Teal500),
                    StatItem("Batches", state.batchCount.toString(), Icons.Default.Groups, Gold500),
                    StatItem("Software", state.softwareCount.toString(), Icons.Default.Code, Navy500)
                )) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(item.icon, contentDescription = null, tint = item.color, modifier = Modifier.size(32.dp))
                            Spacer(Modifier.height(8.dp))
                            Text(item.value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                            Text(item.label, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

private data class StatItem(val label: String, val value: String, val icon: ImageVector, val color: androidx.compose.ui.graphics.Color)