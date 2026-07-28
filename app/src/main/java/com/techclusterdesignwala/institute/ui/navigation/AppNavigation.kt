package com.techclusterdesignwala.institute.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.techclusterdesignwala.institute.ui.admin.ManageBatchesScreen
import com.techclusterdesignwala.institute.ui.admin.ManageSoftwareScreen
import com.techclusterdesignwala.institute.ui.admin.ManageUsersScreen
import com.techclusterdesignwala.institute.ui.assignments.AssignmentListScreen
import com.techclusterdesignwala.institute.ui.attendance.AttendanceScreen
import com.techclusterdesignwala.institute.ui.auth.LoginScreen
import com.techclusterdesignwala.institute.ui.dashboard.AdminDashboardScreen
import com.techclusterdesignwala.institute.ui.dashboard.StudentDashboardScreen
import com.techclusterdesignwala.institute.ui.dashboard.TeacherDashboardScreen
import com.techclusterdesignwala.institute.ui.directory.DirectoryScreen
import com.techclusterdesignwala.institute.ui.events.EventsScreen
import com.techclusterdesignwala.institute.ui.noticeboard.NoticeBoardScreen
import com.techclusterdesignwala.institute.ui.results.ResultsScreen
import com.techclusterdesignwala.institute.ui.theme.Navy800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var currentUserRole by remember { mutableStateOf("") }
    var currentUserId by remember { mutableStateOf(0L) }

    val adminRoutes = listOf("admin_dashboard", "manage_users", "manage_software", "manage_batches", "directory")
    val teacherRoutes = listOf("teacher_dashboard", "attendance", "assignments", "noticeboard")
    val studentRoutes = listOf("student_dashboard", "attendance", "assignments", "results", "events")

    val currentNav = when (currentUserRole) {
        "ADMIN" -> adminRoutes
        "TEACHER" -> teacherRoutes
        "STUDENT" -> studentRoutes
        else -> emptyList()
    }

    val showBottomBar = currentDestination?.route?.let { currentNav.contains(it) } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar && currentUserRole.isNotEmpty()) {
                NavigationBar {
                    val icons = when (currentUserRole) {
                        "ADMIN" -> NavIcons.adminNav
                        "TEACHER" -> NavIcons.teacherNav
                        else -> NavIcons.studentNav
                    }
                    val routes = currentNav
                    icons.forEachIndexed { index, (icon, title) ->
                        val route = routes.getOrNull(index) ?: return@forEachIndexed
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = title) },
                            label = { Text(title, style = MaterialTheme.typography.labelSmall) },
                            selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(indicatorColor = Navy800.copy(alpha = 0.15f))
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = { role, userId ->
                        currentUserRole = role
                        currentUserId = userId
                        val dest = when (role) {
                            "ADMIN" -> "admin_dashboard"
                            "TEACHER" -> "teacher_dashboard"
                            else -> "student_dashboard"
                        }
                        navController.navigate(dest) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable("admin_dashboard") { AdminDashboardScreen(userId = currentUserId, onLogout = {
                currentUserRole = ""; currentUserId = 0L
                navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
            })}
            composable("manage_users") { ManageUsersScreen() }
            composable("manage_software") { ManageSoftwareScreen() }
            composable("manage_batches") { ManageBatchesScreen() }

            composable("teacher_dashboard") { TeacherDashboardScreen(userId = currentUserId, onLogout = {
                currentUserRole = ""; currentUserId = 0L
                navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
            })}

            composable("student_dashboard") { StudentDashboardScreen(userId = currentUserId, onLogout = {
                currentUserRole = ""; currentUserId = 0L
                navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
            })}

            composable("attendance") { AttendanceScreen(userId = currentUserId, userRole = currentUserRole) }
            composable("assignments") { AssignmentListScreen(userId = currentUserId, userRole = currentUserRole) }
            composable("results") { ResultsScreen(userId = currentUserId) }
            composable("noticeboard") { NoticeBoardScreen() }
            composable("events") { EventsScreen() }
            composable("directory") { DirectoryScreen() }
        }
    }
}