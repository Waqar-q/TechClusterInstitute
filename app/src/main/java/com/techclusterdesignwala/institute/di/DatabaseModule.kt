package com.techclusterdesignwala.institute.di

import android.content.Context
import androidx.room.Room
import com.techclusterdesignwala.institute.data.local.InstituteDatabase
import com.techclusterdesignwala.institute.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InstituteDatabase {
        return Room.databaseBuilder(
            context,
            InstituteDatabase::class.java,
            "tech_cluster_institute.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides fun provideStudentDao(db: InstituteDatabase): StudentDao = db.studentDao()
    @Provides fun provideAttendanceDao(db: InstituteDatabase): AttendanceDao = db.attendanceDao()
    @Provides fun provideTimetableSlotDao(db: InstituteDatabase): TimetableSlotDao = db.timetableSlotDao()
    @Provides fun provideAssignmentDao(db: InstituteDatabase): AssignmentDao = db.assignmentDao()
    @Provides fun provideResultDao(db: InstituteDatabase): ResultDao = db.resultDao()
    @Provides fun provideNoticeDao(db: InstituteDatabase): NoticeDao = db.noticeDao()
    @Provides fun provideEventDao(db: InstituteDatabase): EventDao = db.eventDao()
    @Provides fun provideFacultyDao(db: InstituteDatabase): FacultyDao = db.facultyDao()
    @Provides fun provideNotificationDao(db: InstituteDatabase): NotificationDao = db.notificationDao()
}
