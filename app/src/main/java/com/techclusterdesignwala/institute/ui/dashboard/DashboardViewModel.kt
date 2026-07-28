package com.techclusterdesignwala.institute.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.EventEntity
import com.techclusterdesignwala.institute.data.local.entity.NoticeEntity
import com.techclusterdesignwala.institute.data.repository.DashboardRepository
import com.techclusterdesignwala.institute.data.repository.EventRepository
import com.techclusterdesignwala.institute.data.repository.NoticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val studentCount: Int = 0,
    val presentToday: Int = 0,
    val totalToday: Int =