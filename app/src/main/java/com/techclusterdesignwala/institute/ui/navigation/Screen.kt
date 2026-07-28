package com.techclusterdesignwala.institute.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Dashboard)
    data object Attendance : Screen("attendance", "Attendance", Icons.Default.TaskAlt)
    data object Timetable : Screen("timetable", "Timetable", Icons.Default.Schedule)
    data object Assignments : Screen("assignments", "Assignments", Icons.Default.Assignment)
    data object AssignmentDetail : Screen("assignments/{id}", "Assignment Detail") {
        fun createRoute(id: Long) = "assignments/$id"
    }
    data object Results : Screen("results", "Results", Icons.Default.Grade)
    data object NoticeBoard : Screen("noticeboard", "Notices", Icons.Default.Campaign)
    data object NoticeDetail : Screen("noticeboard/{id}", "Notice Detail") {
        fun createRoute(id: Long) = "noticeboard/$id"
    }
    data object Events : Screen("events", "Events", Icons.Default.CalendarMonth)
    data object EventDetail : Screen("events/{id}", "Event Detail") {
        fun createRoute(id: Long) = "events/$id"
    }
    data object Directory : Screen("directory", "Directory", Icons.Default.People)
    data object Notifications : Screen("notifications", "Notifications", Icons.Default.Notifications)

    companion object {
        val bottomNavItems = listOf(Dashboard, Timetable, Assignments, Events, Directory)
    }
}