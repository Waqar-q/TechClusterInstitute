package com.techclusterdesignwala.institute.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import com.techclusterdesignwala.institute.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUsersScreen(viewModel: ManageUsersViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Users") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddDialog() }, containerColor = Navy800) {
                Icon(Icons.Default.Add, contentDescription = "Add User", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.users) { user ->
                UserCard(user = user, onEdit = { viewModel.showEditDialog(user) }, onDelete = { viewModel.deleteUser(user) })
            }
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text(if (state.editUser != null) "Edit User" else "Add User") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = state.name, onValueChange = { viewModel.updateName(it) }, label = { Text("Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = state.email, onValueChange = { viewModel.updateEmail(it) }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    if (state.editUser == null) {
                        OutlinedTextField(value = state.password, onValueChange = { viewModel.updatePassword(it) }, label = { Text("Password") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    }
                    OutlinedTextField(value = state.phone, onValueChange = { viewModel.updatePhone(it) }, label = { Text("Phone") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                        OutlinedTextField(value = state.role, onValueChange = {}, readOnly = true, label = { Text("Role") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            listOf("ADMIN", "TEACHER", "STUDENT").forEach { role ->
                                DropdownMenuItem(text = { Text(role) }, onClick = { viewModel.updateRole(role); expanded = false })
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { viewModel.saveUser() }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { viewModel.hideDialog() }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun UserCard(user: UserEntity, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = RoundedCornerShape(8.dp), color = when(user.role) { "ADMIN" -> Gold500; "TEACHER" -> Teal500; else -> Navy500 }.copy(alpha = 0.1f), modifier = Modifier.size(40.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Text(user.name.take(2).uppercase(), fontWeight = FontWeight.Bold, color = when(user.role) { "ADMIN" -> Gold700; "TEACHER" -> Teal700; else -> Navy700 })
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(user.name, fontWeight = FontWeight.Medium, style = MaterialTheme.typography.titleSmall)
                Text(user.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(user.role, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 8.dp))
            IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) { Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp)) }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) { Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.error) }
        }
    }
}
