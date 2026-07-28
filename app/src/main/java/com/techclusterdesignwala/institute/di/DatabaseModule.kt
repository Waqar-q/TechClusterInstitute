package com.techclusterdesignwala.institute.di

import android.content.Context
import androidx.room.Room
import com.techclusterdesignwala.institute.data.local.InstituteDatabase
import com.techclusterdesignwala.institute.data.local.dao.*
import com.techclusterdesignwala.institute.data.repository.SeedDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InstituteDatabase {
        val db = Room.databaseBuilder(
            context,
            InstituteDatabase::class.java,
            "tech_cluster_institute.db"
        ).fallbackToDestructiveMigration().build()

        runBlocking(Dispatchers.IO) {
            val seedRepo = SeedDataRepository(
                db.userDao(), db.softwareDao(), db.batchDao(),
                db.enrollmentDao(), db.attendanceDao()
            )
            if (!seedRepo.isDataSeeded()) {
                seedRepo.seedData()
            }
        }

        return db
    }

    @Provides fun provideUserDao(db: InstituteDatabase): UserDao = db.userDao()
    @Provides fun provideSoftwareDao(db: InstituteDatabase): SoftwareDao = db.softwareDao()
    @Provides fun provideBatchDao(db: InstituteDatabase): BatchDao = db.batchDao()
    @Provides fun provideEnrollmentDao(db: InstituteDatabase): EnrollmentDao = db.enrollmentDao()
    @Provides fun provideAttendanceDao(db: InstituteDatabase): AttendanceDao = db.attendanceDao()
    @Provides fun provideAssignmentDao(db: InstituteDatabase): AssignmentDao = db.assignmentDao()
    @Provides fun provideAssignmentSubmissionDao(db: InstituteDatabase): AssignmentSubmissionDao = db.assignmentSubmissionDao()
    @Provides fun provideResultDao(db: InstituteDatabase): ResultDao = db.resultDao()
    @Provides fun provideNoticeDao(db: InstituteDatabase): NoticeDao = db.noticeDao()
    @Provides fun provideEventDao(db: InstituteDatabase): EventDao = db.eventDao()
}
