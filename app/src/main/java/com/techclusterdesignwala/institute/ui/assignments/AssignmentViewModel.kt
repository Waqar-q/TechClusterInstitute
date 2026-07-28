package com.techclusterdesignwala.institute.ui.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.dao.AssignmentDao
import com.techclusterdesignwala.institute.data.local.entity.AssignmentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssignmentListState(
    val assignments: List<AssignmentEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao
) : ViewModel() {
    private val _listState = MutableStateFlow(AssignmentListState())
    val listState: StateFlow<AssignmentListState> = _listState.asStateFlow()

    init {
        viewModelScope.launch {
            assignmentDao.getAll().collect { list ->
                _listState.update { it.copy(assignments = list, isLoading = false) }
            }
        }
    }
}