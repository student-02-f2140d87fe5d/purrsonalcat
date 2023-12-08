package com.capstoneproject.purrsonalcatapp.data.repository

import com.capstoneproject.purrsonalcatapp.data.remote.response.LoginResponse
import com.capstoneproject.purrsonalcatapp.data.remote.response.RegisterResponse
import com.capstoneproject.purrsonalcatapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val apiService: ApiService) {
    suspend fun registerAuth(username: String, email: String, password: String): RegisterResponse {
        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.registerUser(username, email, password)

                if (!response.error!!) {
                    response
                } else {
                    RegisterResponse(data = null, error = true, message = "Register Failed")
                }
            } catch (e: Exception) {
                RegisterResponse(error = true, message = "An error occurred: ${e.message}")
            }
        }
    }
    suspend fun loginAuth(email: String, password: String): LoginResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.loginUser(email, password)
                if (!response.error!!) {
                    response
                } else {
                    LoginResponse(error = true, message = "Login Failed")
                }
            } catch (e: Exception) {
                LoginResponse(error = true, message = "An error occurred ${e.message}")
            }
        }
    }
}