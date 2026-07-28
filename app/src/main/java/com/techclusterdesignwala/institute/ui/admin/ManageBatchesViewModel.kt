package com.techclusterdesignwala.institute.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.BatchEntity
import com.techclusterdesignwala.institute.data.local.entity.SoftwareEntity
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import com.techclusterdesignwala.institute.data.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageBatchesState(
    val batches: List<BatchEntity> = emptyList(),
    val softwareList: List<SoftwareEntity> = emptyList(),
    val teachers: List<UserEntity> = emptyList(),
    val showDialog: Boolean = false,
    val name: String = "",
    val selectedSoftware: Long = 0,
    val selectedTeacher: Long = 0,
    val startTime: String = "09:00",
    val endTime: String = "10:30",
    val daysOfWeek: String = "MON,WED,FRI",
    val roomNo: String = "Lab-1",
    val maxStudents: String = "20",
    val isLoading: Boolean = true
)

@HiltViewModel
class ManageBatchesViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ManageBatchesState())
    val state: StateFlow<ManageBatchesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                adminRepository.getAllBatches(),
                adminRepository.getAllSoftware(),
                adminRepository.getUsersByRole("TEACHER")
            ) { b, s, t ->
                _state.update { it.copy(batches = b, softwareList = s, teachers = t, isLoading = false) }
            }.collect()
        }
    }

    fun showAddDialog() { _state.update { it.copy(showDialog = true, name = "", selectedSoftware = state.softwareList.firstOrNull()?.id ?: 0, selectedTeacher = state.teachers.firstOrNull()?.id ?: 0) } }
    fun hideDialog() { _state.update { it.copy(showDialog = false) } }
    fun updateName(v: String) { _state.update { it.copy(name = v) } }
    fun updateSoftware(v: Long) { _state.update { it.copy(selectedSoftware = v) } }
    fun updateTeacher(v: Long) { _state.update { it.copy(selectedTeacher = v) } }
    fun updateStart(v: String) { _state.update { it.copy(startTime = v) } }
    fun updateEnd(v: String) { _state.update { it.copy(endTime = v) } }
    fun updateDays(v: String) { _state.update { it.copy(daysOfWeek = v) } }
    fun updateRoom(v: String) { _state.update { it.copy(roomNo = v) } }
    fun updateMax(v: String) { _state.update { it.copy(maxStudents = v) } }

    fun saveBatch() {
        viewModelScope.launch {
            val s = _state.value
            adminRepository.createBatch(BatchEntity(name = s.name, softwareId = s.selectedSoftware, teacherId = s.selectedTeacher, startTime = s.startTime, endTime = s.endTime, daysOfWeek = s.daysOfWeek, roomNo = s.roomNo, startDate = System.currentTimeMillis(), endDate = System.currentTimeMillis() + 7776000000L, maxStudents = s.maxStudents.toIntOrNull() ?: 20))
            hideDialog()
        }
    }
}
