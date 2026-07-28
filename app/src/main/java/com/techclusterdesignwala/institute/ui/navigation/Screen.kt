package com.techclusterdesignwala.institute.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

sealed class Screen(val route: String, val title: String) {
    data object Login : Screen("login", "Login")
    data object AdminDashboard : Screen("admin_dashboard", "Dashboard")
    data object TeacherDashboard : Screen("teacher_dashboard", "Dashboard")
    data object StudentDashboard : Screen("student_dashboard", "Dashboard")
    data object ManageUsers : Screen("manage_users", "Users")
    data object ManageSoftware : Screen("manage_software", "Software")
    data object ManageBatches : Screen("manage_batches", "Batches")
    data object Attendance : Screen("attendance", "Attendance")
    data object Assignments : Screen("assignments", "Assignments")
    data object Results : Screen("results", "Results")
    data object NoticeBoard : Screen("noticeboard", "Notices")
    data object Events : Screen("events", "Events")
    data object Directory : Screen("directory", "Directory")
}

object NavIcons {
    val adminNav = listOf(
        Icons.Default.Dashboard to "Dashboard",
        Icons.Default.People to "Users",
        Icons.Default.Code to "Software",
        Icons.Default.Groups to "Batches",
        Icons.Default.ContactPage to "Directory"
    )
    val teacherNav = listOf(
        Icons.Default.Dashboard to "Dashboard",
        Icons.Default.TaskAlt to "Attendance",
        Icons.Default.Assignment to "Assignments",
        Icons.Default.Campaign to "Notices"
    )
    val studentNav = listOf(
        Icons.Default.Dashboard to "Dashboard",
        Icons.Default.TaskAlt to "Attendance",
        Icons.Default.Assignment to "Assignments",
        Icons.Default.Grade to "Results",
        Icons.Default.CalendarMonth to "Events"
    )
}