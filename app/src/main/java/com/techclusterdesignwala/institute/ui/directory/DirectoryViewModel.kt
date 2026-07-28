package com.techclusterdesignwala.institute.ui.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.dao.UserDao
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DirectoryUiState(
    val faculty: List<UserEntity> = emptyList(),
    val allUsers: List<UserEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true
)

@HiltViewModel
class DirectoryViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(DirectoryUiState())
    val uiState: StateFlow<DirectoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userDao.getByRole("TEACHER"),
                userDao.getByRole("STUDENT")
            ) { teachers, students ->
                val all = teachers + students
                _uiState.update { it.copy(faculty = all, allUsers = all, isLoading = false) }
            }.collect()
        }
    }

    fun search(query: String) {
        val filtered = _uiState.value.allUsers.filter {
            it.name.contains(query, ignoreCase = true) || it.email.contains(query, ignoreCase = true)
        }
        _uiState.update { it.copy(searchQuery = query, faculty = filtered) }
    }
}