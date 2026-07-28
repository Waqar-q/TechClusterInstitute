package com.techclusterdesignwala.institute.ui.noticeboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.dao.NoticeDao
import com.techclusterdesignwala.institute.data.local.entity.NoticeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoticeListState(
    val notices: List<NoticeEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val noticeDao: NoticeDao
) : ViewModel() {
    private val _listState = MutableStateFlow(NoticeListState())
    val listState: StateFlow<NoticeListState> = _listState.asStateFlow()

    init {
        viewModelScope.launch {
            noticeDao.getAll().collect { list ->
                _listState.update { it.copy(notices = list, isLoading = false) }
            }
        }
    }
}