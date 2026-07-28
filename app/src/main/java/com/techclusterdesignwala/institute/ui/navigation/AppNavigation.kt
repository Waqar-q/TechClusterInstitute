package com.techclusterdesignwala.institute.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techclusterdesignwala.institute.ui.assignments.AssignmentDetailScreen
import com.techclusterdesignwala.institute.ui.assignments.AssignmentListScreen
import com.techclusterdesignwala.institute.ui.attendance.AttendanceScreen
import com.techclusterdesignwala.institute.ui.dashboard.DashboardScreen
import com.techclusterdesignwala.institute.ui.directory.DirectoryScreen
import com.techclusterdesignwala.institute.ui.events.EventDetailScreen
import com.techclusterdesignwala.institute.ui.events.EventsScreen
import com.techclusterdesignwala.institute.ui.noticeboard.NoticeBoardScreen
import com.techclusterdesignwala.institute.ui.noticeboard.NoticeDetailScreen
import com.techclusterdesignwala.institute.ui.notifications.NotificationsScreen
import com.techclusterdesignwala.institute.ui.results.ResultsScreen
import com.techclusterdesignwala.institute.ui.timetable.TimetableScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = Screen.bottomNavItems.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    Screen.bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon!!, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                    onNavigateToAttendance = { navController.navigate(Screen.Attendance.route) }
                )
            }
            composable(Screen.Attendance.route) {
                AttendanceScreen()
            }
            composable(Screen.Timetable.route) {
                TimetableScreen()
            }
            composable(Screen.Assignments.route) {
                AssignmentListScreen(
                    onAssignmentClick = { id -> navController.navigate(Screen.AssignmentDetail.createRoute(id)) }
                )
            }
            composable(
                route = Screen.AssignmentDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: 0L
                AssignmentDetailScreen(assignmentId = id, onBack = { navController.popBackStack() })
            }
            composable(Screen.Results.route) {
                ResultsScreen()
            }
            composable(Screen.NoticeBoard.route) {
                NoticeBoardScreen(
                    onNoticeClick = { id -> navController.navigate(Screen.NoticeDetail.createRoute(id)) }
                )
            }
            composable(
                route = Screen.NoticeDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: 0L
                NoticeDetailScreen(noticeId = id, onBack = { navController.popBackStack() })
            }
            composable(Screen.Events.route) {
                EventsScreen(
                    onEventClick = { id -> navController.navigate(Screen.EventDetail.createRoute(id)) }
                )
            }
            composable(
                route = Screen.EventDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: 0L
                EventDetailScreen(eventId = id, onBack = { navController.popBackStack() })
            }
            composable(Screen.Directory.route) {
                DirectoryScreen()
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}