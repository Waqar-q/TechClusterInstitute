package com.techclusterdesignwala.institute.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.EventEntity
import com.techclusterdesignwala.institute.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class EventsListState(
    val events: List<EventEntity> = emptyList(),
    val selectedMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    val selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val isLoading: Boolean = true
)

data class EventDetailState(
    val event: EventEntity? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _listState = MutableStateFlow(EventsListState())
    val listState: StateFlow<EventsListState> = _listState.asStateFlow()

    private val _detailState = MutableStateFlow(EventDetailState())
    val detailState: StateFlow<EventDetailState> = _detailState.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            eventRepository.getAllEvents().collect { list ->
                _listState.update { it.copy(events = list, isLoading = false) }
            }
        }
    }

    fun loadEventDetail(id: Long) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }
            val event = eventRepository.getEventById(id)
            _detailState.update { it.copy(event = event, isLoading = false) }
        }
    }

    fun changeMonth(month: Int, year: Int) {
        _listState.update { it.copy(selectedMonth = month, selectedYear = year, isLoading = true) }
        loadEvents()
    }
}
