package com.techclusterdesignwala.institute.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.data.local.entity.BatchEntity
import com.techclusterdesignwala.institute.data.repository.TeacherRepository
import com.techclusterdesignwala.institute.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class TeacherDashboardState(
    val batches: List<BatchEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class TeacherDashboardViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TeacherDashboardState())
    val state: StateFlow<TeacherDashboardState> = _state.asStateFlow()

    fun loadBatches(teacherId: Long) {
        viewModelScope.launch {
            teacherRepository.getMyBatches(teacherId).collect { list ->
                _state.update { it.copy(batches = list, isLoading = false) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    userId: Long,
    onLogout: () -> Unit,
    viewModel: TeacherDashboardViewModel = hiltViewModel()
) {
    LaunchedEffect(userId) { viewModel.loadBatches(userId) }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teacher Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else if (state.batches.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No batches assigned", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { Text("My Batches", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
                items(state.batches) { batch ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = MaterialTheme.shapes.small, color = Teal500.copy(alpha = 0.1f), modifier = Modifier.size(48.dp)) {
                                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Groups, contentDescription = null, tint = Teal700) }
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(batch.name, fontWeight = FontWeight.Medium)
                                Text("${batch.startTime}-${batch.endTime} | ${batch.daysOfWeek}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Room: ${batch.roomNo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}