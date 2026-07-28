package com.techclusterdesignwala.institute.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.dao.ResultDao
import com.techclusterdesignwala.institute.data.local.entity.ResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ResultsUiState(
    val results: List<ResultEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val resultDao: ResultDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            resultDao.getBySemester(1).collect { list ->
                _uiState.update { it.copy(results = list, isLoading = false) }
            }
        }
    }
}