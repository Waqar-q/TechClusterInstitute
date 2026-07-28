package com.techclusterdesignwala.institute.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.NotificationEntity
import com.techclusterdesignwala.institute.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationUiState(
    val notifications: List<NotificationEntity> = emptyList(),
    val unreadCount: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            combine(
                notificationRepository.getAllNotifications(),
                notificationRepository.getUnreadCount()
            ) { list, unread ->
                _uiState.update { it.copy(notifications = list, unreadCount = unread, isLoading = false) }
            }.collect()
        }
    }

    fun markAsRead(id: Long) {
        viewModelScope.launch { notificationRepository.markAsRead(id) }
    }

    fun markAllAsRead() {
        viewModelScope.launch { notificationRepository.markAllAsRead() }
    }
}
