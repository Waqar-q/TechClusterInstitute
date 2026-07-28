package com.techclusterdesignwala.institute.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import com.techclusterdesignwala.institute.data.repository.AdminRepository
import com.techclusterdesignwala.institute.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageUsersState(
    val users: List<UserEntity> = emptyList(),
    val showDialog: Boolean = false,
    val editUser: UserEntity? = null,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "STUDENT",
    val phone: String = "",
    val isLoading: Boolean = true
)

@HiltViewModel
class ManageUsersViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ManageUsersState())
    val state: StateFlow<ManageUsersState> = _state.asStateFlow()

    init { loadUsers() }

    private fun loadUsers() {
        viewModelScope.launch {
            adminRepository.getAllUsers().collect { list ->
                _state.update { it.copy(users = list, isLoading = false) }
            }
        }
    }

    fun showAddDialog() { _state.update { it.copy(showDialog = true, editUser = null, name = "", email = "", password = "", role = "STUDENT", phone = "") } }
    fun showEditDialog(user: UserEntity) { _state.update { it.copy(showDialog = true, editUser = user, name = user.name, email = user.email, password = "", role = user.role, phone = user.phone) } }
    fun hideDialog() { _state.update { it.copy(showDialog = false) } }
    fun updateName(v: String) { _state.update { it.copy(name = v) } }
    fun updateEmail(v: String) { _state.update { it.copy(email = v) } }
    fun updatePassword(v: String) { _state.update { it.copy(password = v) } }
    fun updateRole(v: String) { _state.update { it.copy(role = v) } }
    fun updatePhone(v: String) { _state.update { it.copy(phone = v) } }

    fun saveUser() {
        viewModelScope.launch {
            val s = _state.value
            val edit = s.editUser
            if (edit != null) {
                adminRepository.updateUser(edit.copy(name = s.name, email = s.email, phone = s.phone, role = s.role))
            } else {
                authRepository.createUser(s.name, s.email, s.password, s.role, s.phone)
            }
            hideDialog()
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch { adminRepository.deleteUser(user) }
    }
}
