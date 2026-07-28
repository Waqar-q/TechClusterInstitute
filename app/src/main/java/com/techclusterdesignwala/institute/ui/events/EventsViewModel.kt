package com.techclusterdesignwala.institute.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.dao.EventDao
import com.techclusterdesignwala.institute.data.local.entity.EventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventsListState(
    val events: List<EventEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventDao: EventDao
) : ViewModel() {
    private val _listState = MutableStateFlow(EventsListState())
    val listState: StateFlow<EventsListState> = _listState.asStateFlow()

    init {
        viewModelScope.launch {
            eventDao.getAll().collect { list ->
                _listState.update { it.copy(events = list, isLoading = false) }
            }
        }
    }
}