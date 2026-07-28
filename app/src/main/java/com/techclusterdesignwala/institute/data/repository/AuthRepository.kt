package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.UserDao
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

data class LoginResult(val user: UserEntity?, val error: String?)

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    suspend fun login(email: String, password: String): LoginResult {
        if (email.isBlank()) return LoginResult(null, "Email is required")
        if (password.isBlank()) return LoginResult(null, "Password is required")

        val hash = hashPassword(password)
        val user = userDao.login(email, hash)
        return if (user != null) {
            LoginResult(user, null)
        } else {
            val exists = userDao.getByEmail(email)
            if (exists != null) {
                LoginResult(null, "Wrong password")
            } else {
                LoginResult(null, "User not found")
            }
        }
    }

    suspend fun createUser(name: String, email: String, password: String, role: String, phone: String = ""): Long {
        val hash = hashPassword(password)
        return userDao.insert(UserEntity(name = name, email = email, passwordHash = hash, role = role, phone = phone))
    }
}
