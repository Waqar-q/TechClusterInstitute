package com.techclusterdesignwala.institute.ui.attendance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.data.local.entity.AttendanceEntity
import com.techclusterdesignwala.institute.ui.components.EmptyState
import com.techclusterdesignwala.institute.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class AttendanceUiState(
    val records: List<AttendanceEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val attendanceDao: com.techclusterdesignwala.institute.data.local.dao.AttendanceDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()

    fun load(studentId: Long) {
        viewModelScope.launch {
            attendanceDao.getByStudent(studentId).collect { list ->
                _uiState.update { it.copy(records = list, isLoading = false) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    userId: Long,
    userRole: String,
    viewModel: AttendanceViewModel = hiltViewModel()
) {
    LaunchedEffect(userId) { viewModel.load(userId) }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.records.isEmpty()) {
            EmptyState(title = "No Attendance", message = "No attendance records found")
        } else {
            val present = state.records.count { it.status == "PRESENT" }
            val total = state.records.size
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("Overall: ", style = MaterialTheme.typography.bodyLarge)
                            Text("$present / $total", fontWeight = FontWeight.Bold, color = if (total > 0 && present * 100 / total >= 75) StatusPresent else StatusAbsent)
                            Text(" (${if (total > 0) present * 100 / total else 0}%)", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                items(state.records) { record ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (record.status == "PRESENT") Icons.Default.CheckCircle else Icons.Default.Cancel,
                                contentDescription = record.status,
                                tint = if (record.status == "PRESENT") StatusPresent else StatusAbsent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(record.status, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}