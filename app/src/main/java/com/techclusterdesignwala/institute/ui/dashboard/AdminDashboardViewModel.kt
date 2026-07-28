package com.techclusterdesignwala.institute.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.dao.BatchDao
import com.techclusterdesignwala.institute.data.local.dao.SoftwareDao
import com.techclusterdesignwala.institute.data.local.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminDashboardState(
    val studentCount: Int = 0,
    val teacherCount: Int = 0,
    val batchCount: Int = 0,
    val softwareCount: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val userDao: UserDao,
    private val softwareDao: SoftwareDao,
    private val batchDao: BatchDao
) : ViewModel() {
    private val _state = MutableStateFlow(AdminDashboardState())
    val state: StateFlow<AdminDashboardState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userDao.getStudentCount(),
                userDao.getTeacherCount(),
                batchDao.count(),
                softwareDao.count()
            ) { s, t, b, sw ->
                _state.update { it.copy(studentCount = s, teacherCount = t, batchCount = b, softwareCount = sw, isLoading = false) }
            }.collect()
        }
    }
}