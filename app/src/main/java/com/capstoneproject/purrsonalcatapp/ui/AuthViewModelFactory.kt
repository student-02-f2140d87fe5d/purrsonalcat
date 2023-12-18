package com.capstoneproject.purrsonalcatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.purrsonalcatapp.data.local.pref.AuthPreferences
import com.capstoneproject.purrsonalcatapp.data.repository.AuthRepository
import com.capstoneproject.purrsonalcatapp.ui.home.MainViewModel
import com.capstoneproject.purrsonalcatapp.ui.login.LoginViewModel
import com.capstoneproject.purrsonalcatapp.ui.register.RegisterViewModel

class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val authPreferences: AuthPreferences? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository, authPreferences!!) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Auth preferences is required")
    }
}