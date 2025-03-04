package com.capstoneproject.purrsonalcatapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.purrsonalcatapp.data.remote.response.UserResponse
import com.capstoneproject.purrsonalcatapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _userData = MutableStateFlow<UserResponse?>(null)
    val userData = _userData

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            authRepository.getUserData().collect { result ->
                _userData.value = result
            }
        }
    }
}