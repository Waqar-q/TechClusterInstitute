package com.techclusterdesignwala.institute.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.SoftwareEntity
import com.techclusterdesignwala.institute.data.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageSoftwareState(
    val software: List<SoftwareEntity> = emptyList(),
    val showDialog: Boolean = false,
    val name: String = "",
    val description: String = "",
    val duration: String = "40",
    val isLoading: Boolean = true
)

@HiltViewModel
class ManageSoftwareViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ManageSoftwareState())
    val state: StateFlow<ManageSoftwareState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            adminRepository.getAllSoftware().collect { list ->
                _state.update { it.copy(software = list, isLoading = false) }
            }
        }
    }

    fun showAddDialog() { _state.update { it.copy(showDialog = true, name = "", description = "", duration = "40") } }
    fun hideDialog() { _state.update { it.copy(showDialog = false) } }
    fun updateName(v: String) { _state.update { it.copy(name = v) } }
    fun updateDesc(v: String) { _state.update { it.copy(description = v) } }
    fun updateDuration(v: String) { _state.update { it.copy(duration = v) } }

    fun saveSoftware() {
        viewModelScope.launch {
            val s = _state.value
            if (s.name.isNotBlank()) {
                adminRepository.createSoftware(SoftwareEntity(name = s.name, description = s.description, durationHours = s.duration.toIntOrNull() ?: 40))
                hideDialog()
            }
        }
    }
}
