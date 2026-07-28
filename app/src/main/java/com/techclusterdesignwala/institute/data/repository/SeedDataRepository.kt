package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.entity.*
import com.techclusterdesignwala.institute.data.local.dao.*
import javax.inject.Inject
import javax.inject.Singleton
import java.util.*

@Singleton
class SeedDataRepository @Inject constructor(
    private val userDao: UserDao,
    private val softwareDao: SoftwareDao,
    private val batchDao: BatchDao,
    private val enrollmentDao: EnrollmentDao,
    private val attendanceDao: AttendanceDao
) {
    suspend fun isDataSeeded(): Boolean {
        return userDao.getByEmail("admin@techcluster.com") != null
    }

    suspend fun seedData() {
        val hash = hashPassword("admin123")
        val adminId = userDao.insert(UserEntity(name = "Admin", email = "admin@techcluster.com", passwordHash = hash, role = "ADMIN", phone = "9999999999"))
        val teacher1Id = userDao.insert(UserEntity(name = "Rahul Sharma", email = "rahul@techcluster.com", passwordHash = hashPassword("teacher123"), role = "TEACHER", phone = "9999999998"))
        val teacher2Id = userDao.insert(UserEntity(name = "Priya Patel", email = "priya@techcluster.com", passwordHash = hashPassword("teacher123"), role = "TEACHER", phone = "9999999997"))

        val s1 = userDao.insert(UserEntity(name = "Amit Kumar", email = "amit@student.com", passwordHash = hashPassword("student123"), role = "STUDENT", phone = "1111111111"))
        val s2 = userDao.insert(UserEntity(name = "Neha Singh", email = "neha@student.com", passwordHash = hashPassword("student123"), role = "STUDENT", phone = "1111111112"))
        val s3 = userDao.insert(UserEntity(name = "Rohit Verma", email = "rohit@student.com", passwordHash = hashPassword("student123"), role = "STUDENT", phone = "1111111113"))
        val s4 = userDao.insert(UserEntity(name = "Sneha Reddy", email = "sneha@student.com", passwordHash = hashPassword("student123"), role = "STUDENT", phone = "1111111114"))
        val s5 = userDao.insert(UserEntity(name = "Vikas Gupta", email = "vikas@student.com", passwordHash = hashPassword("student123"), role = "STUDENT", phone = "1111111115"))

        val sw1 = softwareDao.insert(SoftwareEntity(name = "AutoCAD", description = "2D & 3D CAD design software", durationHours = 40))
        val sw2 = softwareDao.insert(SoftwareEntity(name = "SolidWorks", description = "3D mechanical CAD software", durationHours = 45))
        val sw3 = softwareDao.insert(SoftwareEntity(name = "Photoshop", description = "Image editing & graphic design", durationHours = 30))
        val sw4 = softwareDao.insert(SoftwareEntity(name = "Illustrator", description = "Vector graphics & illustration", durationHours = 30))
        val sw5 = softwareDao.insert(SoftwareEntity(name = "Figma", description = "UI/UX design & prototyping", durationHours = 25))
        val sw6 = softwareDao.insert(SoftwareEntity(name = "Premiere Pro", description = "Video editing & production", durationHours = 35))
        val sw7 = softwareDao.insert(SoftwareEntity(name = "ANSYS", description = "Engineering simulation software", durationHours = 50))
        val sw8 = softwareDao.insert(SoftwareEntity(name = "Revit", description = "BIM & architectural design", durationHours = 40))
        val sw9 = softwareDao.insert(SoftwareEntity(name = "3ds Max", description = "3D modeling & rendering", durationHours = 45))
        val sw10 = softwareDao.insert(SoftwareEntity(name = "Creo", description = "Parametric 3D CAD design", durationHours = 45))
        val sw11 = softwareDao.insert(SoftwareEntity(name = "After Effects", description = "Motion graphics & VFX", durationHours = 35))

        val now = Calendar.getInstance()
        val startDate = now.timeInMillis
        now.add(Calendar.MONTH, 2)
        val endDate = now.timeInMillis

        val b1 = batchDao.insert(BatchEntity(name = "AutoCAD Morning Batch", softwareId = sw1, teacherId = teacher1Id, startTime = "09:00", endTime = "10:30", daysOfWeek = "MON,WED,FRI", roomNo = "Lab-1", startDate = startDate, endDate = endDate, maxStudents = 15))
        val b2 = batchDao.insert(BatchEntity(name = "SolidWorks Evening Batch", softwareId = sw2, teacherId = teacher1Id, startTime = "16:00", endTime = "17:30", daysOfWeek = "TUE,THU,SAT", roomNo = "Lab-2", startDate = startDate, endDate = endDate, maxStudents = 15))
        val b3 = batchDao.insert(BatchEntity(name = "Photoshop Weekend Batch", softwareId = sw3, teacherId = teacher2Id, startTime = "10:00", endTime = "13:00", daysOfWeek = "SAT,SUN", roomNo = "Lab-3", startDate = startDate, endDate = endDate, maxStudents = 20))

        val enrollIds = listOf(
            enrollmentDao.insert(EnrollmentEntity(studentId = s1, batchId = b1, enrollmentDate = startDate)),
            enrollmentDao.insert(EnrollmentEntity(studentId = s2, batchId = b1, enrollmentDate = startDate)),
            enrollmentDao.insert(EnrollmentEntity(studentId = s3, batchId = b2, enrollmentDate = startDate)),
            enrollmentDao.insert(EnrollmentEntity(studentId = s4, batchId = b2, enrollmentDate = startDate)),
            enrollmentDao.insert(EnrollmentEntity(studentId = s5, batchId = b3, enrollmentDate = startDate)),
            enrollmentDao.insert(EnrollmentEntity(studentId = s1, batchId = b3, enrollmentDate = startDate))
        )

        val cal = Calendar.getInstance()
        repeat(5) { day ->
            val date = cal.timeInMillis
            val eid = enrollIds[day % enrollIds.size]
            if (day % 2 == 0) {
                attendanceDao.insert(AttendanceEntity(enrollmentId = eid, date = date, status = "PRESENT"))
            } else {
                attendanceDao.insert(AttendanceEntity(enrollmentId = eid, date = date, status = "ABSENT"))
            }
            cal.add(Calendar.DAY_OF_MONTH, -1)
        }
    }

    private fun hashPassword(password: String): String {
        val digest = java.security.MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}
