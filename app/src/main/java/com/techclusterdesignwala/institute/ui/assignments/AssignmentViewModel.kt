package com.techclusterdesignwala.institute.ui.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.AssignmentEntity
import com.techclusterdesignwala.institute.data.repository.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssignmentListState(
    val assignments: List<AssignmentEntity> = emptyList(),
    val isLoading: Boolean