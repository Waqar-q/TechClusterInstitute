package com.techclusterdesignwala.institute.ui.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.FacultyEntity
import com.techclusterdesignwala.institute.data.repository.DirectoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DirectoryUiState(
    val faculty: List<FacultyEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val selectedTab: Int = 0
)

@HiltViewModel
class DirectoryViewModel @Inject constructor(
    private val directoryRepository: DirectoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DirectoryUiState())
    val uiState: StateFlow<DirectoryUiState> = _uiState.asStateFlow()

    init {
        loadFaculty()
    }

    private fun loadFaculty() {
        viewModelScope.launch {
            directoryRepository.getAllFaculty().collect { list ->
                _uiState.update { it.copy(faculty = list, isLoading = false) }
            }
        }
    }

    fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            if (query.isBlank()) {
                loadFaculty()
            } else {
                directoryRepository.searchFaculty(query).collect { list ->
                    _uiState.update { it.copy(faculty = list) }
                }
            }
        }
    }

    fun selectTab(tab: Int) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
